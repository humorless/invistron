(ns site.common
  (:require [clojure.string :as string]
            [clojure.set :as s]))

(defn create-navigation
  "This creates the navigation bar."
  []
  [:nav.navbar.navbar-expand-lg.navbar-light.bg-white
   [:div.container
     [:a.navbar-brand {:href "/"}
      [:img {:src "/assets/images/invistron.png", :alt "invistron-logo"}]]
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
       [:li.nav-item.dropdown
         [:a.nav-link.dropdown-toggle
           {:id "productNavbar" :data-toggle "dropdown"
            :aria-haspopup true :aria-expanded false}
             "Product"]
         [:div.dropdown-menu
           {:aria-labelledby "productNavbar"}
             [:a.dropdown-item.text-dark {:href "/all-products.html"} [:b "All Products"]]
             [:a.dropdown-item.text-dark {:href "/linecard.html"} [:b "Line Cards"]]]]
       [:li.nav-item [:a.nav-link {:href "/news.html"} "News"]]
       [:li.nav-item [:a.nav-link {:href "/contact.html"} "Contact"]]]]]])

(defn find-matches
  "Take in lists a and b and return the elements that appear in both.
   src: https://repl.it/repls/VioletIdealisticAmurratsnake"
  [a b]
  (s/intersection (set a) (set b)))

(defn add-page-classname
  "If a page's slug name is in the list of page names that name
   is added as a class to the div.container. This is helpful for
   styling each page uniquely."
  [data]
  [:section.py-8.pt-md-11.border-bottom
   [:div.container
     {:class (-> data
                 :entry
                 :slug)}
     (-> data
         :entry
         :content)]])

(defn add-product-title
  "extract the title related content and add to page"
  [data]
  (let [title (-> data :entry :title)]
    [:h2.text-center.text-md-left.text-primary.mb-6.mb-lg-8
      title]))

(defn add-breadcrumb
  "extract the breadcrumb related content"
  [data]
  (let [component-type (-> data :entry :type)
        root (string/lower-case (first (string/split component-type #" ")))
        vendor (-> data :entry :vendor)
        title (-> data :entry :title)]
    [:nav {:aria-label "breadcrumb"}
      [:ol.breadcrumb.breadcrumb-scroll
        [:li.breadcrumb-item [:a {:href "/index.html"} "Home"]]
        [:li.breadcrumb-item [:a {:href "/all-products.html"} "Product"]]
        [:li.breadcrumb-item [:a {:href (str "/" root ".html")} component-type]]
        [:li.breadcrumb-item vendor]
        [:li.breadcrumb-item.active {:aria-current "page"} title]]]))

(defn make-row
  [x y]
  [:tr [:td [:strong x]] [:td y]])

(defn add-table
  "extract the table content"
  [data]
  (let [left (-> data :entry :left-cols)
        right (-> data :entry :right-cols)]
    [:div.col-12.col-md-7.col-lg-6.order-md-1
      [:table.table-sm.table-bordered.table-striped
        [:tbody
         (map make-row left right)]]]))

(defn add-image
  "extract the img filename"
  [data]
  (let [img-file (-> data :entry :img)]
    [:div.col-12.col-md-5.col-lg-6.order-md-2
      [:img.figure-img.img-fluid.rounded.lift.lift-lg
        {:src (str "/assets/images/product/" img-file)}]]))

(defn create-footer
  []
  [:footer.py-8.py-md-11.bg-dark
   [:div.container
     [:div.row
       [:div.col-lg-4.col-md-4.col-sm-12
         [:a.navbar-brand {:href "/"}
          [:img {:src "/assets/images/invistron_white.png", :alt "invistron-logo"}]]]
       [:div.col-lg-7.col-md-7.col-sm-12
         [:p.font-size-lg.text-white "Email: jessie@invistron.com.tw"]
         [:p.font-size-lg.text-white "Phone Number: +886-2-25559787"]
         [:p.font-size-lg.text-white "Fax Number: +886-2-25559618"]
         [:p.font-size-lg.text-white "Address: 4F-1 NO.45-1 CHANG-AN WEST ROAD,TAIPEI 103, TAIWAN"]
         [:p.font-size-lg.text-white "Office Hours: Monday-Friday 9:00-18:00"]
         [:p.font-size-lg.text-white
           [:a.font-size-lg {:href "https://www.facebook.com/471337183653509/"}
             "facebook link" ]]
      ]]
     [:div.footer-copyright.text-center.py-3
       [:p.text-muted
       "@ 2019 Copyright INVISTRON, Co. Ltd. All Rights Reserved"]]]])

(defn create-main-content
  "This creates the main content of each page."
  [data]
  [:div.main-content
   (create-navigation)
   (add-page-classname data)
   (create-footer)])

(defn create-per-product-main-content
  "This creates the main content of each per product page."
  [data]
  [:div.main-content
   (create-navigation)
   [:section.py-8.pt-md-11.border-bottom
     [:div.container
       (add-product-title data)
       [:div.row.align-items-center
         (add-image data)
         (add-table data)]]]
   (create-footer)])
