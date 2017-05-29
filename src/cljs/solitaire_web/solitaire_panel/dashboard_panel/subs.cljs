(ns solitaire-web.solitaire-panel.dashboard-panel.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]
            ))

;(reg-sub :solitaire-panel
;  (fn [db _]
;    (:solitaire-panel db)))

(reg-sub :dashboard-panel
  :<- [:solitaire-panel]
  (fn [panel _]
    (:dashboard-panel panel)))

(reg-sub :total-money
  :<- [:dashboard-panel]
  (fn [panel _]
    (:total-money panel)))

(reg-sub :total-candies
  :<- [:dashboard-panel]
  (fn [panel _]
    (:total-candies panel)))
