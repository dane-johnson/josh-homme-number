(ns josh-homme-number.core-test
  (:require [expectations :refer :all]
            [josh-homme-number.core :refer :all]))

(expect "Arthur Dent was in The Resturant at the End of the Universe with Ford Prefect who was in Life, the Universe and Everything with Hotblack Desatio.\n"
        (with-out-str
          (print-path
           '("Arthur Dent" "The Resturant at the End of the Universe" "Ford Prefect" "Life, the Universe and Everything" "Hotblack Desatio"))))
