package dictionaries

import org.apache.commons.math3.linear.{Array2DRowRealMatrix, RealMatrix, RealVector}

import scala.math._

trait Dictionary {

  /**
    * The dictionary of atoms
    */
  val atoms: RealMatrix
}

/**
  * The object containing the overcomplete Gabor dictionary as presented in:
  * Mallat SG, Zhang Z. Matching pursuits with time-frequency dictionaries. IEEE Transactions on signal processing.
  * 1993 Dec;41(12):3397-415.
  *
  * Since this dictionary creates all the possible atoms for a given input sequence, it is needed as a parameter.
  */
class Gabor(length: Int) extends Dictionary {

  /**
    * Constants needed to build the sampled dictionary
    */
  private val N: Int = length
  private val a: Int = 2
  private val Δu: Double = 0.5
  private val Δξ: Double = Pi
  private val matrix: RealMatrix = new Array2DRowRealMatrix(N, 4*N*(log(N)/log(2)).toInt)

  private def basis(u: Int, ω: Int, s: Int): Int => Double =
    n => exp(-Pi * pow((n - u).toDouble / s.toDouble, 2)) * sin(2.0 * Pi * (ω.toDouble / N.toDouble) * (n - u).toDouble)

  /**
    * Dyadic sampling of the basis function to generate the dictionary
    */
  override val atoms: RealMatrix = {

    var i: Int = 0
    for {
      j: Int <- 1 until (log(N) / log(2)).toInt
      p: Int <- 0 until (N * pow(2, -j + 1)).toInt
      k: Int <- 0 until (1 * pow(2, j + 1)).toInt
    } yield {
      for {
        n: Int <- 0 until N
      } yield {
        matrix.setEntry(n, i, basis((p * pow(a, j) * Δu).toInt, (k * pow(a, -j) * Δξ).toInt, pow(a, j).toInt)(n))
      }
      // Each atom must have unit norm
      val col: RealVector = matrix.getColumnVector(i)
      matrix.setColumnVector(i, col.mapMultiply(col.getNorm))
      // Update index
      i += 1
    }
    matrix
  }
}

