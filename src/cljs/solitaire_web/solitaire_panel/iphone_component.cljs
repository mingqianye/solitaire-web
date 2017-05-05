(ns solitaire-web.solitaire-panel.iphone-component
  (:require 
    [re-frame.core :refer [subscribe]]))

(defn iphone-component []
  (let [balance (subscribe [:balance])]
    (fn []
    [:div
     {:style {:position "absolute" :left 0 :bottom 0}}
     [:i {:class "zmdi zmdi-balance zmdi-hc-5x"}]
     (str "$ " @balance)
     ])))
