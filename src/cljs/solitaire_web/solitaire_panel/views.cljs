(ns solitaire-web.solitaire-panel.views
    (:require-macros
         [reagent.ratom :as ratom])
  (:require [solitaire-web.solitaire-panel.events]
            [solitaire-web.solitaire-panel.subs]
            [solitaire-web.solitaire-panel.card-component :refer [card-component]]
            [solitaire-web.solitaire-panel.placeholder-component :refer [placeholder-component]]
            [solitaire-web.solitaire-panel.paddle-component :refer [paddle-component]]
            [solitaire-web.solitaire-panel.dealer-component :refer [dealer-component]]
            [solitaire-web.solitaire-panel.money-component :refer [money-component]]
            [solitaire-web.solitaire-panel.coordinate-helper :refer [placeholders]]
            [solitaire-web.solitaire-panel.coordinates :refer [board-left-padding]]
            [reagent.core :as reagent]
            [reanimated.core :as anim]
            [re-frame.core :refer [dispatch dispatch-sync subscribe]]
            [re-com.core     :refer [h-box box v-box line md-icon-button]]
            ))

(defn deal-cards-button []
  [:button {:on-click #(dispatch [:deal-cards])} 
   "deal!"])

(defn new-game-button []
  [:button {:on-click #(dispatch [:start-new-game])} 
   "New game!"])

; this component gets re-render when the game changed
(defn animation-listener []
  (let [in-animation? (subscribe [:in-animation?])]
    (fn []
      (if-not @in-animation? (dispatch [:deselect-all-cards]))
      [:p (str "in animation? " @in-animation?)])))

(defn board []
  [:div  {:id "board"}
   [:div
    {:style {:position "absolute" :right 0 :bottom 0 :width "15vw"}}
    [:img {:src "images/latte.png" :width "100%"}]]
   [animation-listener]
   [deal-cards-button]
   [new-game-button]
   [h-box
    :align :center
    :size "auto"
    :children [
               [box :size "1" :child "box1"]
               [box :size "1" :child "box2"]
               [box :size "1" :justify :center :child [dealer-component]] 
               [box :size "1" :child [money-component]]
               [box :size "1" :child "box3"]
               ]]
 
   [h-box
    :align :center
    :size "auto"
    :children [
               ;[box :size "23.6vw" :child "box4"]
               [box :size board-left-padding :child "box4"]
               [box :size "1" :child 
     [:div
       [paddle-component]
       (for [ph placeholders]
         ^{:key ph} [placeholder-component ph])
       (for [card @(subscribe [:cards])]
         ^{:key (:card-id card)} [card-component (:card-id card)])]
                                      ]]]
   ]
  )
   


(defn main []
  (dispatch [:start-new-game])
  [:div {:id "board-container"} 
    [board]
   ])
