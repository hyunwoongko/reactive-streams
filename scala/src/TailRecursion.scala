import scala.annotation.tailrec

/**
  * @Author : Hyunwoong 
  * @When : 2018-11-04 오후 3:09
  * @Homepage : https://github.com/gusdnd852
  */
class TailRecursion {
  @tailrec // 포문보다 재귀가 인간의 사고와 더 잘맞아.
  final def factorial(n: BigInt, result: BigInt = 1): BigInt = {
    if (n == 1) result
    else factorial(n - 1, n * result)
  }
}

object TailMain {
  def main(args: Array[String]): Unit = {
    val tail = new TailRecursion()
    val result = tail.factorial(5, 2)
    println(result)
  }
}
