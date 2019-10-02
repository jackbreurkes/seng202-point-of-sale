Feature: User handles data using the app
  Data handling

  Scenario Outline: Cost of tofu burgers
    Given XML file from "src/test/resources/xml/TESTXML1.xml" is uploaded
    When user wants to know the total cost of <count> "TOFUBURG"
    Then the total cost is $<total>
    Examples:
      | count | total |
      | 1     |  50   |
      | 2     |  100  |


#  Scenario: User uploads data from file
#    Given the user has a data from directory "src/test/resources/xml/TESTXML1.xml" to upload
#    And the file is the right type and correctly formatted
#    When the user uploads the file
#    Then the system adds the new data to the current data
#    And prompts the user to decide what to keep for each duplicate entry
#
#  Scenario: User uploads data from file
#    Given the user has a data from directory "src/test/resources/xml/TESTXML2.xml" to upload
#    And the file is the right type and correctly formatted
#    When the user uploads the file
#    Then the system adds the new data to the current data
#    And prompts the user to decide what to keep for each duplicate entry
#
#  Scenario: User manually enters a new ingredient/food item/supplier data
#    Given the system is in data entry mode
#    When the user provides new or updated data in the correct field(s)
#    Then the system updates the data entry
#
#  Scenario: User adds a new food item
#    Given the system is in the food item entry state
#    When user adds a new food item
#    Then system adds new food item to database
#    And system displays the new food item
#
#  Scenario: User deletes existing recipe
#    Given menu is not empty
#    When user deletes recipe
#    Then system removes food item from database
#
#  Scenario: User modifies existing recipe
#    Given menu is not empty2
#    When user modifies recipe
#    Then system updates recipe in database
#
#  Scenario: User edits an ingredient’s data
#    Given an ingredient is selected
#    And the system is showing all available information about the ingredient
#    When the user changes a specific field
#    Then the system updates the value to match the user input
#
#  Scenario: Compute number of servings that current stocks will allow to be made
#    Given the current stock is recorded into the database
#    When user requests remaining serves
#    Then the application should compute how many servings can be made with the given amount of stock
#
#  Scenario: User updates the quantities of ingredients used in a recipe
#    Given a food item is in the database and is currently selected
#    When the user changes the quantity value
#    Then the recipe is updated to display the new quantity

#  Scenario: User adds an existing food item to the current menu
#    Given the selected food item(s) are not already in the menu
#    When the user adds a food item to the menu
#    Then the food item and its details should be displayed on the menu

#  Scenario: user needs the lists of data sorted based on their properties
#    Given the items exist in the database
#    And the item has appropriate search/ordering values
#    When the user specifies the attribute to sort by
#    Then the database returns a sorted list of the relevant items
#
#  Scenario: user needs to know when they are running low on ingredients
#    Given the ingredients are registered in the database
#    When the ingredients reach a certain low level
#    Then the user is informed that the ingredient is running low
#
#  Scenario: user wants to see how sales perform under certain conditions
#    Given the tags and time frame are specified
#    When sales are made during the time frame
#    Then the sales data is saved with the property specified
#
#  Scenario: The owner wants to see the sales data in an organised way
#    Given the sales data is recorded
#    When the application is asked to generate a sales report
#    Then the sales data is displayed with visualised sales figures
