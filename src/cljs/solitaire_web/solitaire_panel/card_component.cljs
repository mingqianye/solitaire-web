(ns solitaire-web.solitaire-panel.card-component
  (:require-macros
         [reagent.ratom :refer [reaction]])
  (:require 
    [solitaire-web.solitaire-panel.different-piles :refer [face-down-piles]]
    [reanimated.core :as anim]
    [re-frame.core :refer [dispatch dispatch-sync subscribe]]
    )
  )

(defn image-front-path [{:keys [suit rank]}]
  (str "images/cards/" rank "_of_" (name suit) "s.png"))

(def image-back-path
  "images/cards/back.png")

(defn front-y-rotation [pile-name]
  (if (contains? face-down-piles pile-name) 180 0))

(defn back-y-rotation [pile-name]
  (if (contains? face-down-piles pile-name) 0 180))


(defn card-component [card-id]
  (let [card (subscribe [:card card-id])
        x             (reaction (:x @card))
        y             (reaction (:y @card))
        cx            (anim/interpolate-to x {:duration 400})
        cy            (anim/interpolate-to y {:duration 400})
        in-animation? (reaction (or (not= @x @cx) (not= @y @cy)))
        z-index       (reaction (+ (:z-index @card) (if @in-animation? 1000 0)))
        suit          (:suit @card)
        rank          (:rank @card)
        pile-name     (reaction (:pile-name @card))
        selected?     (reaction (:selected? @card))
        r             (- (rand-int 6) 3)
        front-rotation (anim/interpolate-to (reaction (front-y-rotation @pile-name)) {:duration 400})
        back-rotation (anim/interpolate-to (reaction (back-y-rotation @pile-name)) {:duration 400})
        ]
    (fn []
      (let [translate-to (str "translate3d(" @cx "%," @cy "%, 0)") 
            rotate-to (str "rotate(" r "deg)")
            ;_ (println (str "rendered: " card-id))
            ]
        [:div
          {:on-click #(dispatch [:clicked-on-card card-id])
           :on-double-click #(println "doubled!")
           :class "card"
           :style {
                   :transform (str translate-to " " rotate-to)
                   :z-index @z-index
                   }}
          [:img {:src image-back-path
                 :class "back"
                 :style { :transform (str "rotateY(" @back-rotation "deg)") }}]
          [:img {:src (image-front-path {:suit suit :rank rank})
                 :class (str "front" " " (if @selected? "selected" "noselected"))
                 :style { :transform (str "rotateY(" @front-rotation "deg)") }}]
        ]))))
