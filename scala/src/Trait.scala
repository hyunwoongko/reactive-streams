/**
  * @Author : Hyunwoong 
  * @When : 2018-11-04 오후 12:57
  * @Homepage : https://github.com/gusdnd852
  */
trait Trait {
  // 스칼라의 인터페이스.
  def absFunc()

  def defaultFunc(): Unit = {
    println("default Function")
  } // 자바 인터페이스의 디폴트메소드처럼
  // 인터페이스 내부에서도 구현 가능.
}

trait SecondTrait {
  def sec(): Unit = {
    println("second")
  }
}

class Clazz extends Trait with SecondTrait {
    // 두번쨰부터 인터페이스를 받아드릴땐 with 키워드 사용
  override def absFunc(): Unit = {
    println("abstract Function")
  }

  override def toString: String = super.toString + "투스트링"

}

object TraitMain {
  def main(args: Array[String]): Unit = {
    val clz = new Clazz()
    clz.absFunc()
    clz.defaultFunc()
    clz.sec()

    println(clz.toString())
  }
}
