(ns solitaire-web.solitaire-panel.coordinate-helper
  (:require [solitaire-web.solitaire-panel.coordinates :as c]
            [solitaire-web.solitaire-panel.different-piles :refer [foundation-piles tableau-face-down-piles tableau-face-up-piles counter-pile]]
                                                                   ))

(def placeholders
  [(-> {:pile-name :stock}             (merge (get c/settings :stock)))
   (-> {:pile-name :foundation-1}      (merge (get c/settings :foundation-1)))
   (-> {:pile-name :foundation-2}      (merge (get c/settings :foundation-2)))
   (-> {:pile-name :foundation-3}      (merge (get c/settings :foundation-3)))
   (-> {:pile-name :foundation-4}      (merge (get c/settings :foundation-4)))
   (-> {:pile-name :tableau-1-face-up} (merge (get c/settings :tableau-1-face-down)))
   (-> {:pile-name :tableau-2-face-up} (merge (get c/settings :tableau-2-face-down)))
   (-> {:pile-name :tableau-3-face-up} (merge (get c/settings :tableau-3-face-down)))
   (-> {:pile-name :tableau-4-face-up} (merge (get c/settings :tableau-4-face-down)))
   (-> {:pile-name :tableau-5-face-up} (merge (get c/settings :tableau-5-face-down)))
   (-> {:pile-name :tableau-6-face-up} (merge (get c/settings :tableau-6-face-down)))
   (-> {:pile-name :tableau-7-face-up} (merge (get c/settings :tableau-7-face-down)))])


(defn coordinate-for [card pile-counts]
  (let [pile-name (:pile-name card)
        index-in-pile (:index-in-pile card)]
    (cond 
      (= pile-name :stock)
        (c/nth-card-in-stock {:index-in-pile index-in-pile})            
      (= pile-name :waste)
        (c/nth-card-in-waste {:number-of-waste-cards (:waste pile-counts) :index-in-pile index-in-pile})
      (contains? foundation-piles pile-name)
        (c/nth-card-in-foundations {:pile-name pile-name :index-in-pile index-in-pile})
      (contains? tableau-face-down-piles pile-name)
        (c/nth-card-in-face-downs {:pile-name pile-name :index-in-pile index-in-pile})
      (contains? tableau-face-up-piles pile-name)
        (let [down-pile-name (pile-name counter-pile)]
          (c/nth-card-in-face-ups {:down-pile-name down-pile-name
                                   :index-in-down-pile (+ (down-pile-name pile-counts) index-in-pile)}))
      :else (println "ERRRR cannot find pile"))))


(defn reset-coordinates [cards]
  (let [pile-counts (->> cards (map :pile-name) (frequencies))]
    (->> cards
      (map (fn [card] (merge card (coordinate-for card pile-counts))))
      (vec))))



