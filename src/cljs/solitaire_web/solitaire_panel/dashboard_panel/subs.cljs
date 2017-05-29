(ns solitaire-web.solitaire-panel.dashboard-panel.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]
            ))

(reg-sub :dashboard-panel
  (fn [db _]
    (:dashboard-panel db)))

(reg-sub :total-money
  :<- [:dashboard-panel]
  (fn [panel _]
    (:total-money panel)))

(reg-sub :total-candies
  :<- [:dashboard-panel]
  (fn [panel _]
    (:total-candies panel)))

(reg-sub :last-updated-at
  :<- [:dashboard-panel]
  (fn [panel _]
    (:last-updated-at panel)))
