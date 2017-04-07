(ns solitaire-web.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    ))

(reg-event-db :initialise-db
  (fn  [db [_ _]]
    {}))

(reg-event-db :set-active-panel
  (fn [db [_ panel]]
    (assoc db :active-panel panel)))
  
(reg-event-db :set-x
  (fn [db _]
    (assoc db :x 300)))
