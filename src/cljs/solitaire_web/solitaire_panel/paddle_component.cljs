(ns solitaire-web.solitaire-panel.paddle-component
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe dispatch]])) 

(defn paddle-component []
  (let [coordinate (subscribe [:paddle])
        x (reaction (:x @coordinate))
        y (reaction (:y @coordinate))
        ]
    (fn []
      (println (str @x " : " @y))
      (let [translate-to (str "translate3d(" @x "%," @y "%, 0)")]
        [:div
         {:id "paddle"
          :style {
                  :transform translate-to
                  :z-index 2}}
        [:div
         {:class "glass"}
          [:p "I am a paddle blah" ]
          [:p "I am a paddle blah" ]
          [:p "I am a paddle blah" ]
          [:p "I am a paddle blah" ]
          [:p "I am a paddle blah" ]
        ]]))))
