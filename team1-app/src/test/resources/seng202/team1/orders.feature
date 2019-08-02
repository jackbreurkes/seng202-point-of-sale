Feature: User manages orders within the app
  Managing order

  Scenario: User registers an order
    Given there are menu items available to order
    When the user selects items to order
    Then the order is added to the pending orders