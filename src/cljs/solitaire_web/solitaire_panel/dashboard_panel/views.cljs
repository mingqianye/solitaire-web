(ns solitaire-web.solitaire-panel.dashboard_panel.views
  (:require 
    [re-frame.core :refer [dispatch dispatch-sync subscribe]]))

(defn main []
  [:div {:style {:position "absolute"
                 :right 0
                 :top 0}}
                 "dashboard"])
