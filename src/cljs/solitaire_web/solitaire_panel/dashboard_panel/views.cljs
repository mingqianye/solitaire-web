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

(defn container-capacity []
  (let [total-candies (subscribe [:total-candies])
        capacity (subscribe [:container-capacity])]
    (fn []
        [:p (str "container capacity: " @total-candies "/" @capacity)]
      )))

(defn candy-container-progress []
  (let [percent (subscribe [:container-capacity-progress])]
    (fn []
      [:div
        [progress-bar
         :model percent
         :striped? true]]
      )))

(defn create-candy-btn []
  (let [disabled? (subscribe [:add-candies-btn-in-cooldown?])]
    (fn []
      [:button 
       {:on-click #(dispatch [:add-candies-btn-clicked])
        :disabled @disabled? }
       "create candy"])))

(defn create-candy-progress []
      [progress-bar
       :model (subscribe [:add-candies-btn-cooldown-progress])
       :striped? true])

(defn sell-candy-btn []
  (let [disabled? (subscribe [:sell-candies-btn-in-cooldown?])]
    (fn []
      [:button 
       {:on-click #(dispatch [:sell-candies-btn-clicked])
        :disabled @disabled? }
       "sell candy"])))

(defn sell-candy-progress []
      [progress-bar
       :model (subscribe [:sell-candies-btn-cooldown-progress])
       :striped? true])

(defn dashboard []
  (dispatch [:start-dashboard])
  [:div 
   [last-updated-at]
   [h-box
     :gap "10px"
     :width "100vw"
     :children [
      [box :size "2" :child [v-box :children [
        [box :child [total-candies]]
        [box :child [create-candy-progress]]
        [box :child [create-candy-btn]]]]]
      [box :size "2" :child [v-box :children [
        [box :child [container-capacity]]
        [box :child [candy-container-progress]]]]]
      [box :size "2" :child [v-box :children [
        [box :child [total-money]]
        [box :child [sell-candy-progress]]
        [box :child [sell-candy-btn]]]]]
      [box :size "6" :child [:p "spaceholder"]]]]
   ])

(defn main []
  [:div {:style {:position "absolute"
                 :left 0
                 :bottom 0}}
   [dashboard]
   ])
