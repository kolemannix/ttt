(ns ttt.console
  (:require [ttt.solve :refer :all])
  (:gen-class)
  )

(def slot-map {:x " x " :o " o " :- "   "})
(def separator "\n---------------\n")
(defn- print-row [row]
  (do 
    (dorun (map (fn [x] (print (str "|" (slot-map x) "|"))) row)) )
  )

(defn print-game [game]
  (let [rows (partition 3 (game :board))]
    (do
      (print-row (first rows))
      (print separator)
      (print-row (second rows))
      (print separator)
      (print-row (nth rows 2))
      (println)
      )   
    )
  ) 

(def username (atom "player"))

(defn -main []
  (loop [game new-game]
    (print-game game)
    (print "Make a move: (1-9) ")
    (let [input (read-line)
          move (- (Integer/parseInt input) 1)
          updated (make-move move game)
          ]
      (case (win? updated)
        :x (println "X wins!")
        :o (println "O wins!")
        :- (println "It's a draw!")
        nil nil
        )
      (recur updated)
      )
    )
  ) 
