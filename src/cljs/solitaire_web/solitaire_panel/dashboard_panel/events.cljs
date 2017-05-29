(ns solitaire-web.solitaire-panel.dashboard-panel.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    ))

(reg-event-db :start-dashboard
  (fn [db _]
    (-> db
    (assoc-in [:dashboard-panel :total-candies] 53)
    (assoc-in [:dashboard-panel :total-money] 20)
      )))

(reg-event-db :add-candies
  (fn [db [_ num-candies]]
    (update-in db [:dashboard-panel :total-candies] #(+ % num-candies))))

(reg-event-db :refresh-time
  (fn [db _]
    (assoc-in db [:dashboard-panel :last-updated-at] (.now js/Date.))))
