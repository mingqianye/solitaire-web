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
        x         (reaction (:x @card))
        y         (reaction (:y @card))
        cx        (anim/interpolate-to x {:duration 300})
        cy        (anim/interpolate-to y {:duration 300})
        r         (- (rand-int 6) 3)
        z-index   (reaction (:z-index @card))
        width     (reaction (:width @card))
        suit      (:suit @card)
        rank      (:rank @card)
        pile-name (reaction (:pile-name @card))
        selected? (reaction (:selected? @card))
        front-rotation (anim/interpolate-to (reaction (front-y-rotation @pile-name)) {:duration 300})
        back-rotation (anim/interpolate-to (reaction (back-y-rotation @pile-name)) {:duration 300})
        ]
    (fn []
      (let [translate-to (str "translate3d(" @cx "%," @cy "%, 0)") 
            rotate-to (str "rotate(" r "deg)")
            ;_ (println (str "rendered: " card-id))
            the-width (str @width "%")]
        [:div
          {:on-click #(dispatch [:clicked-on-card card-id])
           :on-double-click #(println "doubled!")
           :style {
                   :position "absolute"
                   :width the-width
                   :transform (str translate-to " " rotate-to)
                   :z-index @z-index
                   }}
          [:img {:src image-back-path
                 :style {
                         :width "100%"
                         :box-shadow "0 1px 1px grey"
                         :border-radius "5px"
                         :backface-visibility "hidden"
                         :transform (str "rotateY(" @back-rotation "deg)")
                         :position "absolute"
                       }}]
          [:img {:src (image-front-path {:suit suit :rank rank})
                 :style {
                         :width "100%"
                         :box-shadow "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)"
                         :border-radius "5px"
                         :background (if @selected? "yellow" "white")
                         :backface-visibility "hidden"
                         :transform (str "rotateY(" @front-rotation "deg)")
                       }}]
        ]))))
