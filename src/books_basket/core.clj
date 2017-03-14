(ns books-basket.core)

(defn apply-discount [price discount]
  (float (* price (/ (- 100 discount) 100))))

(defn book->price [book]
  (if (= :fantasy (:genre book))
    (apply-discount (:price book) 20)
    (:price book)))

; user=> (+ 20 9.99); That's weird and that's why add casts to float
; 29.990000000000002

(defn add [p1 p2]
  (float (+ p1 p2)))

(defn price [basket]
  (reduce add 0.00 (map book->price basket)))
