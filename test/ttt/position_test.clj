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
                       :x :x :-]
              :to-move :x})

(expect true (win? test-1))
(expect nil (win? test-2))
