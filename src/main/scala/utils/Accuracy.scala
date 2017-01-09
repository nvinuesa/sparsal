package utils

import scala.math.Numeric
import scala.math.log10
import utils.Util._

/**
  * Methods for assessing accuracy between estimated and original signals
  */
trait Accuracy[T] {

  /**
    * The method to estimate if the desired level of accuracy is reached
    *
    * @param current  The input to check the estimated level
    * @param original The original signal as reference
    * @return True if the desired level of accuracy is reached
    */
  def estimate(current: Seq[T], original: Seq[T])(implicit num: Numeric[T]): Boolean
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
case class SNR[A](level: Double) extends Accuracy[A] {

  /**
    * The method to check if the desired level of signal-to-noise ratio (in dB) is reached
    *
    * @param current  The input to check the estimated level
    * @param original The original signal as reference
    * @return True if the desired level of accuracy is reached
    */
  override def estimate(current: Seq[A], original: Seq[A])(implicit num: Numeric[A]): Boolean = {

    import num._
    val origSq = dot(original map (x => toDouble(x)), original map (x => toDouble(x)))
    val diffSq = dot(original map (x => toDouble(x)), subtraction(original map (x => toDouble(x)), current map (x => toDouble(x))))
    val result = 10 * log10(origSq - diffSq)
    result >= level
  }
}

case class MSE[A](level: Double) extends Accuracy[A] {

  /**
    * The method to estimate if the desired level of mean squared error (MSE) is reached
    *
    * @param current  The input to check the estimated level
    * @param original The original signal as reference
    * @return True if the desired level of accuracy is reached
    */
  override def estimate(current: Seq[A], original: Seq[A])(implicit num: Numeric[A]): Boolean = ???
}
