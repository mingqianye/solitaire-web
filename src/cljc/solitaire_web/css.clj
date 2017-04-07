(ns solitaire-web.css
  (:require [garden.def :refer [defstyles]])
  )

(defstyles screen
  [:body {:color "black"}]
  [:.level1 {:color "green"}])
