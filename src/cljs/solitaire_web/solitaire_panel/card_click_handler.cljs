(ns solitaire-web.solitaire-panel.card-click-handler
  (:require
    [solitaire-core.public-api :refer [stablize can-be-selected? can-move? move refresh-waste]]
    [solitaire-web.solitaire-panel.coordinate-helper :refer [reset-coordinates]]
    [solitaire-web.solitaire-panel.different-piles :refer [tableau-face-up-piles foundation-piles]]
    ))

(defn select-card [cards card-id]
  (assoc-in (vec cards) [card-id :selected?] true))

(defn deselect-all [cards]
  (map #(assoc % :selected? false) cards))

(defn deselect-card [cards card-id]
  (assoc-in cards [card-id :selected?] false))

(defn handle-placeholder-click [{:keys [cards pile-name]}]
  (let [selected-card (first (filter #(= true (:selected? %)) cards))
        card-selected? #(not (nil? selected-card))
        can-perform-move? #(can-move? {:m cards :i (:index selected-card) 
                                       :from (:pile-name selected-card) :to pile-name})
        move (fn [cards] (move {:m cards :i (:index selected-card) 
                                :from (:pile-name selected-card) :to pile-name}))
        ]
    (if (and (card-selected?) (can-perform-move?))
      (-> cards 
        (move) 
        (stablize)
        (reset-coordinates)
        (deselect-all))
      (-> cards))))


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
  (let [selected-card (first (filter #(= true (:selected? %)) cards))]
  (-> cards
    (refresh-waste)
    (deselect-all)
    (reset-coordinates))))

(defmethod handle-click :face-up-card-clicked [{:keys [cards card-id]}]
  (let [selected-card (first (filter #(= true (:selected? %)) cards))
        clicked-card  (nth cards card-id)
        
        no-card-selected? #(nil? selected-card)
        clicked-card-can-be-selected?  #(can-be-selected? {:m cards :i (:index clicked-card) :from (:pile-name clicked-card)})
        
        can-perform-move? #(can-move? {:m cards :i (:index selected-card) 
                                       :from (:pile-name selected-card) :to (:pile-name clicked-card)})
        move (fn [cards] (move {:m cards :i (:index selected-card) 
                                :from (:pile-name selected-card) :to (:pile-name clicked-card)}))
        ]
    (if (no-card-selected?)
      (if (clicked-card-can-be-selected?)
        (select-card cards card-id)
        (deselect-all cards))
      (if (can-perform-move?)
        (-> cards
          (move)
          (stablize)
          (reset-coordinates)
          (deselect-all))
        (-> cards
          (deselect-all))))))
