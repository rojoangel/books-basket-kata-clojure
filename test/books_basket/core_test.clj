(ns books-basket.core-test
  (:use midje.sweet)
  (:use [books-basket.core]))

(facts "about calculating the price of a books basket"
       (fact "no discount is applied when no book in the basket is elegible"
             (fact "empty basket price is 0"
                   (let [basket []]
                     (price basket) => 0))))
