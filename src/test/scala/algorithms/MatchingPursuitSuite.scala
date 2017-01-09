package algorithms

import dictionaries.Dictionary
import org.scalatest.FunSuite
import utils.SNR

class MatchingPursuitSuite extends FunSuite {

  test("Matching Pursuit should converge") {
    val a = new MatchingPursuit[Double](Vector(1, 2, 3), new Dictionary[Seq[Double]](Vector(Vector(1.0 / 3.7417, 2.0 / 3.7417, 3.0 / 3.7417), Vector(1.0 / 1.7321, 1.0 / 1.7321, 1.0 / 1.7321))))
    val snr =  new SNR[Double](-50.0)
    val b = a.run(Vector.fill(3){0.0}, snr)

    assert(b.length == 3)
  }
}
