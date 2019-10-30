(ns site.common
  (:require [clojure.set :as s]))

(defn create-navigation
  "This creates the navigation bar."
  []
  [:nav.navbar.navbar-expand-lg.navbar-light.bg-white
   [:div.container
     [:a.navbar-brand {:href "/"}
      [:img {:src "/assets/images/invistron.jpg", :alt "invistron-logo"}]]
     [:button.navbar-toggler.navbar-toggler-right
      {:type "button",
       :data-toggle "collapse",
       :data-target "#navbar-ivtn",
       :aria-controls "navbar-ivtn",
       :aria-expanded "false",
       :aria-label "Toggle Navigation"} [:span "Menu"]
      [:span.navbar-toggler-icon]]
     [:div#navbar-ivtn.collapse.navbar-collapse
      [:ul.navbar-nav.ml-auto
       [:li.nav-item [:a.nav-link {:href "/"} "Home"]]
       [:li.nav-item [:a.nav-link {:href "/about.html"} "About"]]
       [:li.nav-item [:a.nav-link {:href "/product.html"} "Product"]]
       [:li.nav-item [:a.nav-link {:href "/news.html"} "News"]]
       [:li.nav-item [:a.nav-link {:href "/contact.html"} "Contact"]]]]]])

(defn create-logo
  "This creates the main logo."
  []
  [:h1 [:a.cbmn-logo {:href "/"} "clojurebridge-mn"]])

(defn create-header
  "This creates the top header."
  []
  [:header (create-navigation)])

(defn find-matches
  "Take in lists a and b and return the elements that appear in both.
   src: https://repl.it/repls/VioletIdealisticAmurratsnake"
  [a b]
  (s/intersection (set a) (set b)))

(defn add-page-classname
  "If a page's slug name is in the list of page names that name
   is added as a class to the div.container. This is helpful for
   styling each page uniquely."
  [data names]
  [:div.container
   {:class (-> data
               :entry
               :slug)}
   (-> data
       :entry
       :content)])

(defn create-main-content
  "This creates the main content of each page."
  [data]
  [:section.main-content (create-header)
   (add-page-classname data ["index" "events" "about"])])
