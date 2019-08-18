Feature: User manages orders within the app
  Managing order

  Scenario: User registers an order
    Given there is a menu with food items available
    When the user adds food items to an order
    Then the order is added to the pending orders