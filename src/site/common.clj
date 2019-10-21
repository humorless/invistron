(ns site.common
  (:require [clojure.set :as s]))

(defn create-navigation
  "This creates the navigation bar."
  []
  [:nav.navbar.navbar-light.bg-light.navbar-toggleable-sm
   [:a.navbar-brand {:href "/"}
    [:img {:src "/assets/images/cbmn-logo.png", :alt "clojurebridge-mn-logo"}]]
   [:button.navbar-toggler.navbar-toggler-right
    {:type "button",
     :data-toggle "collapse",
     :data-target "#navbar-cbmn",
     :aria-controls "navbar-cbmn",
     :aria-expanded "false",
     :aria-label "Toggle Navigation"} [:span "Menu"]
    [:span.navbar-toggler-icon]]
   [:div#navbar-cbmn.collapse.navbar-collapse
    [:div.navbar-nav [:a.nav-item.nav-link {:href "/"} "Home"]
     [:a.nav-item.nav-link {:href "/about.html"} "About"]
     [:a.nav-item.nav-link {:href "/product.html"} "Product"]
     [:a.nav-item.nav-link {:href "/news.html"} "News"]
     [:a.nav-item.nav-link {:href "/contact.html"} "Contact"]]]])

(defn create-logo
  "This creates the main logo."
  []
  [:h1 [:a.cbmn-logo {:href "/"} "clojurebridge-mn"]])

(defn create-header
  "This creates the top header."
  []
  [:header (create-logo) (create-navigation)])

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
