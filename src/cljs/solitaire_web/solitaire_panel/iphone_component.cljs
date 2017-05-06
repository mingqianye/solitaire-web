(ns solitaire-web.solitaire-panel.iphone-component
  (:require 
    [re-frame.core :refer [subscribe]]))

(defn iphone-screen []
  (let [balance (subscribe [:balance])]
    (fn []
      [:div
        [:i {:class "zmdi zmdi-balance zmdi-hc-5x"}]
        (str "$ " @balance)
       ]
      )))



(defn iphone-component []
  [:div
   {:style {:width "18vw" :position "absolute" :left 0 :bottom 0 :transform "translate3d(20%, 50%, 0)"}}
   [:img {:src "images/iphone.png" 
          :filter "drop-shadow(9px 9px 9px rgba(0,0,0,0.3))"
          :width "100%"}]
   [iphone-screen]
   ])
