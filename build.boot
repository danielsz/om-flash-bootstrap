(set-env!
 :source-paths   #{"src/cljs"}
 :resource-paths #{"src/cljs"}
 :dependencies '[[cljs-ajax "0.5.4"]])

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
