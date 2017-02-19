package utils

import org.apache.commons.math3.linear.RealVector

import scala.math.log10

/**
  * Methods for assessing accuracy between estimated and original signals
  */
trait Accuracy {

  /**
    * The method to estimate if the desired level of accuracy is reached
    *
    * @param current  The input to check the estimated level
    * @param original The original signal as reference
    * @return True if the desired level of accuracy is reached
    */
  def estimate(current: RealVector, original: RealVector): Boolean
}

/**
  * Check if maximum iterations are reached
  *
  * @param n The desired number of iterations
  */
case class MaxIter(n: Int) {

  /**
    * The method to check if the desired number of iterations is reached
    *
    * @param current The input to check the estimated level
    * @return True if the desired level of accuracy is reached
    */
  def estimate(current: Int): Boolean = {

    !(current < n)
  }
}

/**
  * Check if the signal-to-noise ratio (in dB) is reached
  *
  * @param level The desired signal-to-noise ratio (in dB)
  */
case class SNR(level: Double) extends Accuracy {

  /**
    * The method to check if the desired level of signal-to-noise ratio (in dB) is reached
    *
    * @param current  The input to check the estimated level
    * @param original The original signal as reference
    * @return True if the desired level of accuracy is reached
    */
  override def estimate(current: RealVector, original: RealVector): Boolean = {

    val origSq = original.dotProduct(original)
    val diffSq = original.dotProduct(original.subtract(current))

    val result = 10 * log10(origSq - diffSq)
    result >= level
  }
}

case class MSE[A](level: Double) extends Accuracy {

  /**
    * The method to estimate if the desired level of mean squared error (MSE) is reached
    *
    * @param current  The input to check the estimated level
    * @param original The original signal as reference
    * @return True if the desired level of accuracy is reached
    */
  override def estimate(current: RealVector, original: RealVector): Boolean = ???
}
