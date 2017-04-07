(ns solitaire-web.css
  (:require 
    [garden.def :refer [defstyles]]
    )
  )

(def placeholder
  [:div.placeholder 
    {:position "absolute"
     :z-index 0
     :width "8%"}
    [:img
      {:width "100%"
       :border-radius "5px"
       :opacity "1"}]])

(def card
  [:div.card 
    {:position "absolute"
     :border-radius "5px"
     :width "8%"}
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

(def board
  [:#board
    {:background "#45a173"
     :margin-left "5%"
     :margin-right "5%"
     :width "90%"
     :height "100%"}])

(def board-container
  [:#board-container
    {:background "grey"
     :height "100vh"}])

(defstyles screen
  [:body {:color "black"}]
  board-container board card placeholder        )
