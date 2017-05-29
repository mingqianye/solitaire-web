(ns solitaire-web.solitaire-panel.dashboard_panel.views
  (:require [solitaire-web.solitaire-panel.dashboard-panel.events]
            [solitaire-web.solitaire-panel.dashboard-panel.subs]
    [re-frame.core :refer [dispatch dispatch-sync subscribe]]))

(defn total-money []
  (let [total (subscribe [:total-money])]
    (fn []
      [:p (str "total money: " @total)]
      )))

(defn total-candies []
  (let [total (subscribe [:total-candies])]
    (fn []
      [:p (str "total number of candies: " @total)]
      )))


(defn dashboard []
  (dispatch [:start-dashboard])
  [:div 
   [total-money]
   [total-candies]
   
   ])

(defn main []
  [:div {:style {:position "absolute"
                 :right 0
                 :top 0}}
   [dashboard]
   ])
