/**
  * @Author : Hyunwoong 
  * @When : 2018-11-04 오후 12:50
  * @Homepage : https://github.com/gusdnd852
  */
object Variable extends App {
  //  App을 extends 해주면 main method 필요 없음.
  // 세미콜론 안씀. 한줄에 두개의 함수 호출하고싶으면 그 때 쓰는데 보통 안씀

  var number = 50
  number = 40
  // 가변변수 var

  print(number)
}


object Value {
  def main(args: Array[String]): Unit = {
    // main을 사용해도 됨.

    val number = 80
    // final 밸류 val

    print(number)
  }
}