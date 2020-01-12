(ns gol.core)

(def live-cells #{[2 1] [2 2] [2 3]})
(def neighbour-deltas (for [x [-1 0 1] y [-1 0 1] :when (not= x y 0)] [x y]))

(defn find-neighbours [cell] (mapv #(mapv + cell %1) neighbour-deltas))
(defn neighbours-frequency [live-cells]
  (frequencies (mapcat find-neighbours live-cells)))

(defn exact-three-neighbours [neighbours-count] (= neighbours-count 3))
(defn more-than-three-neighbours [neighbours-count] (> neighbours-count 3))
(defn less-than-two-neighbours [neighbours-count] (< neighbours-count 2))

(defn any-rule-matching? [rules [_ freq]] ((apply some-fn rules) freq))

(defn filter-by-rules [rules live-cells]
  (map first (filter (partial any-rule-matching? rules) (neighbours-frequency live-cells))))

(defn determine-alive-cells [live-cells]
  (filter-by-rules [exact-three-neighbours] live-cells))

(defn determine-dead-cells [live-cells]
  (filter-by-rules [more-than-three-neighbours less-than-two-neighbours] live-cells))

(defn next-generation [live-cells]
  (apply conj
         (apply disj live-cells
                (determine-dead-cells live-cells))
         (determine-alive-cells live-cells)))

(defn nth-generation [current-generation gen-number]
  (nth (iterate next-generation current-generation) (inc gen-number)))

(nth-generation live-cells 5)
