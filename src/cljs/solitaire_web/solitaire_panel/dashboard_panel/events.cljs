(ns solitaire-web.solitaire-panel.dashboard-panel.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    ))

(defn execute-todo [db todo]
  (if ((:precond todo) db)
    ((:logic todo) db)
    db))

(defn now []
  (.now js/Date.))

(reg-event-db :start-dashboard
  (fn [db _]
    (-> db
      (assoc-in [:dashboard-panel :total-candies] 53)
      (assoc-in [:dashboard-panel :total-money] 20)
      (assoc-in [:dashboard-panel :todo-list] [])
      (assoc-in [:dashboard-panel :add-candies-btn-cooldown-at] (now))
      )))

(defn inc-candies [db]
  (let [cd (get-in db [:dashboard-panel :add-candies-btn-cooldown-at])
        ts (get-in db [:dashboard-panel :now])]
    (if (> ts cd)
      (update-in db [:dashboard-panel :total-candies] inc)
      (update-in db [:dashboard-panel :todo-list] #(conj % inc-candies)))))

(reg-event-db :add-candies-btn-clicked
  (fn [db _]
    (let [noww (now)]
    (-> db
      (assoc-in [:dashboard-panel :add-candies-btn-last-clicked] noww)
      (assoc-in [:dashboard-panel :add-candies-btn-cooldown-at] (+ 5000 noww))
      (update-in [:dashboard-panel :todo-list] #(conj % inc-candies))
      ))))

(reg-event-db :refresh-time
  (fn [db _]
    (let [todo-fn (apply comp (get-in db [:dashboard-panel :todo-list]))]
      (-> db
        (assoc-in [:dashboard-panel :todo-list] [])
        (assoc-in [:dashboard-panel :now] (now))
        (todo-fn)
      ))))
