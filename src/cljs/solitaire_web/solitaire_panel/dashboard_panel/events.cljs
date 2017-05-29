(ns solitaire-web.solitaire-panel.dashboard-panel.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    ))

(defn now []
  (.now js/Date.))

(reg-event-db :start-dashboard
  (fn [db _]
    (-> db
    (assoc-in [:dashboard-panel :total-candies] 53)
    (assoc-in [:dashboard-panel :total-money] 20)
    (assoc-in [:dashboard-panel :add-candies-btn-cooldown-at] (now))
      )))

(reg-event-db :add-candies-btn-clicked
  (fn [db _]
    (-> db
    (assoc-in [:dashboard-panel :add-candies-btn-cooldown-at] (+ 5000 (now)))
    (update-in [:dashboard-panel :total-candies] inc)
      )))

(reg-event-db :refresh-time
  (fn [db _]
    (assoc-in db [:dashboard-panel :now] (now))))
