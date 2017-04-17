(ns solitaire-web.solitaire-panel.paddle-component
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [subscribe dispatch]]
            [reanimated.core :as anim]
            )) 

(defn paddle-component []
  (let [paddle (subscribe [:paddle])
        x (reaction (:x @paddle))
        y (reaction (:y @paddle))
        cx            (anim/interpolate-to x {:duration 400})
        cy            (anim/interpolate-to y {:duration 400})
        ]
    (fn []
      (let [translate-to (str "translate3d(" @cx "%," @cy "%, 0)")
            scale-to (str "scale(1.1," (:scale-y @paddle) ")")]
        [:div
         {:id "paddle"
          :style {
                  :transform translate-to
                  :z-index 500}}
        [:div
         {:class "glass"
          :style { :transform-origin "center top"
                   :transform scale-to 
                  }}
        ]]))))
