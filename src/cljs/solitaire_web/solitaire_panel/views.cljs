(ns solitaire-web.solitaire-panel.views
    (:require-macros
         [reagent.ratom :as ratom])
  (:require [solitaire-web.solitaire-panel.events]
            [solitaire-web.solitaire-panel.subs]
            [solitaire-web.solitaire-panel.card-component :refer [card-component]]
            [solitaire-web.solitaire-panel.placeholder-component :refer [placeholder-component]]
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

(defn ani []
   (let [x (reagent/atom 200)
        cx (anim/interpolate-to x {:duration 200})]
    (fn []
      (let [translate-to (str "translate3d(" @cx "px, 0px, 0px)")]
        [:div
         [:button {:on-click (fn [e] (swap! x - 50))} "<"]
         [:button {:on-click (fn [e] (swap! x + 50))} ">"]
         [:div {:style {:position "absolute" 
                        :left @cx}}
                        ;:transform translate-to}}
          (str "ani#" @cx)
          ]]))))

(defn ani-2 []
   (let [x (subscribe [:x])
        cx (anim/interpolate-to x {:duration 300})]
    (fn []
      (let [translate-to (str "translate3d(" @cx "px,0px,0px)")]
        [:div
         [:button {:on-click #(dispatch [:add-x 50])} ">"]
         [:div {:style {:position "absolute" 
                        :left @cx}}
                        ;:transform translate-to}}
          (str "ani-2#" @cx)
          ]]))))


(defn board []
  [:div  {:style {:background "#45a173"
                  :margin-left "5%"
                  :margin-right "5%"
                  :width "90%"
                  :height "100%"
                  }}
   [:p "Solitaire panel"]
   [ani]
   [ani-2]
   [deal-cards-button]
   [new-game-button]
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
