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

  test("Sparse decomposition of sine wave") {
    val orig: Seq[Double] = sine(1000, 1, 48000, 480)
    val dict = new Gabor(orig)
    val a = new MatchingPursuit[Double](orig, dict)
    val snr = new SNR[Double](4.0)
    val b = a.run(snr)

    assert(b._2.length == orig.length)
    assert(snr.estimate(subtraction(orig, b._2), orig))
  }

  def sine(f: Double, amp: Double, samplef: Double, samples: Int): IndexedSeq[Double] = {
    val wavePeriod = 1 / f
    val samplePeriod = 1 / samplef
    val dTheta = (samplePeriod / wavePeriod) * 2 * math.Pi

    (0 until samples).map(n => math.sin(dTheta * n) * amp)
  }
}
