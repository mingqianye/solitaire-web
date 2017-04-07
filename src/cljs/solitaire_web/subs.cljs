(ns solitaire-web.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub :whole-db
  (fn [db _]
    db))

(reg-sub :active-panel
  (fn [db _]
    (:active-panel db)))
