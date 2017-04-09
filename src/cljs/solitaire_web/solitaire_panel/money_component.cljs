(ns solitaire-web.solitaire-panel.money-component
  (:require 
    [solitaire-web.solitaire-panel.odometer :refer [odometer]]
    [re-frame.core :refer [subscribe]]))

(defn money-component []
  (let [money (subscribe [:money])]
    (fn []
      (println (str "---> " @money))
      [:div
       [odometer {:id "odometer" 
                  :class "odometer" 
                  :format "(,ddd)"
                  :minIntegerLen 3
                  :theme "car"
                  :value 0}]])))
      

