(ns solitaire-web.solitaire-panel.coordinates
  (:require [solitaire-web.solitaire-panel.different-piles :refer [foundation-piles
                                                                   tableau-face-up-piles
                                                                   tableau-face-down-piles]]))

(def settings
  {:foundation-1        {:x (* 130 3) :y 0  }
   :foundation-2        {:x (* 130 4) :y 0  }
   :foundation-3        {:x (* 130 5) :y 0  }
   :foundation-4        {:x (* 130 6) :y 0  }
   :tableau-1-face-down {:x (* 130 0) :y 150}
   :tableau-2-face-down {:x (* 130 1) :y 150}
   :tableau-3-face-down {:x (* 130 2) :y 150}
   :tableau-4-face-down {:x (* 130 3) :y 150}
   :tableau-5-face-down {:x (* 130 4) :y 150}
   :tableau-6-face-down {:x (* 130 5) :y 150}
   :tableau-7-face-down {:x (* 130 6) :y 150}
   :stock               {:x 0         :y 0}
   :waste-up            {:x 200       :y 0}
   :waste-down          {:x 220       :y 0}

   :card-in-s-offset    {:x 0.4       :y 0.4}
   :card-in-wu-offset   {:x 20        :y 0  }
   :card-in-wd-offset   {:x 0         :y 0  }
   :card-in-f-offset    {:x 0         :y 0  }
   :card-in-td-offset   {:x 0         :y 20 }

   :paddle-top-offset {:x 0 :y -10}
   })

(defn nth-card [{:keys [base-key offset-key offset]}]
  {:x (+ (get-in settings [base-key :x]) (* (get-in settings [offset-key :x]) offset))
   :y (+ (get-in settings [base-key :y]) (* (get-in settings [offset-key :y]) offset))
   :z-index offset})

(defn nth-card-in-foundations [{:keys [pile-name index-in-pile]}]
  {:pre [(contains? foundation-piles pile-name)]}
  (nth-card {:base-key pile-name :offset-key :card-in-f-offset :offset index-in-pile}))

(defn nth-card-in-face-downs [{:keys [pile-name index-in-pile]}]
  {:pre [(contains? tableau-face-down-piles pile-name)]}
  (nth-card {:base-key pile-name :offset-key :card-in-td-offset :offset index-in-pile}))

(defn nth-card-in-face-ups [{:keys [down-pile-name index-in-down-pile]}]
  {:pre [(contains? tableau-face-down-piles down-pile-name)]}
  (nth-card {:base-key down-pile-name :offset-key :card-in-td-offset :offset index-in-down-pile}))

(defn nth-card-in-stock [{:keys [index-in-pile]}]
  (nth-card {:base-key :stock :offset-key :card-in-s-offset :offset index-in-pile}))

(defn nth-card-in-waste [{:keys [number-of-waste-cards index-in-pile]}]
  (let [third-last (- number-of-waste-cards 3)
        translated (- index-in-pile third-last)
        is-in-last-3? (>= translated 0)]
    (if is-in-last-3?
      (nth-card {:base-key :waste-up :offset-key :card-in-wu-offset :offset translated})
      (nth-card {:base-key :waste-down :offset-key :card-in-wd-offset :offset -1}))))
