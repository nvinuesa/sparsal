package algorithms

import algorithms.mp.MatchingPursuit1D
import dictionaries.Gabor
import org.scalatest.FunSuite
import utils._

class MatchingPursuitSuite extends FunSuite {

  test("Matching Pursuit should converge") {
    val orig = Vector.fill(512)(1.0)
    val dict = new Gabor(orig.size)
    val a = MatchingPursuit1D(orig, dict)
    val snr = SNR(20.0)
    val b = a.run(snr)

    assert(b._2.length == orig.length)
    //assert(snr.estimate(subtraction(orig, b._2), orig))
  }

  test("Sparse decomposition of sine wave") {
    val orig: Seq[Double] = sine(1000, 1, 48000, 480)
    val dict = new Gabor(orig.size)
    val a = MatchingPursuit1D(orig, dict)
    val snr = SNR(4.0)
    val b = a.run(snr)

    assert(b._2.length == orig.length)
    //assert(snr.estimate(subtraction(orig, b._2), orig))
  }

  def sine(f: Double, amp: Double, samplef: Double, samples: Int): IndexedSeq[Double] = {
    val wavePeriod = 1 / f
    val samplePeriod = 1 / samplef
    val dTheta = (samplePeriod / wavePeriod) * 2 * math.Pi

    (0 until samples).map(n => math.sin(dTheta * n) * amp)
  }
}
