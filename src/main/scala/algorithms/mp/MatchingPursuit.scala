package algorithms.mp

import utils.Accuracy

trait MatchingPursuit[T] {

  /**
    * Run the matching pursuit algorithm. The algorithm is run until the desired level of accuracy (or number of iterations) is reached.
    *
    * @param until The object containing the type of the desired accuracy and the level
    * @return The residual sequence
    */
  def run(until: Accuracy): (List[(T, Int)], Seq[T])
}
