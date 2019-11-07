(ns site.home
  (:require [site.common :as common]))

(defn create-welcome
  "welcome row has Welcome words and image"
  []
  [:section.pt-4.pt-md-11
    [:div.container
      [:div.row.align-items-center
        [:div.col-12.col-md-5.col-lg-6.order-md-2
          ;; images
          [:img.img-fluid.mw-md-150.mw-lg-130.mb-6.mb-md-0
            {:src "/assets/images/electro-components.jpg", :alt "invistron-banner"}]]
        [:div.col-12.col-md-7.col-lg-6.order-md-1
          [:h1.display-3.text-center.text-md-left
            "Welcome to "
            [:span.text-primary
              "Invistron"]]
          [:p.lead.text-center.text-md-left.text-muted.mb-6.mb-lg-8
            "Service Integration On Electronic Components Supply Technology"]]]]])

(defn create-features
  "add three features"
  []
  [:section.py-8.py-md-11.border-bottom
    [:div.container
      [:div.row
        [:div.col-12.col-md-4
          [:div.icon.text-primary.mb-3
            [:h3 "BOM supplying"]
            [:p.text-muted.mb-6.mb-md-0
              "To manufacture a product, you need to make sure that the bill of material (BOM) is correct."]]]
        [:div.col-12.col-md-4
          [:div.icon.text-primary.mb-3
            [:h3 "Buffer stocking"]
            [:p.text-muted.mb-6.mb-md-0
              "To manufacture a product, you need to make sure that the bill of material (BOM) is correct."]]]
        [:div.col-12.col-md-4
          [:div.icon.text-primary.mb-3
            [:h3 "Technical support"]
            [:p.text-muted.mb-6.mb-md-0
              "To manufacture a product, you need to make sure that the bill of material (BOM) is correct."]]]]]])

(defn create-home-content
  "This create the home page content"
  [data]
  [:div.main-content
   (common/create-navigation)
   (create-welcome)
   (create-features)
   (common/create-footer)])
