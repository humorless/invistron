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
          [:h2.text-center.text-md-left.text-muted.mb-6.mb-lg-8
            "Service Integration On Electronic Components Supply Technology"]]]]])

(defn create-carousel
  "add carousel slides"
  []
  [:section.pt-4.pt-md-11
   [:div.container
    [:div {:id "carouselExampleControls", :class "carousel slide", :data-ride "carousel"}
     [:div {:class "carousel-inner"}
      [:div {:class "carousel-item active"}
       [:img {:class "d-block w-100", :src "/assets/images/carousel/MF.png" , :alt "First slide"}]]
      [:div {:class "carousel-item"}
       [:img {:class "d-block w-100", :src "/assets/images/carousel/MF2.png", :alt "Second slide"}]]
      [:div {:class "carousel-item"}
       [:img {:class "d-block w-100", :src "/assets/images/carousel/MF3.png", :alt "Third slide"}]]
      [:div {:class "carousel-item"}
       [:img {:class "d-block w-100", :src "/assets/images/carousel/MF4.png", :alt "Third slide"}]]]
     [:a {:class "carousel-control-prev", :href "#carouselExampleControls", :role "button", :data-slide "prev"}
      [:span {:class "carousel-control-prev-icon", :aria-hidden "true"}]
      [:span {:class "sr-only"} "Previous"]]
     [:a {:class "carousel-control-next", :href "#carouselExampleControls", :role "button", :data-slide "next"}
      [:span {:class "carousel-control-next-icon", :aria-hidden "true"}]
      [:span {:class "sr-only"} "Next"]]]]])

(defn create-features
  "add three features"
  []
  [:section.py-8.py-md-11.border-bottom
    [:div.container
      [:div.row
        [:div.col-12.col-md-4
          [:div.icon.text-primary.mb-3
            [:h3 "BOM Offer Expert"]
            [:p.text-muted.mb-6.mb-md-0
            "Invistron provides a complete BOM solution (Bill of Materials) service to satisfy customers dynamic kitting components requirement professionally and rapidly."]
            [:br]
            [:p.text-muted.mb-6.mb-md-0
            "With our strong global sourcing team, we connect with  more than 3000 active worldwide venders, including manufactures, franchised distributors and long term reliable venders. We can review your BOM the accuracy of parts numbers and the material’s availability. By means of our supplying chain, we can access to hard-to-find or EOL (end of life) parts of stock availability or recommend by a replacement parts, to extend user’s products life."]]]
        [:div.col-12.col-md-4
          [:div.icon.text-primary.mb-3
            [:h3 "Buffer Stocking"]
            [:p.text-muted.mb-6.mb-md-0
            "To support our valuable long-term customers, we keep buffer stock or back order to shorten customer waiting time."]
            [:br]
            [:p.text-muted.mb-6.mb-md-0 "We provide a stable and a competitive supply to avoid components long lead-time or life-span dramatic situation, while the situations happen often."]]]
        [:div.col-12.col-md-4
          [:div.icon.text-primary.mb-3
            [:h3 "Technical Support"]
             [:ul
              [:li [:p.text-muted
                "Factory’s FAE report (failure analysis engineering report): once components failed, this can help user find solution."]]
              [:li [:p.text-muted
              "Replacement or equivalent parts recommendation to EOL (end of life) parts: to extend customer’s products life."]]
              [:li [:p.text-muted
              "Technical instruction for parts applying on PCB: whenever user meets an application problem."]]
              [:li [:p.text-muted
              "Special specification customized development: especially for passive and mechanical parts project, like crystal, converter, hard disc rack, antenna, LED and inductor, etc."]]]]]]]])

(defn create-home-content
  "This create the home page content"
  [data]
  [:div.main-content
   (common/create-navigation)
   (create-welcome)
   (create-carousel)
   (create-features)
   (common/create-footer)])
