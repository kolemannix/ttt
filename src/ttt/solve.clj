(ns ttt.solve
  (:require [ttt.definitions :refer :all]))

;; Board is represented as a flat vector of keywords
;; and indexed thusly:
;; +---+---+---+
;; | 0 | 1 | 2 |
;; +---+---+---+
;; | 3 | 4 | 5 |
;; +---+---+---+
;; | 6 | 7 | 8 |
;; +---+---+---+


(def new-game {:board (vec (repeat 9 :-))
               :to-move :x
               :moves-made 0
               })
(defn- get-all [coll indices]
  (map #(nth coll %) indices))

;; win? will return
;; :x x wins
;; :o o wins
;; :- tie
;; nil not over
(defn win? 
  ([game]
   (cond
     (win? game :x) :x 
     (win? game :o) :o
     (empty? (filter blank? (game :board))) :-  ;; no moves left
     :else nil)) ;; game isn't over
  ([game player]
   (some true? (map #(every? (partial = player) (get-all (game :board) %)) victory-sets))))

(defn toggle-move [game] 
  (let [next-val (if (x? (game :to-move)) :o :x)]
    (assoc game :to-move next-val)))

(defn inc-moves-made [{moves-made :moves-made :as game}]
  (assoc game :moves-made (inc moves-made))
  )

(defn update-result [game]
  (if-let [result (win? game)]
    (assoc game :result result)
    game
    )
  )

(defn make-move [move {:keys [board to-move] :as game}]
  (let [new-board (assoc board move to-move)]
    (-> game 
        (assoc :board new-board)
        (toggle-move)
        (update-result)
        (inc-moves-made))))


(defn- gen-moves [game]
  (filter identity (map-indexed #(if (blank? %2) %1 nil) (game :board))))

(def valid-moves (comp set gen-moves))

(contains? (valid-moves new-game) 1)

(defn- children [game]
  (map (fn [move] (make-move move game)) (gen-moves game)))


(declare max- min-)

(defn- minimax-inner [{depth :moves-made :as game}]
  (let [win (win? game)]
    (cond 
      ;(= win :x) 100
      ;(= win :o) -100
      (= win :x) (- 100 depth)
      (= win :o) (+ -100 depth)
      (= win :-) 0
      (= win nil)
      (if (x? (game :to-move))
        (max- game)
        (min- game)))))

(def minimax (memoize minimax-inner))

(defn- index-of-max [coll]
  (first (apply max-key second (map-indexed vector coll)))
  )
(defn- index-of-min [coll]
  (first (apply min-key second (map-indexed vector coll)))
  )

(defn- minimax-root [game]
  (let [moves (gen-moves game)
        results (map minimax (children game))
        to-move (game :to-move)
        ]
    (if (x? to-move)
      (nth moves (index-of-max results))
      (nth moves (index-of-min results))
      )
    )
  )

(defn best-move [game] (minimax-root game))

(defn max- [game]
  (let [results (map minimax (children game))]
    (apply max results)))
(defn min- [game]
  (let [results (map minimax (children game))]
    (apply min results)))
