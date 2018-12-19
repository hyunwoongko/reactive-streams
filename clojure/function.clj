(ns clojure.function)

(defn hello [arg1, arg2]
  (print arg1)
  (print arg2)
  )
; 함수 생성


(hello "a" "s")

(fn [a b] (+ a b))
#(+ %1 %2)
; 둘다 익명함수
