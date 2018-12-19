; 주석은 세미콜론으로도 가능하고
(comment "이런식으로 comment 키워드를 이용해 여러줄 주석도 가능
주석 주석 주석")

(println '(1 2 3))
; 띠옴표를 붙이면 가장 앞 요소를 오퍼레이터가 아니라 그자체로 봄

^"meta data"
(list 1 2 3)
; ^를 쓰고 메타데이터를 붙여줄수 있음

(println (new Object))
; 자바 객체 생성

(.toString "hello")
; 자바 메소드 호출

(if 1 > 3)
(println 12)