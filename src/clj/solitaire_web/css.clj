(ns solitaire-web.css
  (:require 
    [garden.def :refer [defstyles]]
    )
  )

(def card
  [:.card {:color "blue"}])


(defstyles screen
  [:body {:color "black"}]
  [:.level1 {:color "yellow"}]
  card         )
