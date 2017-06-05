(ns solitaire-web.solitaire-panel.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    [solitaire-core.public-api :refer [new-game]]
    [solitaire-web.solitaire-panel.coordinate-helper :refer [reset-coordinates]]
    [solitaire-web.solitaire-panel.card-click-handler :refer [handle-stock-placeholder-click handle-placeholder-click handle-click]]
    ))

(defn prep-cards [level-name]
  (->> 
    (new-game {:level-name level-name})
    (sort-by :card-id)
    (map (fn [card] (merge card {:selected? false :in-animation? false})))
    (reset-coordinates)
    ))

(reg-event-db :start-new-game
  (fn  [db [_]]
    (let [cards (prep-cards :unshuffled)]
      (-> db
        (assoc-in [:solitaire-panel :transactions] [])
        (assoc-in [:solitaire-panel :cards] cards)
        (assoc-in [:solitaire-panel :stock-placeholder-num-clicks] 0)
          ))))

(reg-event-db :set-dealer-dialog-visible
  (fn [db [_ show?]]
    (assoc-in db [:solitaire-panel :dealer :show-dialog?] show?)))

(reg-event-db :set-scene
  (fn [db [_ scene]]
    (assoc-in db [:solitaire-panel :dealer :scene] scene)))

(reg-event-db :deal-cards
  (fn [db [_]]
    (let [cards (prep-cards :shuffled)
          buy-in-cost (get-in db [:dashboard-panel :buy-in-cost])
          ]
    (-> db
      (update-in [:dashboard-panel :total-candies] #(- % buy-in-cost))
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

(reg-event-db :clicked-on-stock-placeholder
  (fn  [db _]
    (let [cards     (get-in db [:solitaire-panel :cards])
          new-cards (handle-stock-placeholder-click {:cards cards})
          ]
      (-> db
        (assoc-in [:solitaire-panel :cards] new-cards)
        (update-in [:solitaire-panel :stock-placeholder-num-clicks] inc)))
        ))

(reg-event-db :clicked-on-placeholder
  (fn  [db [_ placeholder-pile-name]]
    (let [cards     (get-in db [:solitaire-panel :cards])
          new-cards (handle-placeholder-click {:cards cards 
                                               :pile-name placeholder-pile-name})]
      (-> db
        (assoc-in [:solitaire-panel :cards] new-cards)
        ))))

(reg-event-db :clicked-on-card
  (fn  [db [_ card-id]]
    (let [cards     (get-in db [:solitaire-panel :cards])
          new-cards (handle-click {:cards cards :card-id card-id})]
      (-> db
        (assoc-in [:solitaire-panel :cards] new-cards)
        ))))

(reg-event-db :set-balance
  (fn  [db [_ new-balance]]
    (assoc-in db [:solitaire-panel :balance] new-balance)))

(reg-event-db :add-transaction
  (fn  [db [_ amount msg]]
    (update-in db [:solitaire-panel :transactions] #(conj % {:id (inc (count %))
                                                             :amount amount
                                                             :msg msg}))))
