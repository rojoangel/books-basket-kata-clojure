# Books Basket Kata

Books can be added to the basket

Calculate the total price of the books in the basket with no discounts

Calculate the total price of the books in the basket applying the due discounts for each of them:
    
- Give no discount when a book is not eligible for a discount.

- Give 20% discount for each Fantasy book.

- Give 30% discount for each IT book when there are more than two of them and 10% otherwise.

- Give 40% discount for Travel books when there are more than three of them and no discount otherwise.
    
Based on https://github.com/ericlemerdy/one-kata-per-day/tree/master/SOLID

## How to run the tests

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.
