Feature: Kitchen creates items from storage or based on their recipes if none are in storage

  Scenario: Kitchen gets a pizza with a recipe containing a pizza base that is made of dough
    Given there is a "pizza" with a recipe containing a "pizza base" that is made of "dough"
    And there are no pizzas or pizza bases in storage, but there are 2 doughs in storage
    When the kitchen creates the pizza
    Then a dough is removed from the database