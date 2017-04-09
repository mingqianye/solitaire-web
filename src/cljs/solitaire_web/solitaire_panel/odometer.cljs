(ns solitaire-web.solitaire-panel.odometer
  (:require [reagent.core :as reagent]))

(defn pad-1s [value]
  (str 1 (+ 0.001 value)))

; Wrapper of odometer javascript library
; input parameter documentation can be found here:
; http://github.hubspot.com/odometer/

(defn odometer [{:keys [id class value format duration theme animation] :as all}]
  (let [odometer-options #(merge all {:el (.getElementById js/document id)
                                      :value (pad-1s value)})]
  (reagent/create-class
    {:component-did-mount
      #(js/Odometer. (clj->js (odometer-options)))
     :reagent-render
      (fn [{:keys [value]}]
        [:div {:id id :class class} 
         (pad-1s value)])})))

(defn sample-use []
  [:div 
   [odometer {:id "odometer"
              :class "odometer"
              :value 101}]])
