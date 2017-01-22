package algorithms

import dictionaries.{Dictionary, Gabor}
import org.scalatest.FunSuite
import utils._
import utils.Util._

class MatchingPursuitSuite extends FunSuite {

  test("Matching Pursuit should converge") {
    val orig = Vector.fill(10)(1.0)
    val dict = new Gabor(Vector.fill(10)(0.0))
    val a = new MatchingPursuit[Double](orig, dict)
    val snr = new SNR[Double](10.0)
    val b = a.run(snr)

    assert(b._2.length == 10)
    assert(snr.estimate(subtraction(orig, b._2), orig))
  }
}
