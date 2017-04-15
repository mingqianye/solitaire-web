(ns solitaire-web.solitaire-panel.paddle-helper)

; given all cards, find out where the paddle is
(defn get-paddle [cards]
  (let [selected-cards (filter :selected? cards)
        selected-cards-count (count selected-cards)
        first-selected-card (first selected-cards)]
    {:x (:x first-selected-card) 
     :y (:y first-selected-card)
     :offset selected-cards-count
     }))
