(ns solitaire-web.solitaire-panel.coordinate-helper
  (:require [solitaire-web.solitaire-panel.coordinates :as c]
            [solitaire-web.solitaire-panel.different-piles :refer [foundation-piles tableau-face-down-piles tableau-face-up-piles counter-pile]]
                                                                   ))
(def stock-placeholder
  (merge {:pile-name :stock} (get c/settings :stock)))

(defn reset-coordinates [cards]
  (let [pile-counts (->> cards (map :pile-name) (frequencies))]
    (->> cards
      (map (fn [card] (merge card (c/find-coordinates card pile-counts))))
      (vec))))
