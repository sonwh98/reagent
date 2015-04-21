(defproject reagent "0.5.1-SNAPSHOT"
  :url "http://github.com/reagent-project/reagent"
  :license {:name "MIT"}
  :description "A simple ClojureScript interface to React"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3208"]
                 [cljsjs/react "0.12.2-5"]]

  :plugins [[lein-cljsbuild "1.0.4"]]

  :source-paths ["src"]
  
  :profiles {:test {:cljsbuild
                    {:builds {:client {:source-paths ["test"]}}}}

             :dev [:test
                   {:dependencies [[figwheel "0.2.3-SNAPSHOT"]]
                    :plugins [[lein-figwheel "0.2.3-SNAPSHOT"]]
                    :source-paths ["demo"] ;; for lighttable
                    :resource-paths ["site" "outsite"]
                    :figwheel {:css-dirs ["site/public/css"]}
                    :cljsbuild
                    {:builds
                     {:client
                      {:source-paths ["env/dev"]
                       :compiler {:main "reagentdemo.dev"
                                  :source-map true
                                  :source-map-timestamp true
                                  :optimizations :none
                                  :output-dir "outsite/public/js/out"
                                  :asset-path "js/out"}}}}}]
             
             :site {:resource-paths ^:replace ["outsite"]
                    :figwheel {:css-dirs ^:replace ["outsite/public/css"]}
                    :cljsbuild
                    {:builds {:client
                              {:notify-command ["node" "bin/gen-site.js"]}}}}
             
             :prod [:site
                    {:cljsbuild
                     {:builds {:client
                               {:compiler {:optimizations :advanced
                                           :elide-asserts true
                                           :pretty-print false
                                           :output-dir "target/client"}}}}}]

             :prod-test [:test :prod]
             
             :dev-notest [:dev
                          {:cljsbuild
                           {:builds {:client
                                     {:compiler {:load-tests false}}}}}]}

  :clean-targets ^{:protect false} [:target-path :compile-path
                                    "outsite/public/js"
                                    "outsite/public/site"
                                    "outsite/public/news"
                                    "outsite/public/css"
                                    "outsite/public/index.html"
                                    "out"]

  :cljsbuild {:builds {:client
                       {:source-paths ["src"
                                       "demo"
                                       "examples/todomvc/src"
                                       "examples/simple/src"
                                       "examples/geometry/src"]
                        :compiler {:output-to "outsite/public/js/main.js"}}}}
  
  :figwheel {:http-server-root "public" ;; assumes "resources"
             :repl false})
