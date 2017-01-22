package utils

import scala.math._

/**
  * Utility methods
  */
object Util {

  /**
    * Vector operation
    *
    * @param as First sequence
    * @param bs Second sequence
    * @param f  Function to be applied
    * @return The resulting sequence
    */
  def op[T](as: Seq[T], bs: Seq[T])(f: (T, T) => T): Seq[T] = {

    require(as.size == bs.size, "Both sequences must have the same length for binary operations.")
    (as zip bs) map {
      Function.tupled(f)
    }
  }

  /**
    * Compute the dot product between two sequences
    *
    * @param as First sequence
    * @param bs Second sequence
    * @return The dot product
    */
  def dot[T](as: Seq[T], bs: Seq[T])(implicit num: Numeric[T]): T = {

    import num._
    op(as, bs)(_ * _).sum
  }

  /**
    * Compute the addition between two sequences
    *
    * @param as First sequence
    * @param bs Second sequence
    * @return The addition sequence
    */
  def addition[T](as: Seq[T], bs: Seq[T])(implicit num: Numeric[T]): Seq[T] = {

    import num._
    op(as, bs)(_ + _)
  }

  /**
    * Compute the subtraction between two sequences
    *
    * @param as First sequence
    * @param bs Second sequence
    * @return The subtraction sequence
    */
  def subtraction[T](as: Seq[T], bs: Seq[T])(implicit num: Numeric[T]): Seq[T] = {

    import num._
    op(as, bs)(_ - _)
  }

  /**
    * Compute the p-norm on a sequence
    *
    * @param as The sequence
    * @param p  The power p
    * @return The p-norm
    */
  def pNorm[T](as: Seq[T], p: T)(implicit num: Numeric[T]): T = {

    import num._

    require(p.toDouble() >= 1.0)
    val result = pow((as map (x => pow(abs(x).toDouble(), p.toDouble()))).sum, 1 / p.toDouble())
    (p match {
      case _: Double => result
      case _: Int => result.toInt
      case _: Float => result.toFloat
      case _: Long => result.toLong
    }).asInstanceOf[T]
  }
}
