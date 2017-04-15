(ns solitaire-web.core
    (:require [reagent.core :as reagent :refer [atom]]
              [cljs.pprint :refer [pprint]]
              [solitaire-web.events]
              [solitaire-web.subs]
              [solitaire-web.solitaire-panel.views :as views]
              [re-frame.core :refer [clear-subscription-cache! dispatch dispatch-sync subscribe]]
              [solitaire-web.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn debug-component []
  (let [whole-db (subscribe [:whole-db])]
    (fn []
      [:div
        [:hr]
        [:p "DB:"]
        [:pre (with-out-str (pprint @whole-db))]])))

(defn root-component []
  (let [active  (subscribe [:active-panel])]
  (fn []
    [:div
      (condp = @active
        :solitaire-panel [views/main]
        [:p "No active panel"])
      [debug-component]
    ])))

(defn mount-root []
  (clear-subscription-cache!)
  (reagent/render [root-component]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (dispatch-sync [:initialise-db])
  (dispatch-sync [:set-active-panel :solitaire-panel])
  (dev-setup)
  (mount-root))
