(ns solitaire-web.solitaire-panel.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    [solitaire-core.public-api :refer [new-game]]
    [solitaire-web.solitaire-panel.coordinate-helper :refer [reset-coordinates placeholders]]
    [solitaire-web.solitaire-panel.card-click-handler :refer [handle-placeholder-click handle-click]]
    ))

(defn prep-cards [level-name]
  (->> 
    (new-game {:level-name level-name})
    (sort-by :card-id)
    (map (fn [card] (merge card {:selected? false :in-animation? false})))
    (vec)))

(reg-event-db :start-new-game
  (fn  [db [_]]
    (let [cards (-> (prep-cards :unshuffled) (reset-coordinates))]
      (-> db
        (assoc-in [:solitaire-panel :cards] cards)
          ))))

(reg-event-db :set-dealer-dialog-visible
  (fn [db [_ show?]]
    (assoc-in db [:solitaire-panel :dealer :show-dialog?] show?)))

(reg-event-db :set-avatar
  (fn [db [_ avatar]]
    (assoc-in db [:solitaire-panel :dealer :avatar] avatar)))

(reg-event-db :set-dialog
  (fn [db [_ dialog]]
    (assoc-in db [:solitaire-panel :dealer :dialog] dialog)))

(reg-event-db :deal-cards
  (fn [db [_]]
    (let [cards (-> (prep-cards :shuffled) (reset-coordinates))]
    (-> db
      (assoc-in [:solitaire-panel :cards] cards) 
      ))))

(reg-event-db :deselect-all-cards
  (fn [db [_]]
    (let [cards (get-in db [:solitaire-panel :cards])
          new-cards (vec (map #(assoc % :selected? false) cards))]
      (assoc-in db [:solitaire-panel :cards] new-cards))))

(reg-event-db :update-in-animation?
  (fn [db [_ card-id is-in-animation]]
    (assoc-in db [:solitaire-panel :cards card-id :in-animation?] is-in-animation)))

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
