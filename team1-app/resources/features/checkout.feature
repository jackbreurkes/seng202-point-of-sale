Feature: Checkout
  Scenario: Checkout a banana
    Given the price of a "banana" is $0.4
    When I checkout 1 "banana"
    Then the total price should be $0.4