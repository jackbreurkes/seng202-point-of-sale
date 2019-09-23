Feature: User manages orders within the app
  Managing order

  Scenario: User registers an order
    Given the user has an order to register
    When the user adds "Wonton Noodles" to an order
    Then the order is submitted