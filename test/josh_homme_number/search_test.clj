(ns josh-homme-number.search-test
  (:require [expectations :refer :all]
            [josh-homme-number.search :refer :all]))

(def mock-tail-node
  (->Node "Dane Johnson"
          '("The Band I Formed When I Kidnapped Dave Grohl" "Dave Grohl"
            "Them Crooked Vultures" "Josh Homme") '()))

(expect 2 (.depth mock-tail-node))

(expect delay? (delayed-future (+ 1 1)))
(expect future? (force (delayed-future (+ 1 1))))
(expect 2 (deref-delayed-future (delayed-future (+ 1 1))))

(expect (list "Dane Johnson" "Dave Grohl")
        (next-artist-tier {:links '({:links ("Dane Johnson")}
                                    {:links ("Dave Grohl")})}))
(expect (list (->Node "Dave Grohl" '() '()))
        (next-artist-tier
         (->Node "Josh Homme" '()
                 (list (->Node "TCV" '()
                           (list (->Node "Dave Grohl" '() '())))))))
