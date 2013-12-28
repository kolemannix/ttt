(ns ttt.console
  (:require [ttt.solve :refer :all])
  (:gen-class)
  )

(def slot-map {:x "x" :o "o" :- " "})

(def separator "\n+---+---+---+\n")

(defn- print-row [row]
  (let [pieces (vec (map slot-map row))]
    (str "| " (pieces 0) " | " (pieces 1)  " | " (pieces 2) " |")))

(defn to-string [game]
  (let [rows (partition 3 (game :board))]
    (str 
      separator
      (print-row (first rows))
      separator
      (print-row (second rows))
      separator
      (print-row (nth rows 2))
      separator))) 

(defn- exit-with-result [result]
  (do (case result
        :x (println "X wins!")
        :o (println "O wins!")
        :- (println "Tie!"))
      (System/exit 0)))

(defn -main []
  (println "Player is (x) or (o)? ")
  (def player 
    (let [input (read-line)]
      (case input 
        "x" :x
        "o" :o
        (do 
          (println "type x or o, scrub")
          (System/exit 0)))))
  (loop [game new-game]  
    (println (to-string game))
    (if-let [result (game :result)] 
      (exit-with-result result)
      (do 
        (println (if (= player (game :to-move)) 
                   "Your Turn. Make a move: (1-9) "
                   "My turn. I'm thinking"))
        (recur 
          (let [move 
                (if (= player (game :to-move)) 
                  (let [input-move (- (Integer/parseInt (read-line)) 1)
                        valids (valid-moves game)]
                    (if (contains? valids input-move)
                      input-move
                      (do (println "Illegal Move. Try another one.") -1)))
                  (best-move game))
                next-game (if (= move -1) game (make-move move game))]
            next-game))))))
