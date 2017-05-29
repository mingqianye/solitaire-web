(ns solitaire-web.solitaire-panel.dashboard-panel.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    ))

(reg-event-db :start-dashboard
  (fn [db _]
    (-> db
    (assoc-in [:solitaire-panel :dashboard-panel :total-candies] 53)
    (assoc-in [:solitaire-panel :dashboard-panel :total-money] 20)
      )))

