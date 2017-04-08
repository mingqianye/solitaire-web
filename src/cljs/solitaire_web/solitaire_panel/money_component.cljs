(ns solitaire-web.solitaire-panel.money-component
  (:require 
    [solitaire-web.solitaire-panel.odometer :refer [odometer]]
    [re-frame.core :refer [subscribe]]))

(defn money-component []
  (let [money (subscribe [:money])]
    (fn []
      (println "here")
      [:div
       [odometer {:id "odometer" 
                  :class "odometer" 
                  :value @money}]])))
      

