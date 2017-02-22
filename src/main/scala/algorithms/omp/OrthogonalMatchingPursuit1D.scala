package algorithms.omp

import dictionaries.Dictionary
import org.apache.commons.math3.analysis.function.Abs
import org.apache.commons.math3.linear._
import utils.Accuracy

case class OrthogonalMatchingPursuit1D(x: Seq[Double], dict: Dictionary) extends OrthogonalMatchingPursuit[Double] {

  private val input: RealVector = new ArrayRealVector(x.toArray)
  private val dictRows: Int = this.dict.atoms.getColumn(0).length

  override def run(until: Accuracy): (List[(Double, Int)], Seq[Double]) = {

    /**
      * Perform one step of the Orthogonal Matching Pursuit algorithm
      *
      * @param residual The residual at iteration i
      * @param until    The object containing the type of the desired accuracy and the level
      * @param acc      The accumulator
      * @return A list containing the tuple weight-index of each selected atom, as well as the residual
      */
    def step(residual: RealVector, until: Accuracy, acc: List[(Double, Int)]): (List[(Double, Int)], Seq[Double]) = {

      if (until.estimate(input.subtract(residual), input) || dict.atoms.getRow(0).length <= acc.length) {
        (acc, residual.toArray.toSeq)
      } else {
        // Compute the inner product of x at iteration i and every atom in the dictionary dict
        val nextCorr = this.dict.atoms.transpose().operate(residual).map(new Abs)
        // Find the most correlated weighted atom from the dictionary
        val idx: Int = nextCorr.getMaxIndex
        // Weight the selected atom and normalize
        val norm: Double = this.dict.atoms.getColumnVector(nextCorr.getMaxIndex).getNorm
        val weight: Double = nextCorr.getMaxValue / (norm * norm)
        val nextBestGuess: (Double, Int) = (weight, idx)
        val nextAcc: List[(Double, Int)] = acc.::(nextBestGuess)
        // Get all previously selected atoms
        val previousAtoms: RealMatrix = this.dict.atoms.getSubMatrix((0 until dictRows).toArray, nextAcc.map(_._2).toArray)
        // Compute the projections
        val approximation: RealVector = new QRDecomposition(previousAtoms).getSolver.solve(input)
        // Compute the new residual
        val nextResidual: RealVector = input.subtract(previousAtoms.operate(approximation))

        step(nextResidual, until, nextAcc)
      }

    }
    step(input, until, List.empty)
  }
}
