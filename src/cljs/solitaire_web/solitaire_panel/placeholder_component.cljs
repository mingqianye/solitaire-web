(ns solitaire-web.solitaire-panel.placeholder-component
  (:require 
    [re-frame.core :refer [dispatch]])
  ) 

(defn placeholder-component [{:keys [pile-name x y]}]
  (let [translate-to (str "translate(" x "%," y "%)")]
    [:div
      {:on-click #(dispatch [:clicked-on-placeholder pile-name])
       :on-double-click #(println "doubled!")
       :class "placeholder"
       :style {:transform translate-to
               :z-index 0}}
      [:img {:src "images/placeholder.png"}]
    ]))
