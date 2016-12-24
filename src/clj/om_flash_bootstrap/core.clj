(ns om-flash-bootstrap.core
  (:require [ring.util.response :refer [response content-type]]))

(defn flash-message [{session :session :as request}]
  (if-let [message (:message session)]
    (let [session (dissoc session :message)]
      (-> (pr-str message)
          response
          (assoc :session session)
          (content-type "application/edn")))
    (-> (pr-str {:message nil})
        response
        (content-type "application/edn"))))

(defn flash [_]
  (routes
   (GET "/flash" [] flash-message)))
