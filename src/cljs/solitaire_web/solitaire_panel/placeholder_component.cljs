(ns solitaire-web.solitaire-panel.placeholder-component
  (:require [re-frame.core :refer [subscribe dispatch]] 
            [solitaire-web.solitaire-panel.coordinates :as c]
            [solitaire-web.solitaire-panel.different-piles :refer [foundation-piles tableau-face-up-piles counter-pile]]
            ))

(defn stock-placeholder-component []
  (let [x (get-in c/origins [0 :x])
        y (get-in c/origins [0 :y])
        translate-to (str "translate3d(" x "%," y "%, 0)")
        able-to-refresh? (subscribe [:able-to-refresh-stock?])
        ]
    (fn []
      [:div
        {:on-click #(if @able-to-refresh?
                       (dispatch [:clicked-on-stock-placeholder])
                       (println "Cannot refresh stock anymore"))
         :on-double-click #(println "doubleddddd!")
         :class "placeholder"
         :style {:transform translate-to}}
        [:img {:src (if @able-to-refresh?
                      "images/placeholder_refresh.png"
                      "images/placeholder_forbidden.png")}]
      ]
    )))

(defn placeholder-component [{:keys [pile-name x y]}]
  (let [translate-to (str "translate3d(" x "%," y "%, 0)")]
    [:div
      {:on-click #(dispatch [:clicked-on-placeholder pile-name])
       :on-double-click #(println "doubled!")
       :class "placeholder"
       :style {:transform translate-to}}
      [:img {:src "images/placeholder_empty.png"}]
    ])
  )

(def pile-id-mappings
  [{:pile-name :foundation-1      :idx 3 }
   {:pile-name :foundation-2      :idx 4 }
   {:pile-name :foundation-3      :idx 5 }
   {:pile-name :foundation-4      :idx 6 }
   {:pile-name :tableau-1-face-up :idx 7 }
   {:pile-name :tableau-2-face-up :idx 8 }
   {:pile-name :tableau-3-face-up :idx 9 }
   {:pile-name :tableau-4-face-up :idx 10}
   {:pile-name :tableau-5-face-up :idx 11}
   {:pile-name :tableau-6-face-up :idx 12}
   {:pile-name :tableau-7-face-up :idx 13}])

(defn placeholder-components []
    [:div
      (for [pm pile-id-mappings]
        ^{:key pm} [placeholder-component {:pile-name (:pile-name pm)
                                           :x (get-in c/origins [(:idx pm) :x])
                                           :y (get-in c/origins [(:idx pm) :y])}])])

