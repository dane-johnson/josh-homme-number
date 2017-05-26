(ns josh-homme-number.util-test
  (:require [expectations :refer :all]
            [josh-homme-number.util :refer :all]))

(expect "Arthur Dent was in The Resturant at the End of the Universe with Ford Prefect who was in Life, the Universe and Everything with Hotblack Desatio.\n"
        (with-out-str
          (print-path
           '("Arthur Dent" "The Resturant at the End of the Universe" "Ford Prefect" "Life, the Universe and Everything" "Hotblack Desatio"))))
