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
     :href "/assets/css/theme.css"}]
   [:script
     {:src "https://code.jquery.com/jquery-3.3.1.slim.min.js",
      :integrity "sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
      :crossorigin "anonymous"}]
   [:script
     {:src "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js",
      :integrity "sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49",
      :crossorigin  "anonymous"}]
   [:script
     {:src "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js",
      :integrity "sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy",
      :crossorigin  "anonymous"}]])

(defn create-product-head
  "Render the head for product page"
  [title]
  (conj (create-head title)
    [:link
      {:rel "stylesheet"
       :href "https://cdn.datatables.net/v/dt/dt-1.10.20/datatables.min.css"}]
    [:script
      {:type "text/javascript"
       :src "https://cdn.datatables.net/v/dt/dt-1.10.20/datatables.min.js"}]))

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


(defn product-page [{global-meta :meta entries :entries}]
  (hp/html5 {:lang "en" :itemtype "http://schema.org/Blog"}
    (create-product-head "Invistron")
    [:body.bg-light
     (common/create-navigation)
     [:section.py-8.pt-md-11.border-bottom
      [:div.container
       [:table#product-table.display
        [:thead
         [:tr
          [:th "Type"] [:th "Vendor"] [:th "Name"] [:th "Model No"]]]
        [:tbody
         (for [entry entries]
            [:tr
              [:td (:type entry)] [:td (:vendor entry)] [:td (:title entry) ] [:td (:model entry)]])]]]]
     (common/create-footer)
     [:script {:type "text/javascript"}
       "$(document).ready( function () {
         $('#product-table').DataTable( {responsive: true} );
       } );"]]))


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
