Feature: User manages orders within the app
  Managing order

  Scenario: User registers an order
    Given the user has an order to register
    When the user adds "Wonton Noodles" to an order
    Then the order is submitted

  Scenario: User removes an item from the order
    Given the order has an item
    When the user deletes an item
    Then the item is no longer in the order

  Scenario: Employee removes an ingredient from a food item for a customer
    Given an order with at least one food item exists
    When the employee removes an ingredient from the food item
    Then the item in the order will no longer contain that ingredient

  Scenario: User adds ingredients to a food item’s recipe
    Given an order has at least one food item
    When the user selects the ingredients to add to the recipe
    Then the recipe is updated to include those ingredients

  Scenario: User cancels an order before it is confirmed
    Given an order is being created
    And the order has not been confirmed
    When the user cancels the order
    Then the order is removed from the list of orders
