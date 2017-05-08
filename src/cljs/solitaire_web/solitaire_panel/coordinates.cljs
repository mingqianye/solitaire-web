(ns solitaire-web.solitaire-panel.coordinates
  (:require [solitaire-web.solitaire-panel.different-piles :refer [foundation-piles
                                                                   tableau-face-up-piles
                                                                   tableau-face-down-piles]]))

(def settings
  {
   :paddle-top-offset {:x 0 :y -15}
   :paddle-default {:x 200 :y -500}
   })

(def origins
  [{:x 0         :y 0   :z-index 0}
   {:x 220       :y 0   :z-index 0}
   {:x 200       :y 0   :z-index 52}
   {:x (* 130 3) :y 0   :z-index 0}
   {:x (* 130 4) :y 0   :z-index 0}
   {:x (* 130 5) :y 0   :z-index 0}
   {:x (* 130 6) :y 0   :z-index 0}
   {:x (* 130 0) :y 130 :z-index 0}
   {:x (* 130 1) :y 130 :z-index 0}
   {:x (* 130 2) :y 130 :z-index 0}
   {:x (* 130 3) :y 130 :z-index 0}
   {:x (* 130 4) :y 130 :z-index 0}
   {:x (* 130 5) :y 130 :z-index 0}
   {:x (* 130 6) :y 130 :z-index 0}])

(def deltas
  [{:x 0.4 :y 0.4 :z-index 1}
   {:x 0   :y 0   :z-index 1}
   {:x 20  :y 0   :z-index 1}
   {:x 0   :y 0   :z-index 1}
   {:x 0   :y 0   :z-index 1}
   {:x 0   :y 0   :z-index 1}
   {:x 0   :y 0   :z-index 1}
   {:x 0   :y 20  :z-index 1}
   {:x 0   :y 20  :z-index 1}
   {:x 0   :y 20  :z-index 1}
   {:x 0   :y 20  :z-index 1}
   {:x 0   :y 20  :z-index 1}
   {:x 0   :y 20  :z-index 1}
   {:x 0   :y 20  :z-index 1}])

(def lookup-matrix
  (->> 
    (for [pile-id (range (+ 13 1))
          idx (range (+ 52 1))]
      {:pile-id pile-id
       :idx     idx
       :x       (+ (get-in origins [pile-id :x])       (* idx (get-in deltas [pile-id :x])))
       :y       (+ (get-in origins [pile-id :y])       (* idx (get-in deltas [pile-id :y])))
       :z-index (+ (get-in origins [pile-id :z-index]) (* idx (get-in deltas [pile-id :z-index])))})
    (group-by (fn [e] {:pile-id (:pile-id e) :idx (:idx e)}))))

(defn lookup-coordinates [{:keys [pile-id idx]}]
  (get-in lookup-matrix [{:pile-id pile-id :idx idx} 0]))

(defn lookup-pile-id-and-idx [{:keys [pile-name index-in-pile pile-counts]}]
  (cond 
    (= pile-name :stock) {:pile-id 0 :idx index-in-pile}
    (and (= pile-name :waste) (< index-in-pile (- (:waste pile-counts) 3)))
      {:pile-id 1 :idx index-in-pile}
    (and (= pile-name :waste) (>= index-in-pile (- (:waste pile-counts) 3)))
      {:pile-id 2 :idx (- index-in-pile (- (:waste pile-counts) 3))}
    (= pile-name :foundation-1)        {:pile-id 3  :idx index-in-pile}
    (= pile-name :foundation-2)        {:pile-id 4  :idx index-in-pile}
    (= pile-name :foundation-3)        {:pile-id 5  :idx index-in-pile}
    (= pile-name :foundation-4)        {:pile-id 6  :idx index-in-pile}
    (= pile-name :tableau-1-face-down) {:pile-id 7  :idx index-in-pile}
    (= pile-name :tableau-1-face-up)   {:pile-id 7  :idx (+ index-in-pile (:tableau-1-face-down pile-counts))}
    (= pile-name :tableau-2-face-down) {:pile-id 8  :idx index-in-pile}
    (= pile-name :tableau-2-face-up)   {:pile-id 8  :idx (+ index-in-pile (:tableau-2-face-down pile-counts))}
    (= pile-name :tableau-3-face-down) {:pile-id 9  :idx index-in-pile}
    (= pile-name :tableau-3-face-up)   {:pile-id 9  :idx (+ index-in-pile (:tableau-3-face-down pile-counts))}
    (= pile-name :tableau-4-face-down) {:pile-id 10 :idx index-in-pile}
    (= pile-name :tableau-4-face-up)   {:pile-id 10 :idx (+ index-in-pile (:tableau-4-face-down pile-counts))}
    (= pile-name :tableau-5-face-down) {:pile-id 11 :idx index-in-pile}
    (= pile-name :tableau-5-face-up)   {:pile-id 11 :idx (+ index-in-pile (:tableau-5-face-down pile-counts))}
    (= pile-name :tableau-6-face-down) {:pile-id 12 :idx index-in-pile}
    (= pile-name :tableau-6-face-up)   {:pile-id 12 :idx (+ index-in-pile (:tableau-6-face-down pile-counts))}
    (= pile-name :tableau-7-face-down) {:pile-id 13 :idx index-in-pile}
    (= pile-name :tableau-7-face-up)   {:pile-id 13 :idx (+ index-in-pile (:tableau-7-face-down pile-counts))}
    :else (println "ERRRR cannot find pile")))

(defn find-coordinates [card pile-counts]
  (let [pile-id-and-idx (lookup-pile-id-and-idx {:pile-name (:pile-name card)
                                                 :index-in-pile (:index-in-pile card)
                                                 :pile-counts pile-counts})]
    (lookup-coordinates pile-id-and-idx)))

; Yikes! In order to make tableau 4 in the center of the screen,
; we need to shift the whole board to the right
(def board-left-padding
  (let [card-width-in-vw 6
        t4-top-left (get-in origins [10 :x])
        t4-center (+ t4-top-left 50)
        t4-center-in-vw (* t4-center 0.01 card-width-in-vw)
        screen-center-in-vw 50
        shift-needed (- screen-center-in-vw t4-center-in-vw)]
    (str shift-needed "vw")))
