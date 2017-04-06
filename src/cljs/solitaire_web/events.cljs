(ns solitaire-web.events
    (:require [re-frame.core :as re-frame]
              [solitaire-web.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
