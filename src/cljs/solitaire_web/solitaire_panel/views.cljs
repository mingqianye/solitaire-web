(ns solitaire-web.solitaire-panel.views
    (:require-macros
         [reagent.ratom :as ratom])
  (:require [solitaire-web.solitaire-panel.events]
            [solitaire-web.solitaire-panel.subs]
            [solitaire-web.solitaire-panel.card-component :refer [card-component]]
            [solitaire-web.solitaire-panel.placeholder-component :refer [placeholder-component]]
            [solitaire-web.solitaire-panel.money-component :refer [money-component]]
            [solitaire-web.solitaire-panel.coordinate-helper :refer [placeholders]]
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

(defn board []
  [:div  {:style {:background "#45a173"
                  :margin-left "5%"
                  :margin-right "5%"
                  :width "90%"
                  :height "100%"
                  }}
   [:p "Solitaire panel"]
   [deal-cards-button]
   [new-game-button]
   [money-component]
   (for [ph placeholders]
     ^{:key ph} [placeholder-component ph])
   (for [card @(subscribe [:cards])]
     ^{:key (:card-id card)} [card-component (:card-id card)])
   ])


(defn main []
  (dispatch [:start-new-game])
  [:div {:style {:background "grey"
                 :height "100vh"}}
    [board]
   ])
