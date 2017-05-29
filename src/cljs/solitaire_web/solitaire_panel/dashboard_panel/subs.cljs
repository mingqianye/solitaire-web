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

(reg-sub :now
  :<- [:dashboard-panel]
  (fn [panel _]
    (:now panel)))


(reg-sub :add-candies-btn-cooldown-progress
  :<- [:dashboard-panel]
  (fn [panel _]
    (let [now             (:now panel)
          last-clicked-at (:add-candies-btn-last-clicked panel)
          cooldown-at     (:add-candies-btn-cooldown-at panel)
          raw             (/ (- now last-clicked-at) (- cooldown-at last-clicked-at))]
      (* 100 (min raw 1)))))

(reg-sub :add-candies-btn-in-cooldown?
  :<- [:add-candies-btn-cooldown-progress]
  (fn [percent _]
    (< percent 100)))
