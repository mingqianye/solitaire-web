(ns solitaire-web.solitaire-panel.money-component
  (:require [re-frame.core :refer [subscribe]]))

(defn money-component []
  (let [money (subscribe [:money])]
    (fn []
      [:p (str "money: " @money)])))
