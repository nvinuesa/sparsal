package dictionaries

import scala.math._
import utils.Util._

trait Dictionary[T] {

  /**
    * The basis function that defines the dictionary
    */
  def basis(u: Int, ω: Int, s: Int): Int => T

  /**
    * The dictionary of atoms
    */
  val atoms: Seq[Seq[T]]
}

/**
  * The object containing the overcomplete Gabor dictionary as presented in:
  * Mallat SG, Zhang Z. Matching pursuits with time-frequency dictionaries. IEEE Transactions on signal processing.
  * 1993 Dec;41(12):3397-415.
  *
  * Since this dictionary creates all the possible atoms for a given input sequence, it is needed as a parameter.
  */
class Gabor(xi: Seq[Double]) extends Dictionary[Double] {

  /**
    * Constants needed to build the sampled dictionary
    */
  private val N: Int = xi.length
  private val a: Int = 2
  private val Δu: Double = 0.5
  private val Δξ: Double = Pi

  override def basis(u: Int, ω: Int, s: Int): Int => Double =
    n => exp(-Pi * pow((n - u).toDouble / s.toDouble, 2)) * sin(2.0 * Pi * (ω.toDouble / N.toDouble) * (n - u).toDouble)


  /**
    * Dyadic sampling of the basis function to generate the dictionary
    */
  override val atoms: Seq[Seq[Double]] =
    for {
      j: Int <- 1 until floor(log(N) / log(2)).toInt
      p: Int <- 0 until (N * pow(2, -j + 1)).toInt
      k: Int <- 0 until pow(2, j + 1).toInt
    } yield {
      val helper =
        for {
          n: Int <- 0 until N
        } yield {
          basis((p * pow(a, j) * Δu).toInt, (k * pow(a, -j) * Δξ).toInt, pow(a, j).toInt)(n)
        }
      // Each atom must have unit norm
      helper map (x => if (pNorm[Double](helper, 2) == 0.0) 0.0 else x / pNorm[Double](helper, 2))
    }
}

