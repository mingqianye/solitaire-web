(ns solitaire-web.solitaire-panel.iphone-component
  (:require 
    [re-frame.core :refer [subscribe]]
    [re-com.core     :refer [h-box box v-box line md-icon-button]]
    ))

(defn iphone-screen []
  (let [balance (subscribe [:balance])]
    (fn []
      [:div
       {:style {:color "#545454"}}
        [:i {:class "zmdi zmdi-balance zmdi-hc-lg"}]
        [:span {:style {:font-size "15px"
                        :font-weight "bold"}}
         "  Personal Checking"]
        [:br]
        [h-box
         :children [
                    [box :size "none" :child "Balance"]
                    [box :size "1" :child ""]
                    [box :size "none" :child (str "$ " @balance)]
                    ]]
        [:br]
        [h-box
         :children [
                    [box :size "none" :child "VEGAS SOLI. BUY-IN..."]
                    [box :size "1" :child ""]
                    [box :size "none" :child (str "-$52")]
                    ]]
       ]
      )))



(defn iphone-component []
  [:div
   {:style {:width "18vw" :position "absolute" :left 0 :bottom 0 :transform "translate3d(20%, 60%, 0)"}}

   [:div
    {:style {:position "absolute"
             :margin-top "5vw"
             :margin-left "2vw"
             :margin-right "2vw"
             :width "14vw"
             :z-index 1
             
             
             }}
    [iphone-screen]]

    
   [:img {:src "images/iphone.png" 
          :style {:filter "drop-shadow(9px 9px 9px rgba(0,0,0,0.3))"}
          :width "100%"}]
   ])
