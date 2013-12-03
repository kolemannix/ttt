(ns ttt.solve
  (:require [ttt.definitions :refer :all]))

;; Board is represented as a flat vector of keywords
;; and indexed thusly:
;; | 0 | 1 | 2
;; | 3 | 4 | 5
;; | 6 | 7 | 8


(def new-game {:board (vec (repeat 9 :-))
               :to-move :x
               })
(defn- get-all [coll indices]
  (map #(nth coll %) indices))

(defn toggle-move [game] 
  (let [next-val (if (x? (game :to-move)) :o :x)]
    (assoc game :to-move next-val)))

(defn make-move [move game]
  (let [new-board (assoc (game :board) move (game :to-move))]
    (toggle-move (assoc game :board new-board))))

(defn- gen-moves [game]
  (filter identity (map-indexed #(if (blank? %2) %1 nil) (game :board))))

(defn- children [game]
  (map (fn [move] (make-move move game)) (gen-moves game)))

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
(declare max- min-)
(defn minimax-inner [game]
  (let [win (win? game)]
    (cond 
      (= win :x) 100
      (= win :o) -100
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

(def slot-map {:x " x " :o " o " :- "   "})
(def separator "\n---------------\n")
(defn- print-row [row]
  (do 
    (dorun (map (fn [x] (print (str "|" (slot-map x) "|"))) row)) )
  )
(print-row [:x :o :x])
(defn print-game [game]
  (let [rows (partition 3 (game :board))]
    (do
      (print-row (first rows))
      (print separator)
      (print-row (second rows))
      (print separator)
      (print-row (nth rows 2))
      )   
    )
  )
