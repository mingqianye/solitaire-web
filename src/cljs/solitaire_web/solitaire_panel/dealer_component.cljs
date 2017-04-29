(ns solitaire-web.solitaire-panel.dealer-component
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe dispatch]]
[re-com.core  :refer [h-box v-box box gap line label checkbox radio-button button single-dropdown
                                  popover-content-wrapper popover-anchor-wrapper]]
            [re-com.util  :refer [deref-or-value]]
            [reagent.core :as    reagent]
            [reanimated.core :as anim]
            )) 

(def avatar-image
  {:smile "images/dealer/avatar-smile.png"
   :small-eyes "images/dealer/avatar-small-eyes.png" }
  )


(defn dealer-main []
  (let [dealer  (subscribe [:dealer])]
    (fn []
      [popover-anchor-wrapper
         :showing? (reaction (:show-dialog? @dealer))
         :position :below-center
         :anchor   [:div
                     {:id "dealer-avatar"
                      :on-click #(do (dispatch [:show-dealer-dialog true])
                                     (dispatch [:make-dealer :small-eyes])
                                   ) }
                     [:img {:src ((:avatar-face @dealer) avatar-image)}] ]
         :popover  [popover-content-wrapper
                     :backdrop-opacity 0.3
                     :title    "Bob"
                     :on-cancel #(do (dispatch [:show-dealer-dialog false])
                                     (dispatch [:make-dealer :smile])
                                     )
                     :body     (:dialog-content @dealer)]]
      
      
      )))  ;; v0.10.0 breaking change fix (was [popover-body showing? @position dialog-data on-change])

(defn dealer-component []
  [:div 
   {:id "dealer"}
   [dealer-main]])

