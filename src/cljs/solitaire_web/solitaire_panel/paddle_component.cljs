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

         
        [:div
         {:id "paddle"
          :style {
                  :transform translate-to
                  :z-index 500}}
            [:div
             {:style {:width "1.5vw"
                      :transform-origin "center bottom"
                      :transform "translate3d(2.2vw, -9.5vw, 0)"
                      :z-index 1
                      :position "absolute"}}
              [:img {:src "images/handlebar.png" :width "100%"}]]
          [:div
           {:class "glass"
            :style { :transform-origin "center top"
                     :transform scale-to 
                     :z-index 0
                    }}
           ]
         ]
        
         ]
        
        ))))
