(ns solitaire-web.solitaire-panel.dealer-component
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe dispatch]]
[re-com.core  :refer [h-box v-box box gap line label checkbox radio-button button single-dropdown
                                  popover-content-wrapper popover-anchor-wrapper]]
            [re-com.util  :refer [deref-or-value]]
            [reagent.core :as    reagent]
            [reanimated.core :as anim]
            )) 


(defn dealer-main []
  (let [showing?    (subscribe [:show-dealer-dialog?])]
    (fn []
      [popover-anchor-wrapper
         :showing? showing?
         :position :below-center
         :anchor   [:div
                     {:id "dealer-avatar"
                      :on-click #(dispatch [:show-dealer-dialog true]) }
                     [:img {:src "images/dealer_avatar.png"}] ]
         :popover  [popover-content-wrapper
                     :backdrop-opacity 0.3
                     :title    "Title"
                     :on-cancel #(dispatch [:show-dealer-dialog false])
                     :body     "Popover body text"]]
      
      
      )))  ;; v0.10.0 breaking change fix (was [popover-body showing? @position dialog-data on-change])

(defn dealer-component []
  [:div 
   {:id "dealer"}
   [dealer-main]])

