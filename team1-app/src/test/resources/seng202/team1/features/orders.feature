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
    Given a food item is selected
    When the user selects the ingredients to add to the recipe
    Then the recipe is updated to include those ingredients

  Scenario: user cancels an order before it is confirmed
    Given an order is being created
    And the order has not been confirmed
    When the user cancels the order
    Then the order is removed from the list of orders

  Scenario: user cancels an in-progress order
    Given an order exists in the orders list
    And the order has been confirmed
    And the order has not been completed
    When the user cancels the order
    Then the system removes the order from the order list
    And the user is prompted to select which items can be returned to stock
    And the user is prompted to enter a refund amount
    And the system logs the cancellation of the order

  Scenario: user selects an item in a cancelled order to return to stock
    Given an order is being cancelled
    When the user selects a food item to return to stock
    Then the system logs the cancellation of the order2
    And the system updates the profit based on the cancellation reason
    And the system updates the ingredient stocks based on the cancellation reason
