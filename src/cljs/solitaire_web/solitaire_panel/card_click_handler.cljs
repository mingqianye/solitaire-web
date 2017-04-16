(ns solitaire-web.solitaire-panel.card-click-handler
  (:require
    [solitaire-core.public-api :refer [can-be-selected? can-move? move refresh-waste]]
    [solitaire-web.solitaire-panel.coordinate-helper :refer [reset-coordinates]]
    [solitaire-web.solitaire-panel.different-piles :refer [tableau-face-up-piles foundation-piles]]
    ))

(defn first-selected-card [cards]
  (->> cards 
    (filter #(= true (:selected? %))) 
    (apply min-key :index-in-pile)))


(defn select-cards [cards card-id]
  (let [pile-name (get-in cards [card-id :pile-name])
        index-in-pile (get-in cards [card-id :index-in-pile])]
    (->> cards
      (map (fn [card] (if (and (= (:pile-name card) pile-name) (>= (:index-in-pile card) index-in-pile))
                        (assoc card :selected? true)
                        card)))
      (vec))))

(defn deselect-all [cards]
  (->> cards
    (map #(assoc % :selected? false))
    (vec)))

(defn deselect-card [cards card-id]
  (assoc-in cards [card-id :selected?] false))

(defn handle-placeholder-click [{:keys [cards pile-name]}]
  (let [selected-card (first-selected-card cards)
        card-selected? #(not (nil? selected-card))
        can-perform-move? #(can-move? {:m cards :i (:index-in-pile selected-card) 
                                       :from (:pile-name selected-card) :to pile-name})
        move (fn [cards] (move {:m cards :i (:index-in-pile selected-card) 
                                :from (:pile-name selected-card) :to pile-name}))
        ]
    (if (= :stock pile-name)
      (-> cards (refresh-waste) (reset-coordinates))
      (if (and (card-selected?) (can-perform-move?))
        (-> cards 
          (move) 
          (reset-coordinates))
        (-> cards)))))


(defn dispatch-click [{:keys [cards card-id]}]
  (let [pile-name (:pile-name (nth cards card-id))]
    (cond
      (contains? tableau-face-up-piles pile-name) :face-up-card-clicked
      (contains? foundation-piles pile-name) :face-up-card-clicked
      (= :waste pile-name) :face-up-card-clicked
      (= :stock pile-name) :stock-clicked
      :else (println "ERR"))))

(defmulti handle-click dispatch-click)

(defmethod handle-click :stock-clicked [{:keys [cards]}]
  (let [selected-card (first-selected-card cards)]
  (-> cards
    (refresh-waste)
    (deselect-all)
    (reset-coordinates))))

(defmethod handle-click :face-up-card-clicked [{:keys [cards card-id]}]
  (let [selected-card (first-selected-card cards)
        clicked-card  (nth cards card-id)
        
        no-card-selected? #(nil? selected-card)
        clicked-card-can-be-selected?  #(can-be-selected? {:m cards :i (:index-in-pile clicked-card) :from (:pile-name clicked-card)})
        
        can-perform-move? #(can-move? {:m cards :i (:index-in-pile selected-card) 
                                       :from (:pile-name selected-card) :to (:pile-name clicked-card)})
        move (fn [cards] (move {:m cards :i (:index-in-pile selected-card) 
                                :from (:pile-name selected-card) :to (:pile-name clicked-card)}))
        ]
    (if (no-card-selected?)
      (if (clicked-card-can-be-selected?)
        (select-cards cards card-id)
        (deselect-all cards))
      (if (can-perform-move?)
        (-> cards
          (move)
          (reset-coordinates))
        (-> cards
          (deselect-all))))))
