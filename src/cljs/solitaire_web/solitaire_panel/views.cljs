(ns solitaire-web.solitaire-panel.views
    (:require-macros
         [reagent.ratom :as ratom])
  (:require [solitaire-web.solitaire-panel.events]
            [solitaire-web.solitaire-panel.subs]
            [solitaire-web.solitaire-panel.card-component :refer [card-component]]
            [solitaire-web.solitaire-panel.placeholder-component :refer [stock-placeholder-component placeholder-component placeholder-components]]
            [solitaire-web.solitaire-panel.paddle-component :refer [paddle-component]]
            [solitaire-web.solitaire-panel.dealer-component :refer [dealer-component]]
            [solitaire-web.solitaire-panel.money-component :refer [money-component]]
            [solitaire-web.solitaire-panel.coordinates :refer [board-left-padding]]
            [reagent.core :as reagent]
            [reanimated.core :as anim]
            [re-frame.core :refer [dispatch dispatch-sync subscribe]]
            [re-com.core     :refer [h-box box v-box line md-icon-button]]
            ))

(defn deal-cards-button []
  [:button {:on-click #(dispatch [:deal-cards])} 
   "deal!"]
  )

(defn new-game-button []
  [:button {:on-click #(dispatch [:start-new-game])} 
   "New game!"])

; this component gets re-render when the game changed
(defn animation-listener []
  (let [in-animation? (subscribe [:in-animation?])]
    (fn []
      (if-not @in-animation? (dispatch [:deselect-all-cards]))
      [:p (str "in animation? " @in-animation?)])))

(defn latte []
  [:div
   {:id "latte"}
   [:img {:src "images/latte.png" :width "100%"}]])


(defn board []
  [:div  {:id "board"}
   [latte]
   [animation-listener]
   [deal-cards-button]
   [new-game-button]
   [h-box
    :align :center
    :size "auto"
    :children [
               [box :size "1" :child ""]
               [box :size "1" :child ""]
               [box :size "none" :justify :center :align-self :center :child [dealer-component]] 
               [box :size "1" :align-self :center :child [money-component]]
               [box :size "1" :child ""]
               ]]
   [:br]
   [:br]
   [h-box
    :align :center
    :size "auto"
    :children [
               [box :size board-left-padding :child ""]
               [box :size "1" :child 
     [:div
       [paddle-component]
       [stock-placeholder-component]
       [placeholder-components]

       (for [card @(subscribe [:cards])]
         ^{:key (:card-id card)} [card-component (:card-id card)])]
                                      ]]]
   ]
  )


(defn main []
  (dispatch [:start-new-game])
  (dispatch [:set-dialog :welcome])
  (dispatch [:set-avatar :small-eyes])
  (dispatch [:set-dealer-dialog-visible true])
  [:div {:id "board-container"} 
    [board]
   ])
