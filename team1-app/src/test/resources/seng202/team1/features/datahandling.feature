Feature: User handles data using the app
  Data handling

  Scenario: Valid XML file
    Given the user has data from directory "src/test/resources/xml/TESTXML1.xml" to upload
    And data is within a valid directory
    When "TESTXML1.xml" is parsed and uploaded
    Then upload of "TESTXML1.xml" is a success

  Scenario Outline: Cost of tofu burgers
    Given XML file from "src/test/resources/xml/TESTXML1.xml" is uploaded
    When user wants to know the total cost of <count> "TOFUBURG"
    Then the total cost is $<total>
    Examples:
      | count |  total   |
      | 1     |  50.0    |
      | 2     |  100.0   |

  Scenario: Updating cost of tofu burger
    Given XML file from "src/test/resources/xml/TESTXML1.xml" is uploaded
    When user wants to update cost of a single "TOFUBURG" to $18.50
    Then cost is successfully updated to $18.50

  Scenario: Updating cost of hamburger
    Given XML file from "src/test/resources/xml/TESTXML1.xml" is uploaded
    When user wants to update cost of a single "BEEFBURG" to $20.50
    Then cost is successfully updated to $20.50

  Scenario Outline: Discounting cost of tofu burger
    Given XML file from "src/test/resources/xml/TESTXML1.xml" is uploaded
    When user wants to discount cost of a single "TOFUBURG" by <percentage>%
    Then cost should be $<total>
    Examples:
      | percentage |  total   |
      | 10         |  45.0    |
      | 25         |  37.5    |
      | 50         |  25.0    |

  Scenario: Adding a food item Paella into existing database storage
    Given XML file from "src/test/resources/xml/TESTXML1.xml" is uploaded
    When user creates a new dish "Paella" with the code "PAELLA" and the unit "c"
    And sets the food item's price to $20.0
    And adds food item to database
    Then the food item "PAELLA" should be successfully added to the database storage

  Scenario: Deleting a cheeseburger from database storage
    Given XML file from "src/test/resources/xml/TESTXML1.xml" is uploaded
    When user deletes food item with the code "CHEESEBURG"
    Then "CHEESEBURG" should be successfully removed from the database storage

  # Feature not present in current app
  Scenario: Modifying recipe of food item from database storage
    Given XML file from "src/test/resources/xml/TESTXML1WITHRECIPE.xml" is uploaded
    And uploaded file contains a recipe for "BEEFBURG"
    When user manually adds 1 ingredient "Cucumber" with the code "CUCUMBER" and the unit "c"
    Then a cucumber should be successfully added as an ingredient to a "BEEFBURG"
    And a "CUCUMBER" should be added to the database storage

  Scenario: User deletes ingredient as an ingredient from existing hamburger recipe in the database
    Given XML file from "src/test/resources/xml/TESTXML1WITHRECIPE.xml" is uploaded
    And uploaded file contains a recipe for "BEEFBURG" with ingredient "LETTUCE"
    When user removes "LETTUCE" as an ingredient of a hamburger in the database storage
    Then a lettuce should be successfully removed as an ingredient of "BEEFBURG" in the storage

  Scenario: User updates the quantities of beef used in a recipe for a hamburger
    Given XML file from "src/test/resources/xml/TESTXML1WITHRECIPE.xml" is uploaded
    And uploaded file contains a recipe for "BEEFBURG" with ingredient "BEEF"
    When another beef is added as an ingredient of a hamburger
    Then the recipe should have 2 beef as ingredients of a hamburger