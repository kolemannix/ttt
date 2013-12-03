(ns ttt.console
  (:require [ttt.solve :refer :all])
  (:gen-class)
  )



(def username (atom "player"))
(defn -main []
  (print-game newgame)
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
      )   
    )
  )
