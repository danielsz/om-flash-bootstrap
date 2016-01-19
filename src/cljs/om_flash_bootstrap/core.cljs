(ns om-flash-bootstrap.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn warn [state message]
  (let [cursor (om/ref-cursor (:flash (om/root-cursor state)))]
  (om/update! cursor {:message message :level :warning})))

(defn bless [state message]
  (let [cursor (om/ref-cursor (:flash (om/root-cursor state)))]
    (om/update! cursor {:message message :level :success})))

(defn alert [state message]
  (let [cursor (om/ref-cursor (:flash (om/root-cursor state)))]
    (om/update! cursor {:message message :level :danger})))

(defn info [state message]
  (let [cursor (om/ref-cursor (:flash (om/root-cursor state)))]
  (om/update! cursor {:message message :level :info})))

(defn widget [data owner]
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
        (om/set-state! owner :handler-id (js/setTimeout #(do (om/update! data {})
                                                             (om/set-state! owner :display/state :hidden)) 2000))))
    om/IRenderState
    (render-state [_ state]
      (let [types {:success {:class "alert-success" :prefix "Well done!"} 
                   :info {:class "alert-info" :prefix "Info!"}
                   :warning {:class "alert-warning" :prefix "Sorry!"}
                   :danger {:class "alert-danger" :prefix "On no!"}}]
        (case (:display/state state)
          :show
          (dom/div #js {:id "flash"
                        :className (str "alert fade in " (:class ((:level data) types)))}
                   (dom/strong nil (:prefix ((:level data) types)))
                   (str " " (:message data)))
          :hidden nil)))))
