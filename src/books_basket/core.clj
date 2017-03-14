(ns books-basket.core)

(defn apply-discount [price discount]
  (* price (/ (- 100 discount) 100)))

(defrecord Book [price genre])

; book->price as multimethod - dispatched on book genre
(defmulti book->price :genre)

(defmethod book->price :fantasy [book genre-qty]
  (apply-discount (:price book) 20))

(defmethod book->price :it [book genre-qty]
  (if (> genre-qty 2)
    (apply-discount (:price book) 30)
    (apply-discount (:price book) 10)))

(defmethod book->price :travel [book genre-qty]
  (if (> genre-qty 3)
    (apply-discount (:price book) 40)
    (:price book)))

(defmethod book->price :default [book genre-qty]
  (:price book))

; This is weird and it's why round is used before returning the price
; user=> (+ 20 9.99);
; 29.990000000000002

(defn round
  [d precision]
  (let [factor (Math/pow 10 precision)]
    (/ (Math/floor (* d factor)) factor)))

(defn price [basket]
  (let [genre-freqs (frequencies (map :genre basket))]
    (round (reduce + (map #(book->price % (get genre-freqs (:genre %))) basket)) 2)))
