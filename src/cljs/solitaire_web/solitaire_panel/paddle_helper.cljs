(ns solitaire-web.solitaire-panel.paddle-helper)

; given all cards, find out where the paddle is
(defn get-paddle [cards]
  (let [selected-cards (filter :selected? cards)
        first-selected-card (apply min-key :index-in-pile selected-cards)]
    {:x (or (:x first-selected-card) 200)
     :y (or (:y first-selected-card) -100)
     :selected-cards selected-cards
     }))
