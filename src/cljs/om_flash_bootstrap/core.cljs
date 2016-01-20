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
    om/IInitState
    (init-state [_]
      {:display/state :hidden})
    om/IWillReceiveProps
    (will-receive-props [this next-props]
      (when (not (empty? next-props))
        (om/set-state! owner :display/state :show)
        (js/clearTimeout (om/get-state owner :handler-id))
        (om/set-state! owner :handler-id (js/setTimeout #(om/set-state! owner :display/state :hidden) (* timeout 1000)))))
    om/IRenderState
    (render-state [_ state]
      (let [flash (om/observe owner (flash))
            types {:success {:class "alert-success" :prefix "Well done!"} 
                   :info {:class "alert-info" :prefix "Info!"}
                   :warning {:class "alert-warning" :prefix "Sorry!"}
                   :danger {:class "alert-danger" :prefix "On no!"}}]
        (case (:display/state state)
          :show
          (dom/div #js {:id "flash"
                        :className (str "alert fade in " (:class ((:level flash) types)))}
                   (dom/strong nil (:prefix ((:level flash) types)))
                   (str " " (:message flash)))
          :hidden nil)))))
