(ns solitaire-web.solitaire-panel.placeholder-component
  (:require [re-frame.core :refer [subscribe dispatch]] 
            [solitaire-web.solitaire-panel.coordinates :as c]
            [solitaire-web.solitaire-panel.different-piles :refer [foundation-piles tableau-face-up-piles counter-pile]]
            ))

(defn stock-placeholder-component []
  (let [x (get-in c/settings [:stock :x])
        y (get-in c/settings [:stock :y])
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

(defn placeholder-components []
  (let [pile-names [:foundation-1 :foundation-2 :foundation-3 :foundation-4 :tableau-1-face-up :tableau-2-face-up :tableau-3-face-up :tableau-4-face-up :tableau-5-face-up :tableau-6-face-up :tableau-7-face-up]]
    [:div
      (for [ph foundation-piles]
        ^{:key ph} [placeholder-component {:pile-name ph 
                                           :x (get-in c/settings [ph :x])
                                           :y (get-in c/settings [ph :y])}])
      (for [ph tableau-face-up-piles]
        ^{:key ph} [placeholder-component {:pile-name ph 
                                           :x (get-in c/settings [(ph counter-pile) :x])
                                           :y (get-in c/settings [(ph counter-pile) :y])}])
     ]
    ))

