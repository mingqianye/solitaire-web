(ns solitaire-web.solitaire-panel.different-piles)

(def face-down-piles
  #{:stock :tableau-1-face-down :tableau-2-face-down :tableau-3-face-down :tableau-4-face-down :tableau-5-face-down :tableau-6-face-down :tableau-7-face-down})

(def tableau-face-down-piles
  #{:tableau-1-face-down :tableau-2-face-down :tableau-3-face-down :tableau-4-face-down
    :tableau-5-face-down :tableau-6-face-down :tableau-7-face-down})

(def tableau-face-up-piles
  #{:tableau-1-face-up :tableau-2-face-up :tableau-3-face-up :tableau-4-face-up
    :tableau-5-face-up :tableau-6-face-up :tableau-7-face-up})

(def foundation-piles
  #{:foundation-1 :foundation-2 :foundation-3 :foundation-4})

(def counter-pile
  (let [one-way  {:tableau-1-face-down :tableau-1-face-up
                  :tableau-2-face-down :tableau-2-face-up
                  :tableau-3-face-down :tableau-3-face-up
                  :tableau-4-face-down :tableau-4-face-up
                  :tableau-5-face-down :tableau-5-face-up
                  :tableau-6-face-down :tableau-6-face-up
                  :tableau-7-face-down :tableau-7-face-up}
        another-way (zipmap (vals one-way) (keys one-way))]
    (merge one-way another-way)))
