(ns solitaire-web.solitaire-panel.dealer-component
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe dispatch]]
[re-com.core  :refer [h-box v-box box gap line label checkbox radio-button button single-dropdown
                                  popover-content-wrapper popover-anchor-wrapper]]
            [re-com.util  :refer [deref-or-value]]
            [reagent.core :as    reagent]
            [reanimated.core :as anim]
            )) 


(defn popover-body
  [dialog-data on-change & {:keys [showing-injected? position-injected]}]  ;; v0.10.0 breaking change fix (was [showing? position dialog-data on-change])
  (let [dialog-data   (reagent/atom (deref-or-value dialog-data))
        submit-dialog (fn [new-dialog-data]
                        (reset! showing-injected? false)
                        (on-change new-dialog-data))
        cancel-dialog #(reset! showing-injected? false)
        show-tooltip? (reagent/atom (= (:tooltip-state @dialog-data) "2"))]
    (fn []
      [popover-content-wrapper
       :showing-injected? showing-injected?
       :position-injected position-injected
       :on-cancel         cancel-dialog
       :width            "400px"
       :backdrop-opacity 0.3
       :title            "This is the title"
       :body             [v-box
                          :children [[label
                                      :label "The body of a popover can act like a dialog box containing standard input controls."]
                                     [gap :size "15px"]
                                     [h-box
                                      :children [[v-box
                                                  :size "auto"
                                                  :children [[radio-button
                                                              :label     "Don't show extra popover"
                                                              :value     "1"
                                                              :model     (:tooltip-state @dialog-data)
                                                              :on-change (fn [val]
                                                                           (swap! dialog-data assoc :tooltip-state val)
                                                                           (reset! show-tooltip? false))]
                                                             [radio-button
                                                              :label     "Show extra popover"
                                                              :value     "2"
                                                              :model     (:tooltip-state @dialog-data)
                                                              :on-change (fn [val]
                                                                           (swap! dialog-data assoc :tooltip-state val)
                                                                           (reset! show-tooltip? true))]]]]]
                                     [gap :size "20px"]
                                     [line]
                                     [gap :size "10px"]
                                     [h-box
                                      :gap      "10px"
                                      :children [[button
                                                  :label    [:span [:i {:class "zmdi zmdi-check" }] " Apply"]
                                                  :on-click #(submit-dialog @dialog-data)
                                                  :class    "btn-primary"]
                                                 ]]]]])))

(defn dealer-main []
  (let [showing?    (reagent/atom false)
        dialog-data (reagent/atom {:tooltip-state "2"})
        on-change   (fn [new-dialog-data]
                      (reset! dialog-data new-dialog-data))]
    (fn []
      [popover-anchor-wrapper
       :showing? showing?
       :position :below-center
       :anchor   [:div
                   {:id "dealer-avatar"
                     :on-click #(reset! showing? true) }
                   [:img {:src "images/dealer_avatar.png"}] ]
       :popover  [popover-body dialog-data on-change]])))  ;; v0.10.0 breaking change fix (was [popover-body showing? @position dialog-data on-change])

(defn dealer-component []
  [:div 
   {:id "dealer"}
   [dealer-main]])
