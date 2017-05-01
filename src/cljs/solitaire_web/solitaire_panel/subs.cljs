(ns solitaire-web.solitaire-panel.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]
            [solitaire-core.public-api :refer [can-be-selected?]]
            [solitaire-web.solitaire-panel.different-piles :refer [foundation-piles]]
            [solitaire-web.solitaire-panel.paddle-helper :refer [get-paddle]]
            ))

;; layer 2 (data extraction)

(reg-sub :solitaire-panel
  (fn [db _]
    (:solitaire-panel db)))

(reg-sub :able-to-refresh-stock?
  :<- [:solitaire-panel]
  (fn [panel _]
    (< (:stock-placeholder-num-clicks panel) 2))) ; can only go through the deck 3 times

(reg-sub :dealer
  :<- [:solitaire-panel]
  (fn [panel _]
    (:dealer panel)))

(reg-sub :dealer-dialog-visible?
  :<- [:dealer]
  (fn [dealer _]
    (:show-dialog? dealer)))

(reg-sub :dealer-avatar
  :<- [:dealer]
  (fn [dealer _]
    (:avatar dealer)))

(reg-sub :dealer-dialog
  :<- [:dealer]
  (fn [dealer _]
    (:dialog dealer)))

(reg-sub :cards
  :<- [:solitaire-panel]
  (fn [panel _]
    (:cards panel)))

(reg-sub :in-animation?
  :<- [:cards]
  (fn [cards _]
    (not-every? false? (map :in-animation? cards))))

(reg-sub :card
  :<- [:cards]
  (fn [cards [_ card-id]]
    (nth cards card-id)))

(reg-sub :money
  :<- [:cards]
  (fn [cards _]
    (->> cards 
      (filter #(contains? foundation-piles (:pile-name %)))
      (count)
      (* 5.0))))

(reg-sub :paddle
  :<- [:cards]
  (fn [cards _]
    (get-paddle cards)))
