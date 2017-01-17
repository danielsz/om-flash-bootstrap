(set-env!
 :source-paths   #{"src/cljs" "src/clj"}
 :resource-paths #{"resources" "src/cljs" "src/clj"}
 :dependencies '[[cljs-ajax "0.5.8"]
                 [compojure "1.5.2"]])

(task-options!
 push {:repo-map {:url "https://clojars.org/repo/"}}
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
   (push)))
