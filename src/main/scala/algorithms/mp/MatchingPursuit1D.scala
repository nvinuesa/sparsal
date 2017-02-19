package algorithms.mp

import dictionaries.Dictionary
import org.apache.commons.math3.analysis.function.Abs
import org.apache.commons.math3.linear.{ArrayRealVector, RealMatrix, RealVector}
import utils.Accuracy

case class MatchingPursuit1D(x: Seq[Double], dict: Dictionary) extends MatchingPursuit[Double] {

  private val input: RealVector = new ArrayRealVector(x.toArray)

  /**
    * Compute the inner product of x at iteration i and every atom ω in the dictionary dict
    *
    * @param xi The sequence x at iteration i
    * @return The correlation matrix between x at iteration i and every atom ω in the dictionary
    */
  private def corr(xi: RealVector): RealVector = {

    this.dict.atoms.transpose().operate(xi)
  }

  /**
    * Perform one step of the Matching Pursuit algorithm
    *
    * @param residual The residual at iteration i
    * @param until    The object containing the type of the desired accuracy and the level
    * @return The residual at iteration i
    */
  private def step(residual: RealVector, until: Accuracy, acc: List[(Double, Int)]): (List[(Double, Int)], Seq[Double]) = {

    if (until.estimate(input.subtract(residual), input)) {
      (acc, residual.toArray.toSeq)
    } else {
      val nextCorr = this.corr(residual).map(new Abs)
      //Find the most correlated weighted atom from the dictionary
      val nextBestGuess: (Double, Int) = (nextCorr.getMaxValue, nextCorr.getMaxIndex)
      val nextAcc: List[(Double, Int)] = acc.::(nextBestGuess)
      // Multiply the selected atom by the weight
      val weightedAtom: RealVector = this.dict.atoms.getColumnVector(nextBestGuess._2).mapMultiply(nextBestGuess._2)
      step(residual.subtract(weightedAtom), until, nextAcc)
    }
  }

  /**
    * Run the matching pursuit algorithm. The algorithm is run until the desired level of accuracy (or number of iterations) is reached.
    *
    * @param until The object containing the type of the desired accuracy and the level
    * @return The residual sequence
    */
  override def run(until: Accuracy): (List[(Double, Int)], Seq[Double]) = {

    this.step(input, until, List.empty)
  }
}
