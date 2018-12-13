/**
  * @Author : Hyunwoong 
  * @When : 2018-11-04 오후 1:19
  * @Homepage : https://github.com/gusdnd852
  */
object Container_List extends App {

  private val list1 = List(1, 2, 3, 4)
  private val list2: List[Int] = List.range(0, 10)
  // 리스트의 생성방법이 다양하다.
  private val  zipper: List[Int] = list1 ::: list2
  // zip 연산
  private val  adder: List[Int] = 99 :: list1
  // 리스트 앞에 넣기
  private val  adder2: List[Int] = list1 :+ 999
  // 리스트 뒤에넣기 -> 근데 오래걸린다고한다.
  private val  adder3: List[Int] = (999 :: list1.reverse).reverse
  //리스트 뒤에 넣기 1 2 3 을 뒤집어 3 2 1 로 만들고 앞에 넣으면
  // 999 3 2 1 이된다. 얘를 다시 뒤집으면 1 2 3 999 (뒤에넣기)가 됨.
  private val adder4 = list1 ::: List(999)
  // 리스트에 리스트 추가하기로 뒤에 넣을 수도 있다.

  println(list1)
  println(zipper)
  println(adder)
  println(adder2)
  println(adder3)
  println(adder4)
}
