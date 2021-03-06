(ns solitaire-web.solitaire-panel.money-component
  (:require 
    [solitaire-web.solitaire-panel.odometer :refer [odometer]]
    [re-frame.core :refer [subscribe]]))

(defn money-component []
  (let [money (subscribe [:total-money])]
    (fn []
      [:div
       [odometer {:id "odometer" 
                  :class "odometer" 
                  :format "(,ddd)"
                  :theme "car"
                  :value @money}]])))
      

