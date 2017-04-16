(ns solitaire-web.solitaire-panel.paddle-component
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [reanimated.core :as anim]
            )) 

(defn paddle-component []
  (let [coordinate (subscribe [:paddle])
        x (reaction (:x @coordinate))
        y (reaction (:y @coordinate))
        cx            (anim/interpolate-to x {:duration 400})
        cy            (anim/interpolate-to y {:duration 400})
        in-animation? (reaction (or (not= @x @cx) (not= @y @cy)))
        ]
    (fn []
      (let [translate-to (str "translate3d(" @cx "%," @cy "%, 0)")]
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
