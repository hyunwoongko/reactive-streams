/**
  * @Author : Hyunwoong 
  * @When : 2018-11-04 오후 1:08
  * @Homepage : https://github.com/gusdnd852
  */

object defs {
  def add(a: Int, b: Int): Int = a + b

  // 이런식으로 함수를 정의함.
  // 함수를 마치 변수나 객체 선언하듯이 선언하는게 특징

  def sub(a: Int, b: Int): Int = {
    if (a > b) a - b
    else b - a
  } // 기존방식으로 코딩하기
  // 특징은 return을 사용하지 않는다는 것.
  // 그냥 값으로 놔두면 그게 리턴됨.
}

object FuncMain extends App {
  var adder = defs.add(5, 4)
  println(adder)

  var subber = defs.sub(9, 11)
  println(subber)
}

