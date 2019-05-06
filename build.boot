(set-env!
 :source-paths   #{"src/cljs" "src/clj"}
 :resource-paths #{"resources" "src/cljs" "src/clj"}
 :dependencies '[[compojure "1.6.1"]])

(task-options!
 push {:repo-map {:url "https://clojars.org/repo/"}}
 pom {:project 'org.danielsz/om-flash-bootstrap
      :version "0.1.0"
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
