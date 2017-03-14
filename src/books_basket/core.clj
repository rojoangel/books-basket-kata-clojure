(ns books-basket.core)

(defn apply-discount [price discount]
  (float (* price (/ (- 100 discount) 100))))

(defmulti book->price :genre)
(defmethod book->price :fantasy [book genre-qty]
  (apply-discount (:price book) 20))
(defmethod book->price :it [book genre-qty]
  (if (> genre-qty 2)
    (apply-discount (:price book) 30)
    (apply-discount (:price book) 10)))
(defmethod book->price :default [book genre-qty]
  (:price book))

; user=> (+ 20 9.99); That's weird and that's why add casts to float
; 29.990000000000002

(defn add [p1 p2]
  (float (+ p1 p2)))

(defn price [basket]
  (let [genre-freqs (frequencies (map :genre basket))]
    (reduce add 0.00 (map #(book->price % (get genre-freqs (:genre %))) basket))))
