Feature: User manages orders within the app
  Managing order

  Scenario: User registers an order
    #UC4
    Given the user has an order to register
    When the user adds "Wonton Noodles" to an order
    Then the order is submitted

  Scenario: User removes an item from the order
    #NEW
    Given the order has an item
    When the user deletes an item
    Then the item is no longer in the order


  Scenario: Employee removes an ingredient from a food item for a customer
    #UC4
    Given an order with at least one food item exists
    When the employee removes an ingredient from the food item
    Then the item in the order will no longer contain that ingredient


  Scenario: User adds ingredients to a food item’s recipe
    #UC4
    Given an order has at least one food item
    When the user selects the ingredients to add to the recipe
    Then the recipe is updated to include those ingredients

  Scenario: user cancels an order before it is confirmed
    #UC4
    Given an order is being created
    And the order has not been confirmed
    When the user cancels the order
    Then the order is removed from the list of orders

 Scenario: User refunds an order that has been completed
   #UC4
   Given the order has been created
   And the order has been submitted
   And the order has been completed
   When the customer asks for a refund, the order is refunded

  Scenario: user cancels an in-progress order
  #UC10
    Given an order exists in the orders list
    And the order has been confirmed
    And the order has not been completed
    When the user cancels the order
    Then the order is cancelled

