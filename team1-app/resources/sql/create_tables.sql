/*
FoodItem (Code, Name, Cost, UnitType, StockLevel, IsVegetarian, IsVegan, IsGlutenFree, CalPerUnit) primkey Code
TODO Menu()
TODO Order()
TODO Supplier()
Recipe(Product, AmountCreated) primkey Product (foreign key of FoodItem)

Sells(Supplier, FoodItem, Price) primkey (Supplier, FoodItem) (foreign keys)
RecipeContains(Recipe, FoodItem, Amount) primkey (Recipe, FoodItem) (foreign keys)
RecipeOptionallyContains(Recipe, FoodItem, Amount) primkey (Recipe, FoodItem) (foreign keys)
TODO OrderContains(Order, FoodItem)
TODO MenuContains(Menu, FoodItem)
 */

CREATE TABLE FoodItem
(Id /* primary key ID. added because varchar prim key isn't good? */ INT NOT NULL PRIMARY KEY,
 Code /* alphanumberic code of the FoodItem */ VARCHAR(10) UNIQUE NOT NULL
    CONSTRAINT code_min_size CHECK (LENGTH(Code) >= 3),
 Name /* the FoodItem's name */ VARCHAR(20) NOT NULL
    CONSTRAINT name_min_size CHECK (LENGTH(Name) >= 3),
 UnitType /* unit type (count, ml or gram) */ CHAR(1)
    CONSTRAINT check_unit CHECK (UnitType in ('c', 'm', 'g')),
Cost /* cost of the FoodItem for customers */ VARCHAR(MAX) NOT NULL DEFAULT '0'
    /* TODO check that Cost is a numeric string */,
 StockLevel /* the current amount of this item stored */ INTEGER NOT NULL DEFAULT 0,
 IsVegetarian /* whether the item is vegetarian */ BIT NOT NULL DEFAULT 0,
 IsVegan /* whether the item is vegan */ BIT NOT NULL DEFAULT 0,
 IsGlutenFree /* whether the item is gluten free */ BIT NOT NULL DEFAULT 0,
 CalPerUnit /* number of calories per single unit */ VARCHAR(MAX)
    /* TODO check that CalPerUnit is a numeric string */);

CREATE TABLE Recipe
(Id /* unique identifier for a Recipe */ INT NOT NULL,
 Product /* the Id of the FoodItem created by the Recipe */ INT NOT NULL REFERENCES FoodItem,
 AmountCreated /* the amount of the Product created by the Recipe */ INT NOT NULL,
 PRIMARY KEY (Id, Product));

CREATE TABLE RecipeContains
(Recipe /* Id of the Recipe */ INT NOT NULL REFERENCES Recipe,
 FoodItem /* FoodItem contained in the recipe */ INT NOT NULL REFERENCES FoodItem,
 Amount /* the amount of the FoodItem in the Recipe */ INT NOT NULL,
 PRIMARY KEY (Recipe, FoodItem));