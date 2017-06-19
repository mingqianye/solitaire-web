(ns solitaire-web.solitaire-panel.dashboard-panel.events
  (:require
    [re-frame.core :refer [reg-event-db]]
    ))

(defn now [db]
  (get-in db [:dashboard-panel :now]))

(reg-event-db :start-dashboard
  (fn [db _]
    (-> db
      (assoc-in [:dashboard-panel :buy-in-cost] 52)
      (assoc-in [:dashboard-panel :total-candies] 51)
      (assoc-in [:dashboard-panel :total-money] 20)
      (assoc-in [:dashboard-panel :container-capacity] 60)
      (assoc-in [:dashboard-panel :todo-list] [])
      (assoc-in [:dashboard-panel :add-candies-btn-reactivate-at] (now db))
      )))

(reg-event-db :add-candies-btn-clicked
  (fn [db _]
    (let [noww (now db)]
    (-> db
      (assoc-in [:dashboard-panel :add-candies-btn-last-clicked] noww)
      (assoc-in [:dashboard-panel :add-candies-btn-reactivate-at] (+ 5000 noww))
      (update-in [:dashboard-panel :todo-list] #(conj % 
        {:can-be-executed? 
          (fn [dbb] 
            (let [cd (get-in dbb [:dashboard-panel :add-candies-btn-reactivate-at])
                  ts (now dbb)]
              (> ts cd)))
         :execute 
          (fn [dbb] (update-in dbb [:dashboard-panel :total-candies] inc))}))
      ))))

(reg-event-db :sell-candies-btn-clicked
  (fn [db _]
    (let [noww (now db)]
    (-> db
      (assoc-in [:dashboard-panel :sell-candies-btn-last-clicked] noww)
      (assoc-in [:dashboard-panel :sell-candies-btn-reactivate-at] (+ 5000 noww))
      (update-in [:dashboard-panel :todo-list] #(conj % 
        {:can-be-executed? 
          (fn [dbb] 
            (let [cd (get-in dbb [:dashboard-panel :sell-candies-btn-reactivate-at])
                  ts (now dbb)
                  total-candies (get-in dbb [:dashboard-panel :total-candies])]
              (and (> ts cd) (> total-candies 0))))
         :execute 
          (fn [dbb] 
            (-> dbb
            (update-in [:dashboard-panel :total-candies] dec)
            (update-in [:dashboard-panel :total-money] (fn [x] (+ x 5)))
              ))}))
      ))))


(reg-event-db :refresh-time
  (fn [db _]
    (let [new-db        (assoc-in db [:dashboard-panel :now] (.now js/Date.))
          todo-fns      (get-in new-db [:dashboard-panel :todo-list])
          grouped       (group-by #((:can-be-executed? %) new-db) todo-fns)
          execute-now   (->> (get grouped true []) (map :execute) (apply comp))
          execute-later (get grouped false [])]
      (-> new-db
        (assoc-in [:dashboard-panel :todo-list] execute-later)
        (execute-now)
      ))))
