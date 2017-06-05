(ns solitaire-web.solitaire-panel.dashboard-panel.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]
            ))

(defn calc-progress [{:keys [now last-clicked-at will-be-done-at]}]
  (let [raw-num (/ (- now last-clicked-at) (- will-be-done-at last-clicked-at))]
    (if (<= 0 raw-num 1)
      (int (* 100 raw-num ))
      0)))


(reg-sub :dashboard-panel
  (fn [db _]
    (:dashboard-panel db)))

(reg-sub :total-money
  :<- [:dashboard-panel]
  (fn [panel _]
    (:total-money panel)))

(reg-sub :total-candies
  :<- [:dashboard-panel]
  :<- [:money]
  (fn [[panel money] _]
    (+ (:total-candies panel) money)))

(reg-sub :now
  :<- [:dashboard-panel]
  (fn [panel _]
    (:now panel)))

(reg-sub :container-capacity
  :<- [:dashboard-panel]
  (fn [panel _]
    (:container-capacity panel)))

(reg-sub :container-capacity-progress
  :<- [:container-capacity]
  :<- [:total-candies]
  (fn [[capacity total] _]
    (int (* 100 (/ total capacity)))))


(reg-sub :add-candies-btn-cooldown-progress
  :<- [:dashboard-panel]
  (fn [panel _]
    (calc-progress {:now (:now panel)
                    :last-clicked-at (:add-candies-btn-last-clicked panel)
                    :will-be-done-at (:add-candies-btn-reactivate-at panel)})))

(reg-sub :add-candies-btn-in-cooldown?
  :<- [:add-candies-btn-cooldown-progress]
  (fn [percent _]
    (< 0 percent 100)))

(reg-sub :sell-candies-btn-cooldown-progress
  :<- [:dashboard-panel]
  (fn [panel _]
    (calc-progress {:now (:now panel)
                    :last-clicked-at (:sell-candies-btn-last-clicked panel)
                    :will-be-done-at (:sell-candies-btn-reactivate-at panel)})))

(reg-sub :sell-candies-btn-in-cooldown?
  :<- [:sell-candies-btn-cooldown-progress]
  (fn [percent _]
    (< 0 percent 100)))
