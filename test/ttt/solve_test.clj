(ns ttt.solve-test
  (:require [expectations :refer :all] 
            [ttt.solve :refer :all]))


(def test-1  {:board  [:x :x :x
                       :o :o :x
                       :x :x :x]
              :to-move :x
              :moves-made 0
              })
(def test-2  {:board  [:- :- :x
                       :o :o :x
                       :x :o :-]
              :to-move :x
              :moves-made 0
              })
(def test-3  {:board  [:- :- :x
                       :o :o :o
                       :x :x :-]
              :to-move :x
              :moves-made 0
              })

(expect 100 (minimax test-1))
(expect -100 (minimax test-3))

;(expect [0 1 8] (gen-moves test-2))
;(expect [] (gen-moves test-1))

(def test-4  {:board  [:x :o :o
                       :- :x :-
                       :x :- :-]
              :to-move :o
              :moves-made 0
              })
(def test-5  {:board  [:x :- :x
                       :o :x :-
                       :o :- :-]
              :to-move :o
              :moves-made 0
              })
(def test-6  {:board  [:o :o :x
                       :- :x :-
                       :- :- :-]
              :to-move :o
              :moves-made 0
              })

(expect 98 (minimax test-4))
(expect 0 (minimax new-game)) 

(expect 8 (best-move test-5))

(expect 6 (best-move test-6))

(expect 1 (:moves-made (make-move 1 new-game)))
