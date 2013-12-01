(ns ttt.position-test
  (:require [expectations :refer :all] 
            [ttt.position :refer :all]))

(def new-game  {:board  (vec  (repeat 9 :-))
                :to-move :x}) 

(expect :o ((toggle-move new-game) :to-move))
(expect :x ((toggle-move (toggle-move new-game)) :to-move))

(def test-1  {:board  [:x :x :x
                       :o :o :x
                       :x :x :x]
              :to-move :x})
(def test-2  {:board  [:- :- :x
                       :o :o :x
                       :x :o :-]
              :to-move :x})
(def test-3  {:board  [:- :- :x
                       :o :o :o
                       :x :x :-]
              :to-move :x})

(expect :x (win? test-1))
(expect nil (win? new-game))

(expect 100 (minimax test-1))
(expect -100 (minimax test-3))

(expect [0 1 8] (gen-moves test-2))
(expect [] (gen-moves test-1))

(def test-4  {:board  [:x :o :o
                       :- :x :-
                       :x :- :-]
              :to-move :o})
(expect 100 (minimax test-4))

(children new-game)
;(expect 0 (minimax new-game)) ; this takes 16 seconds

