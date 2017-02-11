package algorithms

import dictionaries.Dictionary
import utils.Accuracy
import utils.Util._

import scala.collection.parallel.ParSeq


class MatchingPursuit[T](val x: Seq[T], val dict: Dictionary[T]) {

  /**
    * Compute the inner product of x at iteration i and every atom ω in the dictionary dict
    *
    * @param xi The sequence x at iteration i
    * @return The correlation matrix between x at iteration i and every atom ω in the dictionary
    */
  private def corr(xi: ParSeq[T])(implicit num: Numeric[T]): ParSeq[T] = {

    this.dict.atoms map (ω => dot(xi, ω))
  }

  /**
    * Find the most correlated weighted atom ω from the dictionary with x at iteration i (from corr)
    *
    * @param corr The correlation matrix between x at iteration i and every atom ω in the dictionary
    * @return The best matching weighted atom index and its weight
    */
  private def bestGuess(corr: ParSeq[T])(implicit num: Numeric[T]): (T, Int) = {

    import num._
    val absCorr = corr map abs
    val α: T = absCorr reduceLeft max
    val idx = absCorr.indexOf(α)
    (corr(idx), idx)
  }

  /**
    * Apply a weight α to an atom ω
    *
    * @param αω The tuple of the weight and the atom
    * @return The weighted atom
    */
  private def weightAtom(αω: (T, ParSeq[T]))(implicit num: Numeric[T]): ParSeq[T] = {

    import num._
    αω._2 map (_ * αω._1)
  }

  /**
    * Perform one step of the Matching Pursuit algorithm
    *
    * @param residual The residual at iteration i
    * @param until    The object containing the type of the desired accuracy and the level
    * @return The residual at iteration i
    */
  private def step(residual: ParSeq[T], until: Accuracy[T], acc: List[(T, Int)])(implicit num: Numeric[T]): (List[(T, Int)], ParSeq[T]) = {

    if (until.estimate(subtraction(x.par, residual), x.par)) {
      (acc, residual)
    } else {
      val nextCorr: ParSeq[T] = this.corr(residual)
      val nextBestGuess: (T, Int) = this.bestGuess(nextCorr)
      val nextAcc: List[(T, Int)] = acc.::(nextBestGuess)
      step(subtraction(residual, weightAtom((nextBestGuess._1, this.dict.atoms(nextBestGuess._2)))), until, nextAcc)
    }
  }

  /**
    * Run the matching pursuit algorithm. The algorithm is run until the desired level of accuracy (or number of iterations) is reached.
    *
    * @param until The object containing the type of the desired accuracy and the level
    * @return The residual sequence
    */
  def run(until: Accuracy[T])(implicit num: Numeric[T]): (List[(T, Int)], ParSeq[T]) = {

    this.step(x.par, until, List.empty)
  }
}
