(ns books-basket.core)

(defn apply-discount [price discount]
  (float (* price (/ (- 100 discount) 100))))

(defmulti book->price :genre)
(defmethod book->price :fantasy [book]
  (apply-discount (:price book) 20))
(defmethod book->price :default [book]
  (:price book))

; user=> (+ 20 9.99); That's weird and that's why add casts to float
; 29.990000000000002

(defn add [p1 p2]
  (float (+ p1 p2)))

(defn price [basket]
  (reduce add 0.00 (map book->price basket)))
