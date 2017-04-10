(ns solitaire-web.solitaire-panel.coordinate-helper)

(defn x [index-in-pile]
  (* 130 index-in-pile))

(def placeholders
    [{:pile-name :stock             :x 0     :y 0}
     {:pile-name :foundation-1      :x (x 3) :y 0}
     {:pile-name :foundation-2      :x (x 4) :y 0}
     {:pile-name :foundation-3      :x (x 5) :y 0}
     {:pile-name :foundation-4      :x (x 6) :y 0}
     {:pile-name :tableau-1-face-up :x (x 0) :y 150}
     {:pile-name :tableau-2-face-up :x (x 1) :y 150}
     {:pile-name :tableau-3-face-up :x (x 2) :y 150}
     {:pile-name :tableau-4-face-up :x (x 3) :y 150}
     {:pile-name :tableau-5-face-up :x (x 4) :y 150}
     {:pile-name :tableau-6-face-up :x (x 5) :y 150}
     {:pile-name :tableau-7-face-up :x (x 6) :y 150}
     ])

; use this function to calculate the y offset for 
; tableau face up piles
(defn offset [{:keys [pile-name cards]}]
  (let [count-cards-of-pile (fn [p-name] (->> cards
                                              (filter #(= p-name (:pile-name %)))
                                              (count)))]
    (case pile-name
      :tableau-1-face-up (count-cards-of-pile :tableau-1-face-down)
      :tableau-2-face-up (count-cards-of-pile :tableau-2-face-down)
      :tableau-3-face-up (count-cards-of-pile :tableau-3-face-down)
      :tableau-4-face-up (count-cards-of-pile :tableau-4-face-down)
      :tableau-5-face-up (count-cards-of-pile :tableau-5-face-down)
      :tableau-6-face-up (count-cards-of-pile :tableau-6-face-down)
      :tableau-7-face-up (count-cards-of-pile :tableau-7-face-down)
      0)))

(defn coordinate-for [{:keys [card-id cards]}]
  (let [card      (nth cards card-id)
        pile-name (:pile-name card)
        base      (:index-in-pile card)
        y-offset  (offset {:pile-name pile-name :cards cards})
        y-final     (+ base y-offset)]
        
  (cond 
    (= pile-name :stock)               {:x (* 0.4 base) :y (* 0.4 y-final) :z-index y-final}
    (= pile-name :waste)               {:x (+ 200 (* 20 base)) :y 0        :z-index y-final}
    (= pile-name :tableau-1-face-down) {:x (x 0) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-2-face-down) {:x (x 1) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-3-face-down) {:x (x 2) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-4-face-down) {:x (x 3) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-5-face-down) {:x (x 4) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-6-face-down) {:x (x 5) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-7-face-down) {:x (x 6) :y (+ 150 (* 20 y-final)) :z-index y-final}

    (= pile-name :tableau-1-face-up)   {:x (x 0) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-2-face-up)   {:x (x 1) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-3-face-up)   {:x (x 2) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-4-face-up)   {:x (x 3) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-5-face-up)   {:x (x 4) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-6-face-up)   {:x (x 5) :y (+ 150 (* 20 y-final)) :z-index y-final}
    (= pile-name :tableau-7-face-up)   {:x (x 6) :y (+ 150 (* 20 y-final)) :z-index y-final}

    (= pile-name :foundation-1) {:x (x 3) :y 0 :z-index y-final}
    (= pile-name :foundation-2) {:x (x 4) :y 0 :z-index y-final}
    (= pile-name :foundation-3) {:x (x 5) :y 0 :z-index y-final}
    (= pile-name :foundation-4) {:x (x 6) :y 0 :z-index y-final}
    :else {:x 400 :y 400 :z 400})))


;not actively used
(defn coordinate-before-deal [{:keys [card-id]}]
  {:x (* 0.2 card-id) :y (* 0.2 card-id) :z-index card-id})

(defn reset-coordinates [cards]
  (let [new-card-coordinates (fn [idx] (coordinate-for {:card-id idx :cards cards}))]
    (->> cards
      (map-indexed (fn [idx card] (merge card (new-card-coordinates idx))))
      (vec))))

