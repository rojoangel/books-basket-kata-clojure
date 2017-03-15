(ns books-basket.core)

; encapsulates book instances
; equivalent to use {:price 10.00 :genre :fantasy}
(defrecord Book [price genre])

(defn- apply-discount [price discount]
  (* price (/ (- 100 discount) 100)))

; book->price as multimethod - dispatched on book genre
(defmulti ^:private book->price :genre)

(defmethod ^:private book->price :fantasy [book genre-qty]
  (apply-discount (:price book) 20))

(defmethod ^:private book->price :it [book genre-qty]
  (if (> genre-qty 2)
    (apply-discount (:price book) 30)
    (apply-discount (:price book) 10)))

(defmethod ^:private book->price :travel [book genre-qty]
  (if (> genre-qty 3)
    (apply-discount (:price book) 40)
    (:price book)))

(defmethod ^:private book->price :default [book genre-qty]
  (:price book))

; This is weird and it's why round is used before returning the price
; user=> (+ 20 9.99);
; 29.990000000000002
; round function found here: http://clojure-doc.org/articles/language/functions.html#how-to-define-functions
(defn- round
  [d precision]
  (let [factor (Math/pow 10 precision)]
    (/ (Math/floor (* d factor)) factor)))

(defn price [basket]
  (let [genre-freqs (frequencies (map :genre basket))]
    (round
      (->>  basket
            (map #(book->price % (get genre-freqs (:genre %))))
            (reduce +))
      2)))
