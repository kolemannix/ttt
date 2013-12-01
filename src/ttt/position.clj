(ns ttt.position)

;; Board is represented as a flat vector of keywords
;; and indexed thusly:
;; | 0 | 1 | 2
;; -------------
;; | 3 | 4 | 5
;; --------------
;; | 6 | 7 | 8

(def top [0 1 2])
(def middle [3 4 5])
(def bottom [6 7 8])
(def left [0 3 6])
(def center [1 4 7])
(def right [2 5 8])
(def pos [0 4 8])
(def neg [2 4 6])
(def rows #{top middle bottom})
(def cols #{left center right})
(def diags #{pos neg})
(def victory-sets (clojure.set/union rows cols diags))

(defn get-all [coll indices]
  (map #(nth coll %) indices))

(get-all [:x :x :o, :o :o :x, :- :- :0] [0 4 8])
(defn toggle-move [game] 
  (let [next-val (if (= (game :to-move) :x) :o :x)]
    (assoc game :to-move next-val)))

(defn make-move [move game]
  (let [new-board (assoc (game :board) move (game :to-move))]
    (toggle-move (assoc game :board new-board))))

(defn gen-moves [game]
  (filter identity (map-indexed #(if (= %2 :-) %1 nil) (game :board))))

(defn children [game]
  (map (fn [move] (make-move move game)) (gen-moves game)))

(defn tie? [game]
  )

;; win? will return
;; :x if x team won the game
;; :o if o team won the game
;; :- if the game ended as a tie
;; nil if the game is not over
(defn win? 
  ([game]
   (cond
     (win? game :x) :x 
     (win? game :o) :o
     (empty? (filter (partial = :-) (game :board))) :-  ;; no moves left
     :else nil)) ;; game isn't over
  ([game player]
   (some true? (map #(every? (partial = player) (get-all (game :board) %)) victory-sets))))
(declare max- min-)
(defn minimax [game]
  (println (game :board))
  (let [win (win? game)]
    (cond 
      (= win :x) 100
      (= win :o) -100
      (= win :-) 0
      (= win nil)
      (if (= (game :to-move) :x)
        (max- game)
        (min- game)))))

(defn max- [game]
  (println "max called")
  (let [results (map minimax (children game))]
    (apply max results)))
(defn min- [game]
  (let [results (map minimax (children game))]
    (apply min results)))
