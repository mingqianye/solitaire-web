(ns solitaire-web.solitaire-panel.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    [solitaire-core.public-api :refer [new-game]]
    [solitaire-web.solitaire-panel.coordinate-helper :refer [coordinate-before-deal reset-coordinates placeholders]]
    [solitaire-web.solitaire-panel.card-click-handler :refer [handle-placeholder-click handle-click]]
    ))

(defn new-sorted-cards []
  (->> (new-game)
    (sort-by :card-id)
    (map-indexed (fn [idx card] (merge card (coordinate-before-deal {:card-id idx}))))
    (map (fn [card] (merge card {:selected? false})))
    (vec)))


(reg-event-db :start-new-game
  (fn  [db [_]]
    (-> db
    (assoc-in [:solitaire-panel :cards] (new-sorted-cards))
    (assoc-in [:solitaire-panel :placeholders] placeholders)
      
      )))

(reg-event-db :deal-cards
  (fn [db [_]]
    (update-in db [:solitaire-panel :cards] #(reset-coordinates %))))

(reg-event-db :clicked-on-placeholder
  (fn  [db [_ placeholder-pile-name]]
    (let [cards     (get-in db [:solitaire-panel :cards])
          new-cards (handle-placeholder-click {:cards cards 
                                               :pile-name placeholder-pile-name})]
      (assoc-in db [:solitaire-panel :cards] new-cards))))


(reg-event-db :clicked-on-card
  (fn  [db [_ card-id]]
    (let [cards     (get-in db [:solitaire-panel :cards])
          new-cards (handle-click {:cards cards :card-id card-id})]
      (assoc-in db [:solitaire-panel :cards] new-cards))))
