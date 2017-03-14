(ns books-basket.core-test
  (:use midje.sweet)
  (:use [books-basket.core]))

(facts "about calculating the price of a books basket"
       (fact "no discount is applied when no book in the basket is elegible"
             (fact "empty basket price is 0"
                   (let [basket []]
                     (price basket) => 0.00))

             (fact "non empty basket price is the sum of prices"
                   (let [basket [(->Book 15.00 :other)]]
                     (price basket) => 15.00)

                   (let [basket [(->Book 20.00 :other)
                                 (->Book 9.99 :other)]]
                     (price basket) => 29.99)))

       (fact "fantasy books get a 20% discount"
             (let [basket [(->Book 10.00 :fantasy)]]
               (price basket) => 8.00)

             (let [basket [(->Book 99.99 :other)
                           (->Book 15.00 :fantasy)]]
               (price basket) => 111.99))

       (fact "it books get a 30% discount when there are more than two, 10% otherwise"
             (let [basket [(->Book 10.00 :it)]]
               (price basket) => 9.00)

             (let [basket [(->Book 10.00 :it)
                           (->Book 10.00 :it)]]
               (price basket) => 18.00)

             (let [basket [(->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :it)]]
               (price basket) => 21.00)

             (let [basket [(->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :other)]]
               (price basket) => 31.00))

       (fact "travel books get a 40% discount when there are more than three, no discount otherwise"
             (let [basket [(->Book 10.00 :travel)]]
               (price basket) => 10.00)

             (let [basket [(->Book 10.00 :travel)
                           (->Book 10.00 :travel)]]
               (price basket) => 20.00)

             (let [basket [(->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)]]
               (price basket) => 30.00)

             (let [basket [(->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)]]
               (price basket) => 24.00)

             (let [basket [
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :other)]]
               (price basket) => 34.00))

       (fact "discounts can be combined"
             (let [basket [(->Book 10.00 :other)
                           (->Book 10.00 :fantasy)
                           (->Book 10.00 :it)
                           (->Book 10.00 :travel)]]
               (price basket) => 37.00)

             (let [basket [(->Book 10.00 :other)
                           (->Book 10.00 :fantasy)
                           (->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)]]
               (price basket) => 63.00)))