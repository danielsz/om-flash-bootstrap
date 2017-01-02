(ns om-flash-bootstrap.core
  (:require [ring.util.response :refer [response content-type]]
            [compojure.core :refer [routes GET]]))


(defn flash-message
  "Flash handler"
  [{session :session :as request}]
  (if-let [message (:message session)]
    (let [session (dissoc session :message)]
      (-> (pr-str message)
          response
          (assoc :session session)
          (content-type "application/edn")))
    (-> (pr-str {:message nil})
        response
        (content-type "application/edn"))))



(defn flash
  "Flash route"
  [_]
  (routes
   (GET "/flash" [] flash-message)))

(defn wrap-message
  "Flash middleware"
  [handler]
  (fn [{flash :flash :as request}]
    (if flash
      (let [resp (handler request)
            session (if (:session resp)
                      (-> (:session resp)
                          (assoc :message flash))
                      (-> (:session request)
                          (assoc :message flash)))]
        (assoc resp :session session))
      (handler request))))
