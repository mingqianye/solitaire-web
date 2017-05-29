(ns solitaire-web.solitaire-panel.dashboard_panel.views
  (:require [solitaire-web.solitaire-panel.dashboard-panel.events]
            [solitaire-web.solitaire-panel.dashboard-panel.subs]
    [re-frame.core :refer [dispatch dispatch-sync subscribe]]))

;refresh the clock every 41 microsecond (1s/24)
(defonce do-timer 
  (js/setInterval #(dispatch [:refresh-time]) 41))

(defn last-updated-at []
  (let [last-updated (subscribe [:last-updated-at])]
    (fn []
      [:p "last updated at: " @last-updated])))

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

(defn create-candy []
  (let []
    (fn []
      [:button 
       {:on-click #(dispatch [:add-candies 1])}
       "create candy"])))

(defn sell-candy []
  (let []
    (fn []
      [:button "sell candy"])))



(defn dashboard []
  (dispatch [:start-dashboard])
  [:div 
   [total-money]
   [total-candies]
   [last-updated-at]
   [create-candy]
   [:br]
   [sell-candy]
   ])

(defn main []
  [:div {:style {:position "absolute"
                 :right 0
                 :top 0}}
   [dashboard]
   ])
