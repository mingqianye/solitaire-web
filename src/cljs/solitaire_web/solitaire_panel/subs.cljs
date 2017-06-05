(ns solitaire-web.solitaire-panel.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]
            [solitaire-core.public-api :refer [can-be-selected? won?]]
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


(reg-sub :transactions
  :<- [:solitaire-panel]
  (fn [panel _]
    (:transactions panel)))

(reg-sub :balance
  :<- [:transactions]
  (fn [transactions _]
    (apply + (map :amount transactions))))

(reg-sub :transaction-display
  :<- [:transactions]
  (fn [transactions _]
    (let [negativity #(if (>= % 0) "+" "-")
          pure-amount #(max % (* -1 %))
          displayable (fn [amount]
                         (str (negativity amount) "$" (pure-amount amount)))]
      (->> transactions
        (map-indexed (fn [idx tr] {:id          idx
                                   :msg         (:msg tr)
                                   :amount-text (displayable (:amount tr))}))
        (reverse)))))

(reg-sub :deal-cards-button-endabled?
  :<- [:dashboard-panel]
  (fn [panel _]
    (>= (:total-candies panel) (:buy-in-cost panel))))

(reg-sub :dealer
  :<- [:solitaire-panel]
  (fn [panel _]
    (:dealer panel)))

(reg-sub :dealer-dialog-visible?
  :<- [:dealer]
  (fn [dealer _]
    (:show-dialog? dealer)))

(reg-sub :dealer-scene
  :<- [:dealer]
  (fn [dealer _]
    (:scene dealer)))

(reg-sub :cards
  :<- [:solitaire-panel]
  (fn [panel _]
    (:cards panel)))

(reg-sub :won?
  :<- [:cards]
  (fn [cards _]
    ; need the nil check because there were intialization issues
    (and (not (nil? cards)) (won? cards))))

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


