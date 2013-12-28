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
      separator
      ))) 

(defn- exit-with-result [result]
  (case result
    :x (println "X wins!")
    :o (println "O wins!")
    :- (println "Tie!"))
  (System/exit 0)
  )

(defn -main []
  (loop [game new-game]  
    (do 
      (println (to-string game))
      (println "Make a move: (1-9) "))
    (if-let [result (game :result)] 
      (exit-with-result result)
      (recur 
            (let [input (read-line)
                  move (- (Integer/parseInt input) 1)
                  updated (make-move move game)
                  ]
              (assoc updated :result (win? updated))))))
  ) 
