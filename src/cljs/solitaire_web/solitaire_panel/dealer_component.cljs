(ns solitaire-web.solitaire-panel.dealer-component
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe dispatch]]
[re-com.core  :refer [h-box v-box box gap line label checkbox radio-button button single-dropdown
                                  popover-content-wrapper popover-anchor-wrapper]]
            [re-com.util  :refer [deref-or-value]]
            [reagent.core :as    reagent]
            [reanimated.core :as anim]
            )) 

(def welcome-scene
  { :avatar-img "images/dealer/avatar-small-eyes.png"
    :content [:div
                [:p "Welcome to Vegas solitaire!"]
                [:p "My name is Bob."]
                [:p "Shall we start the game?"]
              ]})



(def scenes
  {:welcome welcome-scene})

(defn dealer-main []
  (let [dialog-visible? (subscribe [:dealer-dialog-visible?])
        cur-scene       (subscribe  [:dealer-scene])]
    (fn []
      (let [avatar-img (get-in scenes [@cur-scene :avatar-img])
            content    (get-in scenes [@cur-scene :content])]
      
        [popover-anchor-wrapper
           :showing? dialog-visible?
           :position :below-center
           :anchor   [:div
                       {:id "dealer-avatar"
                        :on-click #(dispatch [:set-dealer-dialog-visible true])}
                       [:img {:src avatar-img}] ]
           :popover  [popover-content-wrapper
                       :backdrop-opacity 0.3
                       :title    "Bob"
                       :on-cancel #(dispatch [:set-dealer-dialog-visible false])
                       :body     content]]
     )))) 

(defn dealer-component []
  [:div 
   {:id "dealer"}
   [dealer-main]])

