(ns solitaire-web.solitaire-panel.dashboard_panel.views
  (:require [solitaire-web.solitaire-panel.dashboard-panel.events]
            [solitaire-web.solitaire-panel.dashboard-panel.subs]
            [re-com.core :refer [progress-bar]]
    [re-frame.core :refer [dispatch dispatch-sync subscribe]]))

;refresh the clock every one second
(defonce do-timer 
  (js/setInterval #(dispatch [:refresh-time]) 50))

(defn last-updated-at []
  (let [last-updated (subscribe [:now])]
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
  (let [disabled? (subscribe [:add-candies-btn-in-cooldown?])
        percent (subscribe [:add-candies-btn-cooldown-progress])]
    (fn []
      [:div
        [:button 
         {:on-click #(dispatch [:add-candies-btn-clicked])
          :disabled @disabled?
          }
         "create candy"]
        [progress-bar
         :model percent
         ]
       
       ]
       )))

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
