(ns site.core
  (:require [hiccup.page :as hp]
            [site.common :as common]
            [site.home :as home]))

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
       :href "https://cdn.datatables.net/v/dt/dt-1.10.20/b-1.6.1/r-2.2.3/rg-1.1.1/datatables.min.css"}]
    [:script
      {:type "text/javascript"
       :src "https://cdn.datatables.net/v/dt/dt-1.10.20/b-1.6.1/r-2.2.3/rg-1.1.1/datatables.min.js"}]))

(defn home-page
  "the home page renderer"
  [data]
  (let [contents (->> data
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

(defn per-product-page
  "the per-product page renderer"
  [data]
  (hp/html5 {:lang "en"}
            (create-head "Invistron")
            [:body.bg-light (common/create-per-product-main-content data)]))

(defn add-category-selector
 ""
 []
 [:script {:type "text/javascript"}
   "var typeSel = null;

    var draw = function (typeSel) {
      $('a.linecard').hide();
      $('a.linecard').filter(typeSel).show();
    }

    $(document).ready( function () {
      $('button.ItemType').on('click', function () {
        var name = $(this).data('name');
        typeSel = name;
        console.log('Debugging info - typeSel: ', typeSel);
        draw('.' + typeSel);
      });
    });"])

(defn add-jquery-enabler
 ""
 []
 [:script {:type "text/javascript"}
       "var typeSel = sessionStorage.getItem('typeSel');
        var vendorSel = sessionStorage.getItem('vendorSel');
        var rowMatch = function(type, vendor) {
          if (typeSel && !vendorSel) {
            if(type === typeSel) {
              return true;
            } else {
              return false;
            }
          }
          if (typeSel && vendorSel) {
            if(type === typeSel && vendor === vendorSel) {
              return true;
            } else {
              return false;
            }
          }
          return true;
        }

        $.fn.dataTable.ext.search.push(
          function( settings, data, dataIndex) {
            if (rowMatch(data[0], data[1])) {
              return true;
            }
            return false;
          }
        );

        $(document).ready( function () {

         var table = $('#product-table').DataTable({
           responsive: true,
           order: [[1, 'asc']],
           columnDefs: [{
             targets: [0, 1],
             visible: false
           }]
         });

         $('button.ItemType').on('click', function () {
           var name = $(this).data('name');
           typeSel = name;
           vendorSel = null;
           sessionStorage.setItem('typeSel', name);
           sessionStorage.removeItem('vendorSel');
           console.log('Debugging info - typeSel: ', typeSel);
           console.log('Debugging info - vendorSel: ', vendorSel);
           table.draw(false);
         });

         $('button.ItemVendor').on('click', function () {
           var name = $(this).data('name');
           vendorSel = name;
           sessionStorage.setItem('vendorSel', name);
           console.log('Debugging info - typeSel: ', typeSel);
           console.log('Debugging info - vendorSel: ', vendorSel);
           table.draw(false);
         });

        });"])

(def s-active "Active Parts")
(def s-passive "Passive Components")
(def s-electro "Electromechanical")
(def s-wireless "Wireless Technologies")
(def s-led "LED")
(def s-power "Power Solution")

;; (-> data :entry :active)
;; (-> data :entry :passive)
(defn make-entries
  "change yaml data to entry"
  [data type-keyword type-name]
  (let [linecards (-> data :entry type-keyword)]
    (for [line linecards]
      (let [href (:href line)
            img-name (:img line)
            v (clojure.string/split img-name #"\.")
            dir (name type-keyword)]
        {:type type-name :vendor (first v) :img (str dir "/" img-name) :href href}))))

(defn data->entries
  "transform data to the entries data form"
  [data]
  (let [active-xs   (make-entries data :active s-active)
        passive-xs  (make-entries data :passive s-passive)
        electro-xs  (make-entries data :electro s-electro)
        wireless-xs (make-entries data :wireless s-wireless)
        led-xs      (make-entries data :led s-led)
        power-xs    (make-entries data :power s-power)
        entries (concat active-xs passive-xs electro-xs wireless-xs led-xs power-xs)]
    entries))

(def active? (comp #(= % s-active) :type))
(def passive? (comp #(= % s-passive) :type))
(def electro? (comp #(= % s-electro) :type))
(def wireless? (comp #(= % s-wireless) :type))
(def led? (comp #(= % s-led) :type))
(def power? (comp #(= % s-power) :type))

(defn ->one-word [sentence]
  (->> (clojure.string/split sentence #" ")
       (filter #(not (clojure.string/blank? %)))
       (clojure.string/join "_")))

(defn create-linecard-category-option [category-name]
  [:li
    [:button.btn-success.btn-sm.ItemType
      {:data-name (->one-word category-name)}
       category-name]])

(defn linecard-page
  "the linecard page renderer"
  [data]
  (let [entries (data->entries data)]
    (hp/html5 {:lang "en"}
      (create-product-head "Invistron")
      [:body.bg-light
       (common/create-navigation)
       [:section.py-8.pt-md-11.border-bottom
        [:div.container
         [:div.row
           [:div.col-md-4.col-12
             [:h2 "Category"]
               [:ul
                 (create-linecard-category-option s-active)
                 (create-linecard-category-option s-passive)
                 (create-linecard-category-option s-electro)
                 (create-linecard-category-option s-wireless)
                 (create-linecard-category-option s-led)
                 (create-linecard-category-option s-power)]]
           [:div.col-md-8.col-12
             (for [entry entries]
               (let [category (->one-word (:type entry))]
                 [:a.linecard.mx-2 {:href (:href entry) :class category}
                   [:img.py-2 {:width "20%" :src (str "/assets/images/linecards/" (:img entry))}]]))]
           ]]]
       (common/create-footer)
       (add-category-selector)])))

(defn create-category-option [css-id category-name selector entries]
  [:li.dropright
    [:button.btn-success.btn-sm.ItemType
      {:id css-id :data-toggle "dropdown"
       :aria-haspopup true :aria-expanded false
       :data-name category-name}
       category-name]
    [:div.dropdown-menu
      {:aria-labelledby css-id}
      (let [vendors (distinct (map :vendor (filter selector entries)))]
        (for [v vendors]
          [:a.dropdown-item [:button.btn-sm.ItemVendor {:data-name v} v]]))]])

(defn product-page [{global-meta :meta entries :entries}]
  (hp/html5 {:lang "en" :itemtype "http://schema.org/Blog"}
    (create-product-head "Invistron")
    [:body.bg-light
     (common/create-navigation)
     [:section.py-8.pt-md-11.border-bottom
      [:div.container
       [:div.row
         [:div.col-md-5.col-12
           [:h2 "Category"]
             [:ul
               (create-category-option "activeOpt" s-active active? entries)
               (create-category-option "passiveOpt" s-passive passive? entries)
               (create-category-option "electOpt" s-electro electro? entries)
               (create-category-option "wirelessOpt" s-wireless wireless? entries)
               (create-category-option "ledOpt" s-led led? entries)
               (create-category-option "powerOpt" s-power power? entries)]]
         [:div.col-md-7.col-12
           [:table#product-table.display
            [:thead
             [:tr
              [:th "Category"] [:th "Manufacturer"] [:th "Parts No"] [:th "Description"]]]
            [:tbody
             (for [entry entries]
                [:tr
                  [:td (:type entry)]
                  [:td (:vendor entry)]
                  [:td [:a {:href (str "product/" (:slug entry) ".html")} (:title entry)]]
                  [:td (:model entry)]])]]]]]]
     (common/create-footer)
     (add-jquery-enabler)]))


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

