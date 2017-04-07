(ns solitaire-web.css
  (:require 
    [garden.def :refer [defstyles]]
    )
  )

(def placeholder
  [:div.placeholder 
    {:position "absolute"
     :width "8%"}
    [:img
      {:width "100%"
       :border-radius "5px"
       :opacity "1"}]])

(def card
  [:div.card 
    {:position "absolute"
     :width "8%"}
    [:img 
      {:width "100%"
       :border-radius "5px"
       :backface-visibility "hidden"}]
    [:img.back  
      {:box-shadow "0 1px 1px grey"
       :position "absolute"}]
    [:img.front 
      {:box-shadow "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)"}]
    [:img.front.selected
      {:background "yellow"}]
    [:img.front.noselected
      {:background "white"}]
           ])


(defstyles screen
  [:body {:color "black"}]
  card placeholder        )
