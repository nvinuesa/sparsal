package algorithms.omp

import utils.Accuracy

trait OrthogonalMatchingPursuit[T] {

  /**
    * Run the Orthogonal Matching Pursuit algorithm. The algorithm is run until the desired level of accuracy (or number of iterations) is reached or until the
    * decomposition contains every atom in the dictionary.
    *
    * @param until The object containing the type of the desired accuracy and the level
    * @return The residual sequence
    */
  def run(until: Accuracy): (List[(T, Int)], Seq[T])
}
