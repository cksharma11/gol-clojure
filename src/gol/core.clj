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

(def live-cells #{[0 1] [1 1] [1 0]})
(def neighbour-deltas (for [x [-1 0 1] y [-1 0 1] :when (not= x y 0)] [x y]))
(defn find-neighbours [cell] (mapv #(mapv + cell %1) neighbour-deltas))

(apply conj live-cells
       (map first
            (filter #(> (second %) 2)
                    (frequencies (mapcat find-neighbours live-cells)))))

(apply disj live-cells
       (map first
            (filter #(> (second %) 3)
                    (frequencies (mapcat find-neighbours live-cells)))))