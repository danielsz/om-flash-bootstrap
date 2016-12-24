(set-env!
 :source-paths   #{"src/cljs" "src/clj"}
 :resource-paths #{"src/cljs" "src/clj"}
 :dependencies '[[cljs-ajax "0.5.4"]
                 [compojure "1.5.0"]])

(task-options!
 pom {:project 'org.danielsz/om-flash-bootstrap
      :version "0.1.0-SNAPSHOT"
      :scm {:name "git"
            :url "https://github.com/danielsz/om-flash-bootstrap"}})

(deftask build
  []
  (comp (pom) (jar) (install)))

(deftask push-release
  []
  (comp
   (build)
   (push :repo "clojars")))
