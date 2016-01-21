(ns om-flash-bootstrap.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn warn [cursor message]
  (om/update! (cursor) {:message message :level :warning :timestamp (.getTime (js/Date.))}))

(defn bless [cursor message]
  (om/update! (cursor) {:message message :level :success :timestamp (.getTime (js/Date.))}))

(defn alert [cursor message]
  (om/update! (cursor) {:message message :level :danger :timestamp (.getTime (js/Date.))}))

(defn info [cursor message]
  (om/update! (cursor) {:message message :level :info :timestamp (.getTime (js/Date.))}))

(defn widget [{:keys [timeout flash]} owner]
  (reify
    om/IDisplayName
    (display-name [this]
      "flash")
    om/IWillReceiveProps
    (will-receive-props [this next-props]
      (when-not (empty? (flash))
        (js/clearTimeout (om/get-state owner :handler-id))
        (om/set-state! owner :handler-id (js/setTimeout #(om/update! (flash) {}) (* timeout 1000)))))
    om/IRender
    (render [_]
      (let [flash (om/observe owner (flash))           
            types {:success {:class "alert-success" :prefix "Well done!"} 
                   :info {:class "alert-info" :prefix "Info!"}
                   :warning {:class "alert-warning" :prefix "Sorry!"}
                   :danger {:class "alert-danger" :prefix "On no!"}}]
        (when-not (empty? flash)
          (dom/div #js {:id "flash"
                        :className (str "alert fade in " (:class ((:level flash) types)))}
            (dom/strong nil (:prefix ((:level flash) types)))
            (str " " (:message flash))))))))
