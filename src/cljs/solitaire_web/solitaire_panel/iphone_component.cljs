(ns solitaire-web.solitaire-panel.iphone-component
  (:require 
    [re-frame.core :refer [subscribe]]
    [re-com.core     :refer [h-box box v-box line md-icon-button]]
    ))

(defn iphone-screen []
  (let [balance (subscribe [:balance])
        transactions (subscribe [:transaction-display])]
    (fn []
      [:div
       {:style {:color "#545454" :font-size "1vw"}}
        [:div
         {:style {:font-size "1.2vw" :font-weight "bold" :margin-bottom "0.5vw"}}
          [:i {:class "zmdi zmdi-balance zmdi-hc-lg"}]
          [:span "  Personal Checking"]]
       
        [h-box
         :children [
                    [box :size "none" :child [:span {:style {:font-weight "Bold" }}"Balance"]]
                    [box :size "1" :child ""]
                    [box :size "none" :child (str "$ " @balance)]
                    ]]

        [:hr {:style {:margin-top "0.3vw" :margin-bottom "0.3vw" :color "grey" :border-top "1px solid grey"}}]

        (for [t @transactions]
          ^{:key t}
          [h-box
           :children [
                      [box :size "none" :child (:msg t)]
                      [box :size "1" :child ""]
                      [box :size "none" :child (:amount-text t)]]])
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
