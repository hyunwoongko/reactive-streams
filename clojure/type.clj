(ns clojure.type)

(println \c)
; 캐릭터 타입은 역슬래시 하고 뒤에 문자 1개를 적어주면 됨
; 자바의 'c' 와 동일

(println "스트링은 요렇게")
; 스트링 타입

(println (str \h \e \l \l \o))
; 캐릭터를 스트링으로 변환하기

(println :키워드타입)
; 자바 intern()와 비슷
; 정적인 String 필드를 만드는것과 같음

(keyword "키워드로 변환")
; 스트링을 키워드로 변환하기

'(1 2 3 4)
(list 1 2 3 4)
; 둘다 리스트 타입

[1 2 3 4 5]
(vector 1 2 3 4)
; 벡터 타입

#(1 2 3 3 3 3 1)
; 셋 타입

{:key1 "va1" :key2 "val2"}
; 맵 타입
