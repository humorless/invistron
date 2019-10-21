(ns site.table-of-contents
  (:require [net.cgrand.enlive-html :as html]))

(defn string->stream
  ([s] (string->stream s "UTF-8"))
  ([s encoding]
   (-> (or s "")
       (.getBytes encoding)
       (java.io.ByteArrayInputStream.))))

(defn build-toc
  "Build a table of contents from headings. Return a map with the following keys
   :tag
   :title
   :id
  "
  [content]
  (map
    (fn [data]
      {:tag (:tag data),
       :title (-> data
                  :content
                  second),
       :id (-> data
               :content first
               :attrs :href)})
    (flatten
      (let [parsed (html/html-resource (string->stream content))]
        (let [selected #{[:html :body :h1] [:html :body :h2] [:html :body :h3]
                         [:html :body :h4] [:html :body :h5] [:html :body :h6]}]
          (html/select parsed selected))))))

(defn create-toc-sidebar
  "This creates the sidebar that will contain the table of contents."
  [data]
  [:div.toc-sidebar
   [:div.toc-hidden
    [:button#toc-show {:type "button", :title "Show table of contents sidebar."}
     [:span.sr-only "Open table of contents sidebar."] [:i.fa.fa-angle-down]]]
   [:section.toc-visible
    [:div.toc-header
     [:button#toc-hide
      {:type "button", :title "Hide table of contents sidebar."}
      [:span "Table of Contents"]
      [:span.sr-only "Hide table of contents sidebar."] [:i.fa.fa-angle-up]]]
    (let [toc (build-toc (-> data
                             :entry
                             :content))
          ;; We only want to show h1, h2, and h3 or the toc will be too busy.
          restricted-toc (filter (comp #{:h1 :h2 :h3} :tag) toc)]
      (doall (for [{:keys [tag title id]} restricted-toc]
               [:div.toc-link
                (case tag
                  :h1 [:a.toc.first {:href id} title]
                  :h2 [:a.toc.second {:href id} title]
                  :h3 [:a.toc.third {:href id} title])])))]])
