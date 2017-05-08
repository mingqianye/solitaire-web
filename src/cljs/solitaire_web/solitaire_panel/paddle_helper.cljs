(ns solitaire-web.solitaire-panel.paddle-helper
  (:require [solitaire-web.solitaire-panel.coordinates :as c]))

; given all cards, find out where the paddle is
(defn get-paddle [cards]
  (let [selected-cards (filter :selected? cards)
        first-selected-card (apply min-key :index-in-pile selected-cards)
        card-count (count selected-cards)]
    {:x (or (:x first-selected-card) (get-in c/settings [:paddle-default :x]))
     :y (+ (or (:y first-selected-card) (get-in c/settings [:paddle-default :y])) (-> c/settings :paddle-top-offset :y))
     :scale-y (+ 1 (* (/ (get-in c/deltas [7 :y]) 100) card-count))
     }))
