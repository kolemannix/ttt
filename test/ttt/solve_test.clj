(ns ttt.solve-test
  (:require [midje.sweet :refer :all] 
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

(fact (minimax test-1) => 100)
(fact (minimax test-3) => -100)

(def test-4  {:board  [:x :o :o
                       :- :x :-
                       :x :- :-]
              :to-move :o
              :moves-made 0})
(def test-5  {:board  [:x :- :x
                       :o :x :-
                       :o :- :-]
              :to-move :o
              :moves-made 0})
(def test-6  {:board  [:o :o :x
                       :- :x :-
                       :- :- :-]
              :to-move :o
              :moves-made 0})

(fact (minimax test-4) => 98)
(fact (minimax new-game) => 0) 

(fact (best-move test-5) => 8)

(fact (best-move test-6) => 6)

(fact (:moves-made (make-move 1 new-game)) => 1)
