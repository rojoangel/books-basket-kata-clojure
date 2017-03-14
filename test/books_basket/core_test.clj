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
                     (price basket) => (float 8.00))
                   (let [basket [{:price 99.99 :genre :other}
                                 {:price 15.00 :genre :fantasy}]]
                     (price basket) => (float 111.99)))
             (fact "it books get a 30% discount when there are more than two, 10% otherwise"
                   (let [basket [{:price 10.00 :genre :it}]]
                     (price basket) => (float 9.00))
                   (let [basket [{:price 10.00 :genre :it}
                                 {:price 10.00 :genre :it}]]
                     (price basket) => (float 18.00))
                   (let [basket [{:price 10.00 :genre :it}
                                 {:price 10.00 :genre :it}
                                 {:price 10.00 :genre :it}]]
                     (price basket) => (float 21.00))
                   (let [basket [{:price 10.00 :genre :it}
                                 {:price 10.00 :genre :it}
                                 {:price 10.00 :genre :it}
                                 {:price 10.00 :genre :other}]]
                     (price basket) => (float 31.00)))
             (fact "travel books get a 40% discount when there are more than three, no discount otherwise"
                   (let [basket [{:price 10.00 :genre :travel}]]
                     (price basket) => (float 10.00))
                   (let [basket [{:price 10.00 :genre :travel}
                                 {:price 10.00 :genre :travel}]]
                     (price basket) => (float 20.00))
                   (let [basket [{:price 10.00 :genre :travel}
                                 {:price 10.00 :genre :travel}
                                 {:price 10.00 :genre :travel}]]
                     (price basket) => (float 30.00))
                   (let [basket [{:price 10.00 :genre :travel}
                                 {:price 10.00 :genre :travel}
                                 {:price 10.00 :genre :travel}
                                 {:price 10.00 :genre :travel}]]
                     (price basket) => (float 24.00)))))