/**
  * @Author : Hyunwoong 
  * @When : 2018-11-04 오후 1:03
  * @Homepage : https://github.com/gusdnd852
  */
abstract class Abstract {
  var vari = "variable"

  def absMethod(): Unit
  // 추상클래스에서 이렇게 메소드명만 선언해주면 추상메소드가됨.
  // 따로 abstract 키워드 안붙여도됨. -> 오직 클래스에만 붙일수 있음.
}

class Clz extends Abstract {
  override def absMethod(): Unit = println("hello world")
}

object AbstractMain extends App {
  val clz = new Clz()
  clz.absMethod()
}
