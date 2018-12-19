/**
  * @Author : Hyunwoong 
  * @When : 2018-11-04 오후 3:19
  * @Homepage : https://github.com/gusdnd852
  */
object AnonymousFunction extends App {

  def dob(x: Int) = x + x

  val a = dob(5)
  println(a)
  // 기존방식

  ((x: Int) => println(x * x)) (3)
  // 익명함수 1

  (1 to 30)
    .filter(n => n % 2 == 0)
    .foreach(n => print(n + " "))
  // 자바 스트림과 비슷

  println()
  (0 until 10 by 2)
    .foreach(println)
}