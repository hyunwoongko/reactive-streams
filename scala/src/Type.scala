/**
  * @Author : Hyunwoong 
  * @When : 2018-11-04 오후 3:15
  * @Homepage : https://github.com/gusdnd852
  */
object Type {
  def main(args: Array[String]): Unit = {
    type R = String
    type Q = Int

    val typeString: R = "타입의 별칭을 지어줄 수 있다"
    val typeNum: Q = 50

    println(typeString + " , " + typeNum)
  }
}
