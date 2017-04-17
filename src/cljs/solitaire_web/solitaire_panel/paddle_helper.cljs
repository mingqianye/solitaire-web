(ns solitaire-web.solitaire-panel.paddle-helper
  (:require [solitaire-web.solitaire-panel.coordinates :as c]))

; given all cards, find out where the paddle is
(defn get-paddle [cards]
  (let [selected-cards (filter :selected? cards)
        first-selected-card (apply min-key :index-in-pile selected-cards)
        card-count (count selected-cards)]
    {:x (or (:x first-selected-card) 200)
     :y (+ (or (:y first-selected-card) 100) (-> c/settings :paddle-top-offset :y))
     :scale-y (+ 1 (* (-> c/settings :card-in-td-offset :y (/ 100)) card-count))
     }))
