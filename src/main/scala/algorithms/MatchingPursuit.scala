package algorithms

import dictionaries.Dictionary
import utils.Accuracy
import utils.Util._

class MatchingPursuit[T](val x: Seq[T], val dict: Dictionary[Seq[T]]) {

  /**
    * Compute the inner product of x at iteration i and every atom ω in the dictionary dict
    *
    * @param xi The sequence x at iteration i
    * @return The correlation matrix between x at iteration i and every atom ω in the dictionary
    */
  private def corr(xi: Seq[T])(implicit num: Numeric[T]): Seq[T] = {

    this.dict.Θ map (ω => dot(xi, ω))
  }

  /**
    * Find the most correlated weighted atom ω from the dictionary with x at iteration i (from corr)
    *
    * @param corr The correlation matrix between x at iteration i and every atom ω in the dictionary
    * @return The best matching weighted atom
    */
  private def bestGuess(corr: Seq[T])(implicit num: Numeric[T]): (T, Seq[T]) = {

    import num._
    val α: T = (corr map (x => abs(x))).reduceLeft(max)
    (α, this.dict.getAtom(corr.indexOf(α)))
  }

  /**
    * Apply a weight α to an atom ω
    *
    * @param αω The tuple of the weight and the atom
    * @return The weighted atom
    */
  private def weightAtom(αω: (T, Seq[T]))(implicit num: Numeric[T]): Seq[T] = {

    import num._
    αω._2 map (_ * αω._1)
  }

  /**
    * Perform one step of the Matching Pursuit algorithm
    *
    * @param xi The input x at iteration i
    * @return The residual at iteration i
    */
  private def step(xi: Seq[T])(implicit num: Numeric[T]): Seq[T] = {

    subtraction(xi, weightAtom(bestGuess(corr(xi))))
  }

  /**
    * Run the matching pursuit algorithm. The algorithm is run until the desired level of accuracy (or number of iterations) is reached.
    *
    * @param current The estimated sequence (empty sequence for the first iteration)
    * @param until   The object containing the type of the desired accuracy and the level
    * @return The residual sequence
    */
  def run(current: Seq[T], until: Accuracy[T])(implicit num: Numeric[T]): Seq[T] = {

    if (until.estimate(current, x)) {
      current
    } else {
      run(step(subtraction(x, current)), until)
    }
  }
}
