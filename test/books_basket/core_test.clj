(ns books-basket.core-test
  (:use midje.sweet)
  (:use [books-basket.core]))

(facts "about calculating the price of a books basket"
       (fact "no discount is applied when no book in the basket is elegible"
             (fact "empty basket price is 0"
                   (let [basket []]
                     (price basket) => (float 0.00)))
             (fact "non empty basket price is the sum of prices"
                   (let [basket [{:price 15.00}]]
                     (price basket) => (float 15))
                   (let [basket [{:price 20.00} {:price 9.99}]]
                     (price basket) => (float 29.99)))
             (fact "fantasy books get a 20% discount"
                   (let [basket [{:price 10.00 :genre :fantasy}]]
                     (price basket) => (float 8.00)))))