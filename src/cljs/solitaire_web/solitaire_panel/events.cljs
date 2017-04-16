(ns solitaire-web.solitaire-panel.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    [solitaire-core.public-api :refer [new-game]]
    [solitaire-web.solitaire-panel.coordinate-helper :refer [reset-coordinates placeholders]]
    [solitaire-web.solitaire-panel.card-click-handler :refer [handle-placeholder-click handle-click]]
    [solitaire-web.solitaire-panel.paddle-helper :refer [get-paddle]]
    ))

(defn prep-cards [level-name]
  (->> 
    (new-game {:level-name level-name})
    (sort-by :card-id)
    (map (fn [card] (merge card {:selected? false})))
    (vec)))


(reg-event-db :start-new-game
  (fn  [db [_]]
    (let [cards (-> (prep-cards :unshuffled) (reset-coordinates))
          paddle (get-paddle cards)]
      (-> db
      (assoc-in [:solitaire-panel :cards] cards)
      (assoc-in [:solitaire-panel :placeholders] placeholders)
      (assoc-in [:solitaire-panel :paddle] paddle)
        ))))

(reg-event-db :deal-cards
  (fn [db [_]]
    (let [cards (-> (prep-cards :shuffled) (reset-coordinates))
          paddle (get-paddle cards)]
    (-> db
      (assoc-in [:solitaire-panel :cards] cards) 
      (assoc-in [:solitaire-panel :paddle] paddle)
      ))))

(reg-event-db :clicked-on-placeholder
  (fn  [db [_ placeholder-pile-name]]
    (let [cards     (get-in db [:solitaire-panel :cards])
          new-cards (handle-placeholder-click {:cards cards 
                                               :pile-name placeholder-pile-name})]
      (assoc-in db [:solitaire-panel :cards] new-cards))))


(reg-event-db :clicked-on-card
  (fn  [db [_ card-id]]
    (let [cards     (get-in db [:solitaire-panel :cards])
          new-cards (handle-click {:cards cards :card-id card-id})
          paddle (get-paddle new-cards)]
      (-> db
        (assoc-in [:solitaire-panel :cards] new-cards)
        (assoc-in [:solitaire-panel :paddle] paddle)
        ))))
