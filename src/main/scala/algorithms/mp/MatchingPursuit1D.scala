package algorithms.mp

import dictionaries.Dictionary
import org.apache.commons.math3.analysis.function.Abs
import org.apache.commons.math3.linear.{ArrayRealVector, RealVector}
import utils.Accuracy

case class MatchingPursuit1D(x: Seq[Double], dict: Dictionary) extends MatchingPursuit[Double] {

  private val input: RealVector = new ArrayRealVector(x.toArray)

  override def run(until: Accuracy): (List[(Double, Int)], Seq[Double]) = {

    /**
      * Perform one step of the Matching Pursuit algorithm
      *
      * @param residual The residual at iteration i
      * @param until    The object containing the type of the desired accuracy and the level
      * @param acc      The accumulator
      * @return A list containing the tuple weight-index of each selected atom, as well as the residual
      */
    def step(residual: RealVector, until: Accuracy, acc: List[(Double, Int)]): (List[(Double, Int)], Seq[Double]) = {

      if (until.estimate(input.subtract(residual), input)) {
        (acc, residual.toArray.toSeq)
      } else {
        // Compute the inner product of x at iteration i and every atom in the dictionary dict
        val nextCorr: RealVector = this.dict.atoms.transpose().operate(residual).map(new Abs)
        // Find the most correlated weighted atom from the dictionary
        val idx: Int = nextCorr.getMaxIndex
        // Weight the selected atom and normalize
        val norm: Double = this.dict.atoms.getColumnVector(nextCorr.getMaxIndex).getNorm
        val weight: Double = nextCorr.getMaxValue / (norm * norm)
        val nextBestGuess: (Double, Int) = (weight, idx)
        val nextAcc: List[(Double, Int)] = acc.::(nextBestGuess)
        // Multiply the selected atom by the weight
        val weightedAtom: RealVector = this.dict.atoms.getColumnVector(nextBestGuess._2).mapMultiply(weight)
        step(residual.subtract(weightedAtom), until, nextAcc)
      }
    }

    step(input, until, List.empty)
  }
}
