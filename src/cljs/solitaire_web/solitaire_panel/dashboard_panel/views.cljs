(ns solitaire-web.solitaire-panel.dashboard_panel.views
  (:require [solitaire-web.solitaire-panel.dashboard-panel.events]
            [solitaire-web.solitaire-panel.dashboard-panel.subs]
            [re-com.core :refer [progress-bar h-box v-box box]]
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

(defn candy-capacity []
  (let [percent (subscribe [:candy-capacity-progress])]
    (fn []
      [:div
        [:p "candy capacity" ]
        [progress-bar
         :model percent
         :striped? true]]
      )))

(defn create-candy []
  (let [disabled? (subscribe [:add-candies-btn-in-cooldown?])
        percent (subscribe [:add-candies-btn-cooldown-progress])]
    (fn []
      [h-box
       :width "20vw"
       :align :center
       :children [[box 
                   :size "1"
                   :child
                     [:button 
                      {:on-click #(dispatch [:add-candies-btn-clicked])
                       :disabled @disabled?
                       }
                      "create candy"]]
                  [box
                   :size "1"
                   :child
                   [:div {:style {:width "100%"}}
                     [progress-bar
                      :model percent
                      :striped? true]]]]]
       )))

(defn sell-candy []
  (let [disabled? (subscribe [:sell-candies-btn-in-cooldown?])
        percent (subscribe [:sell-candies-btn-cooldown-progress])]
    (fn []
      [h-box
       :width "20vw"
       :align :center
       :children [[box 
                   :size "1"
                   :child
                     [:button 
                      {:on-click #(dispatch [:sell-candies-btn-clicked])
                       :disabled @disabled?
                       }
                      "sell candy"]]
                  [box
                   :size "1"
                   :child
                   [:div {:style {:width "100%"}}
                     [progress-bar
                      :model percent
                      :striped? true]]]]]
       )))



(defn dashboard []
  (dispatch [:start-dashboard])
  [:div 
   [total-money]
   [total-candies]
   [last-updated-at]
   [create-candy]
   [candy-capacity]
   [sell-candy]
   ])

(defn main []
  [:div {:style {:position "absolute"
                 :right 0
                 :top 0}}
   [dashboard]
   ])
