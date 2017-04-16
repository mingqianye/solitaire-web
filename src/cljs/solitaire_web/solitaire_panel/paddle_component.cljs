(ns solitaire-web.solitaire-panel.paddle-component
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe dispatch]])) 

(defn paddle-component []
  (let [coordinate (subscribe [:paddle])
        x (reaction (:x @coordinate))
        y (reaction (:y @coordinate))
        ]
    (fn []
      (let [translate-to (str "translate3d(" @x "%," @y "%, 0)")]
        [:div
         {:id "paddle"
          :style {
                  :transform (str translate-to "scale(1.1,1.2)")
                  :z-index 500}}
        [:div
         {:class "glass"}
          [:p "I am a paddle blah" ]
          [:p "I am a paddle blah" ]
          [:p "I am a paddle blah" ]
        ]]))))
