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

(def slot-map {:x "x" :o "o" :- " "})

(defrecord Game [board to-move moves-made]
  Object 
  (toString [this]
    (let [rows (partition 3 board)
          separator "\n+---+---+---+\n"
          print-row (fn [row] (let [pieces (vec (map slot-map row))]
                                (str "| " (pieces 0) 
                                     " | " (pieces 1)  
                                     " | " (pieces 2) " |")))
          ]
      (str 
        separator
        (print-row (first rows))
        separator
        (print-row (second rows))
        separator
        (print-row (nth rows 2))
        separator)))  
  )

(def new-game (->Game (vec (repeat 9 :-)) :x 0))

(defn- get-all [coll indices]
  (map #(nth coll %) indices))

(defn win? 
  ":x if x wins, :o if o wins, :- for a tie, and nil for game in progress"
  ([{board :board :as game}]
   (cond
     (win? game :x) :x 
     (win? game :o) :o
     (empty? (filter blank? board)) :-  ;; no moves left
     :else nil)) ;; game isn't over
  ([{board :board :as game} player]
   (some true? (map #(every? (partial = player) (get-all board %)) victory-sets))))

(defn toggle-move [{to-move :to-move :as game}] 
  (let [next-val (if (x? to-move) :o :x)]
    (assoc game :to-move next-val)))

(defn inc-moves-made [{moves-made :moves-made :as game}]
  (assoc game :moves-made (inc moves-made)))

(defn update-result [game]
  (if-let [result (win? game)]
    (assoc game :result result)
    game))

(defn make-move [move {:keys [board to-move] :as game}]
  (let [new-board (assoc board move to-move)]
    (-> game 
        (assoc :board new-board)
        (toggle-move)
        (update-result)
        (inc-moves-made))))

(defn- gen-moves [{board :board}]
  (filter identity (map-indexed #(if (blank? %2) %1 nil) board)))

(def valid-moves (comp set gen-moves))

(contains? (valid-moves new-game) 1)

(defn- children [game]
  (map (fn [move] (make-move move game)) (gen-moves game)))

(declare max- min-)

(defn- minimax-inner [{to-move :to-move depth :moves-made :as game}]
  (let [win (win? game)]
    (case win 
      :x (- 100 depth)
      :o (+ -100 depth)
      :- 0
      (if (x? to-move)
        (max- game)
        (min- game)))))

(def minimax (memoize minimax-inner))

(defn- index-of-max [coll]
  (first (apply max-key second (map-indexed vector coll))))

(defn- index-of-min [coll]
  (first (apply min-key second (map-indexed vector coll))))

(defn- minimax-root [{to-move :to-move :as game}]
  (let [moves (gen-moves game)
        results (map minimax (children game))
        ]
    (if (x? to-move)
      (nth moves (index-of-max results))
      (nth moves (index-of-min results)))))

(defn best-move [game] (minimax-root game))

(defn- max- [game]
  (let [results (map minimax (children game))]
    (apply max results)))
(defn- min- [game]
  (let [results (map minimax (children game))]
    (apply min results)))
