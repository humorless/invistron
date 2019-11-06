(set-env! :source-paths #{"src" "content"}
          :resource-paths #{"resources"}
          :dependencies '[[org.clojure/clojure "1.9.0"] ;
                          [perun "0.4.2-SNAPSHOT" :scope "test"] ;
                          [hiccup "2.0.0-alpha1"] ;
                          [enlive "1.1.6"] ;
                          [boot-fmt/boot-fmt "0.1.8"] ;
                          [boot-cljfmt "0.1.3" :scope "test"]
                          [pandeiro/boot-http "0.8.3"] ;
                          [boot-deps "0.1.9"] ;
                          [deraen/boot-sass "0.3.1"] ;
                         ])

(require '[io.perun :refer :all]
         '[site.core]
         '[clojure.string :as str]
         '[boot-fmt.core :refer [fmt]]
         '[pandeiro.boot-http :refer [serve]]
         '[deraen.boot-sass :refer [sass]])

(defn filter-builder [prefix] (comp #(str/starts-with? % prefix) :path))

(def product? (filter-builder "public/product"))
(def major? (comp #(= % true) :major))
(def home? (comp #(= % true) :home))
(def active? (comp #(= % "Active Parts") :type))
(def passive? (comp #(= % "Passive Components") :type))
(def electro? (comp #(= % "Electromechanical") :type))
(def wireless? (comp #(= % "Wireless Technologies") :type))
(def led? (comp #(= % "LED") :type))
(def power? (comp #(= % "Power Solution") :type))


(deftask
  build
  "Base task, you probably want to use `build-prod` or `dev`."
  []
  (comp (global-metadata)
        (markdown)
        (render :renderer 'site.core/page
                :filterer (complement (some-fn product? home?)))
        (collection :renderer 'site.core/product-page
                    :filterer (every-pred product? active?)
                    :sortby :path
                    :page "active.html")
        (collection :renderer 'site.core/product-page
                    :filterer (every-pred product? passive?)
                    :sortby :path
                    :page "passive.html")
        (collection :renderer 'site.core/product-page
                    :filterer (every-pred product? electro?)
                    :sortby :path
                    :page "electromechanical.html")
        (collection :renderer 'site.core/product-page
                    :filterer (every-pred product? wireless?)
                    :sortby :path
                    :page "wireless.html")
        (collection :renderer 'site.core/product-page
                    :filterer (every-pred product? led?)
                    :sortby :path
                    :page "led.html")
        (collection :renderer 'site.core/product-page
                    :filterer (every-pred product? power?)
                    :sortby :path
                    :page "power.html")
        (collection :renderer 'site.core/product-page
                    :filterer product?
                    :sortby :path
                    :page "product.html")
        (collection :renderer 'site.core/home-page
                    :filterer (some-fn home? major?)
                    :sortby :path
                    :page "index.html")
        (sass)))

(deftask build-prod "Emit HTML files" [] (comp (build) (target)))

(deftask dev
         "Serve the site and watch the filesystem for changes."
         []
         (comp (watch) (build) (serve :resource-root "public")))

