(ns solitaire-web.solitaire-panel.dealer-component
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe dispatch]]
[re-com.core  :refer [h-box v-box box gap line label checkbox radio-button button single-dropdown
                                  popover-content-wrapper popover-anchor-wrapper]]
            [re-com.util  :refer [deref-or-value]]
            [reagent.core :as    reagent]
            [reanimated.core :as anim]
            )) 

(def welcome-dialog
  [:div
    [:p "Welcome to Vegas solitaire!"]
    [:p "My name is Bob."]
    [:p "Shall we start the game?"]
    [:button 
     {:on-click #(do (dispatch [:set-dialog :intro])
                     (dispatch [:set-avatar :intro])
                   )}
     "Sure!"] ])

(def intro-dialog
  [:div
    [:p "Here is how much you win so far."]
    [:p "Shall we start?"]
    [:button 
     {:on-click #(do (dispatch [:deal-cards])
                     (dispatch [:set-avatar :smile])
                     (dispatch [:set-dialog :pause-game])
                     (dispatch [:set-dealer-dialog-visible false])
                   )}
      "Deal!"]])

(def in-game-dialog
  [:div
    [:p "He"]
              ])

(def pause-game-dialog
  [:div
    [:p "What can I do for you?"]])

(def won-game-dialog
  [:div
    [:p "Congrats!"]])

(def dialogs
  {:welcome welcome-dialog
   :intro   intro-dialog
   :in-game in-game-dialog
   :pause-game pause-game-dialog
   :won-game won-game-dialog
   })

(def avatars
  {:small-eyes "images/dealer/avatar-small-eyes.png"
   :intro      "images/dealer/avatar-intro.png"
   :smile      "images/dealer/avatar-smile.png"
   :hide-hands "images/dealer/avatar-hide-hands.png"
   :won        "images/dealer/avatar-cards.png"
   })


(defn dealer-main []
  (let [dialog-visible? (subscribe [:dealer-dialog-visible?])
        avatar (subscribe [:dealer-avatar])
        dialog (subscribe [:dealer-dialog])]
    (fn []
      (let [avatar-img (get avatars @avatar)
            content    (get dialogs @dialog)]
      
        [popover-anchor-wrapper
           :showing? dialog-visible?
           :position :below-center
           :anchor   [:div
                       {:id "dealer-avatar"
                        :on-click #(do 
                                     (dispatch [:set-avatar :hide-hands])
                                     (dispatch [:set-dealer-dialog-visible true]))}
                       [:img {:src avatar-img}] ]
           :popover  [popover-content-wrapper
                       :close-button? false
                       :backdrop-opacity 0.3
                       :title    "Bob"
                       :on-cancel #(do 
                                     (dispatch [:set-avatar :smile])
                                     (dispatch [:set-dealer-dialog-visible false]))
                       :body     content]]
     )))) 

(defn dealer-component []
  [:div 
   {:id "dealer"}
   [dealer-main]])

