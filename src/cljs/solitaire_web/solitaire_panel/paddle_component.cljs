(ns solitaire-web.solitaire-panel.paddle-component
  (:require [re-frame.core :refer [dispatch]])) 

(defn paddle-component []
    (fn []
      [:div
       {:id "paddle"
        :style {:left "200px"
                :z-index 2}}
      [:div
       {:class "glass"}
        [:p "I am a paddle blah" ]
        [:p "I am a paddle blah" ]
        [:p "I am a paddle blah" ]
        [:p "I am a paddle blah" ]
        [:p "I am a paddle blah" ]
      ]])
  )
