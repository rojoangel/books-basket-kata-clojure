(ns books-basket.core-test
  (:use midje.sweet)
  (:use [books-basket.core]))

(facts "about calculating the price of a books basket"
       (fact "no discount is applied when no book in the basket is elegible"
             (fact "empty basket price is 0"
                   (let [basket []]
                     (price basket) => (float 0.00)))

             (fact "non empty basket price is the sum of prices"
                   (let [basket [(->Book 15.00 :other)]]
                     (price basket) => (float 15))

                   (let [basket [(->Book 20.00 :other)
                                 (->Book 9.99 :other)]]
                     (price basket) => (float 29.99))))

       (fact "fantasy books get a 20% discount"
             (let [basket [(->Book 10.00 :fantasy)]]
               (price basket) => (float 8.00))

             (let [basket [(->Book 99.99 :other)
                           (->Book 15.00 :fantasy)]]
               (price basket) => (float 111.99)))

       (fact "it books get a 30% discount when there are more than two, 10% otherwise"
             (let [basket [(->Book 10.00 :it)]]
               (price basket) => (float 9.00))

             (let [basket [(->Book 10.00 :it)
                           (->Book 10.00 :it)]]
               (price basket) => (float 18.00))

             (let [basket [(->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :it)]]
               (price basket) => (float 21.00))

             (let [basket [(->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :other)]]
               (price basket) => (float 31.00)))

       (fact "travel books get a 40% discount when there are more than three, no discount otherwise"
             (let [basket [(->Book 10.00 :travel)]]
               (price basket) => (float 10.00))

             (let [basket [(->Book 10.00 :travel)
                           (->Book 10.00 :travel)]]
               (price basket) => (float 20.00))

             (let [basket [(->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)]]
               (price basket) => (float 30.00))

             (let [basket [(->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)]]
               (price basket) => (float 24.00))

             (let [basket [
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :other)]]
               (price basket) => (float 34.00)))

       (fact "discounts can be combined"
             (let [basket [(->Book 10.00 :other)
                           (->Book 10.00 :fantasy)
                           (->Book 10.00 :it)
                           (->Book 10.00 :travel)]]
               (price basket) => (float 37.00))

             (let [basket [(->Book 10.00 :other)
                           (->Book 10.00 :fantasy)
                           (->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :it)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)
                           (->Book 10.00 :travel)]]
               (price basket) => (float 63.00))))