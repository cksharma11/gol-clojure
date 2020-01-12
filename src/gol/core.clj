(ns gol.core)

;Any live cell with fewer than two live neighbours dies, as if by underpopulation.
;Any live cell with two or three live neighbours lives on to the next generation.
;Any live cell with more than three live neighbours dies, as if by overpopulation.
;Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
;These rules, which compare the behavior of the automaton to real~~ life, can be condensed into the following:
;
;Any live cell with two or three neighbors survives.
;Any dead cell with three live neighbors becomes a live cell.
;All other live cells die in the next generation. Similarly, all other dead cells stay dead.

(def live-cells #{[2 1] [2 2] [2 3]})
(def neighbour-deltas (for [x [-1 0 1] y [-1 0 1] :when (not= x y 0)] [x y]))
(defn find-neighbours [cell] (mapv #(mapv + cell %1) neighbour-deltas))

(defn neighbours-count [live-cells]
  (frequencies (mapcat find-neighbours live-cells)))

(defn determine-alive-cells [live-cells]
  (map first (filter #(> (second %) 2) (neighbours-count live-cells))))

(defn determine-dead-cells [live-cells]
  (map first (filter #(or (> (second %) 3) (< (second %) 2)) (neighbours-count live-cells))))

(defn next-generation [live-cells]
  (apply conj
         (apply disj live-cells
                (determine-dead-cells live-cells))
         (determine-alive-cells live-cells)))

;(next-generation live-cells)
(take 5 (iterate next-generation live-cells))

(#{[2 2] [2 3] [2 1]}
 #{[2 2] [1 2] [3 2]}
 #{[2 2] [2 3] [2 1]}
 #{[2 2] [1 2] [3 2]}
 #{[2 2] [2 3] [2 1]})
