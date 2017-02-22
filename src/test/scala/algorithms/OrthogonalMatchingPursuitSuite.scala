package algorithms

import algorithms.omp.OrthogonalMatchingPursuit1D
import dictionaries.Dictionary
import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.mockito.Matchers._
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalactic.TolerantNumerics
import utils.{Accuracy, SNR}

class OrthogonalMatchingPursuitSuite extends FunSuite with MockitoSugar {

  test ("Stop condition accuracy achieved should return the acc and residual") {

    val accuracy = mock[Accuracy]
    val dict = mock[Dictionary]
    when(accuracy.estimate(any(), any())).thenReturn(true)
    when(dict.atoms).thenReturn(new Array2DRowRealMatrix(1, 1))

    val omp = OrthogonalMatchingPursuit1D(Vector.empty, dict)
    val result: (List[(Double, Int)], Seq[Double]) = omp.run(accuracy)

    assert(result._1.isEmpty)
    assert(Vector.empty.equals(result._2))
  }

  test ("Stop condition all atoms chosen achieved should return the acc and residual") {

    // One fake atom that is also used as input
    val atom: Array[Double] = Array.fill(10)(5)

    val accuracy = mock[Accuracy]
    val dict = mock[Dictionary]
    when(dict.atoms).thenReturn(new Array2DRowRealMatrix(atom))

    val omp = OrthogonalMatchingPursuit1D(atom, dict)
    val result: (List[(Double, Int)], Seq[Double]) = omp.run(accuracy)

    // The expected output:
    val expected: List[(Double, Int)] = List((1.0, 0))
    assert(expected.equals(result._1))

    val epsilon = 1e-4f
    implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(epsilon)
    assert(result._2.sum === 0.0)
  }

  test ("OMP should converge, input made out of two atoms") {

    // Fake atoms that are also used as input
    val atom1: Array[Double] = Array.fill(10)(5)
    val atom2: Array[Double] = Array.fill(5)(1.5) ++ Array.fill(5)(-1.5)
    val atoms: Array[Array[Double]] = Array(atom1, atom2)
    val input: Array[Double] = atom1.zip(atom2).map { case (x, y) => x + y }

    val dict = mock[Dictionary]
    when(dict.atoms).thenReturn(new Array2DRowRealMatrix(atoms).transpose())

    val omp = OrthogonalMatchingPursuit1D(input, dict)
    val result: (List[(Double, Int)], Seq[Double]) = omp.run(SNR(30.0))

    assert(2.equals(result._1.length))
    // Check that both atoms where chosen from the dictionary
    assert(2.equals(result._1.map(_._2).distinct.length))

    val epsilon = 1e-4f
    implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(epsilon)
    assert(result._2.sum === 0.0)
  }
}
