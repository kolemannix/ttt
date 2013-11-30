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

(defn toggle-move [game] 
  (let [next-val (if (= (game :to-move) :x) :o :x)]
    (assoc game :to-move next-val)))

(defn make-move [move game]
  (let [new-board (assoc (game :board) move (game :to-move))]
    (toggle-move (assoc game :board new-board))))

(defn win? 
  ([game]
   (or (win? game :x) (win? game :o))) 
  ([game player]
   (some true? (map #(every? (partial = player) (get-all (game :board) %)) victory-sets))))
