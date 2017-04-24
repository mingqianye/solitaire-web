(ns solitaire-web.css
  (:require 
    [garden.def :refer [defstyles]]
    [garden.selectors :refer [span nth-child]]
    )
  )

(def card-width
  "6vw")

(def placeholder
  [:div.placeholder 
    {:position "absolute"
     :z-index 0
     :width card-width}
    [:img
      {:width "100%"
       :border-radius "5px"
       :opacity "1"}]])

(def card
  [:div.card 
    {:position "absolute"
     :border-radius "5px"
     :width card-width
     }
    [:img 
      {:width "100%"
       :border-radius "5px"
       :backface-visibility "hidden"}]
    [:img.back  
      {:box-shadow "0 1px 1px grey"
       :position "absolute"}]
    [:img.front 
      {:box-shadow "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)"
       :background "white"
       :transition "box-shadow 0.3s cubic-bezier(.25,.8,.25,1)"}]
    [:img.front:hover
      {:box-shadow "0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22)"}]
    [:img.front.selected
      {:box-shadow "0 0 60px rgba(81, 203, 238, 1)"}]])

; temporary work around for 1. adding currency 2. hide last digit
; https://github.com/HubSpot/odometer/issues/106
(def odometer
  [:#odometer {:font-size "30px"}
   ; add dollar sign
   [:.odometer-inside:before
    {:content "'$'"
     :display "inline-block"
     :vertical-align "sup"
     :opacity 0.6
     :font-size "0.85em"
     :margin-right "0.12em"}]
   ; hide first and last digit
   [:.odometer-inside
    [(span :.odometer-digit (nth-child :1))
     (span :.odometer-formatting-mark (nth-child :2))
     (span :.odometer-digit:last-child) {:display "none"}]
    ]])

(def glass
  [:#paddle
   {:position "absolute"
    :width card-width
    :height (str "calc(4.49/3.08*" card-width ")") ;(card-hight/card-width)*6 
    }
   [:.glass
    {:box-shadow "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)"
     :background-color "rgba(255,255,255,.6)"
     :border "1px solid #ccc"
     :border-radius "5px"
     :backdrop-filter "blur(5px)"
     :width "100%"
     :height "100%"
     }
    ]
  ]
  )

(def dealer
  [:#dealer
   {:width "5vw" }
   [:#dealer-avatar
    {:z-index 9999}
    [:img
      {:width "100%"}]]
   ])

(def board
  [:#board
    {:background-image "url(/images/background.png)"
     :background-color "#029948"
     :height "100vh"}])

(def board-container
  [:#board-container
   {
     }])

(defstyles screen
  [:body {:color "black"}]
  board-container board card placeholder odometer glass dealer)
