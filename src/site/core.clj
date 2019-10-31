(ns site.core
  (:require [hiccup.page :as hp]
            [site.common :as common]
            [site.home :as home]
            [site.table-of-contents :as toc]))

(defn create-head
  "This will render the <head>."
  [title]
  [:head [:title title] [:meta {:charset "utf-8"}]
   [:meta
    {:content "width=device-width, initial-scale=1, shrink-to-fit=no",
     :name "viewport"}]
   [:link
    {:rel "shortcut icon",
     :href "/assets/images/favicon.ico",
     :type "image/x-icon"}]
   [:link
    {:rel "stylesheet",
     :href
       "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"}]
   [:link
    {:rel "stylesheet",
     :href "https://fonts.googleapis.com/css?family=Oswald|Roboto"}]
   [:link
    {:rel "stylesheet",
     :href "/assets/css/theme.css"}]])

(defn home-page
  "Takes in a collection of pages and concatenates them, additionally add
   a table of contents and klipse."
  [data]
  (let [major-products (->> data
                        :entries
                        (filter #(:major %)))
        contents       (->> data
                        :entries
                        (filter #(:home %))
                        (map :content))
        data {:entry {:content contents}}]
      (hp/html5 {:lang "en"}
            (create-head "Invistron")
            [:body (home/create-home-content data)])))


(defn page
  [data]
  (hp/html5 {:lang "en"}
            (create-head "Invistron")
            [:body.bg-light (common/create-main-content data)]))


(defn paginate-page [{global-meta :meta posts :entries entry :entry}]
  (hp/html5 {:lang "en" :itemtype "http://schema.org/Blog"}
    [:head
      [:title (str (:site-title global-meta) "|" (:tag entry))]
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, user-scalable=no"}]]
    [:body.bg-light
     [:h1 (str "Page " (:page entry))]
     [:ul.items.columns.small-12
      (for [post posts]
        [:li (str (:title post) " " (:vendor post) "|" (:model post))])]]))


(defn load-bootstrap-js
  "This loads all js files needed for Bootstrap. Must be the last fn before closing <body>."
  []
  (list
    [:script
     {:src "https://code.jquery.com/jquery-3.1.1.slim.min.js",
      :integrity
        "sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n",
      :crossorigin "anonymous"}]
    [:script {:src "/scripts/script.js"}]
    [:script
     {:src
        "https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js",
      :integrity
        "sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb",
      :crossorigin "anonymous"}]
    [:script
     {:src
        "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js",
      :integrity
        "sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn",
      :crossorigin "anonymous"}]))

(defn doc-page
  "Takes in a collection of pages and concatenates them, additionally add
   a table of contents and klipse."
  [data]
  (let [contents (->> data
                      :entries
                      (map :content)
                      reverse
                      (clojure.string/join ""))
        data {:entry {:content contents}}]
    (hp/html5
      {:lang "en"}
      (create-head "clojurebridge-mn")
      [:script {:type "text/javascript"}
       "window.klipse_settings = {selector: '.language-c', selector_eval_js: '.language-j'};"]
      [:body.toc.docs (toc/create-toc-sidebar data)
       (common/create-main-content data) (load-bootstrap-js)]
      ;; The klipse plugin must be loaded last.
      [:script
       {:type "text/javascript",
        :src
          "https://storage.googleapis.com/app.klipse.tech/plugin/js/klipse_plugin.js"}])))
