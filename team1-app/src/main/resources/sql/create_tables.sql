CREATE TABLE IF NOT EXISTS FoodItem
(Id /* primary key ID */ INTEGER PRIMARY KEY,
 Code /* alphanumberic code of the FoodItem */ VARCHAR(10) UNIQUE NOT NULL
    CONSTRAINT code_min_size CHECK (LENGTH(Code) >= 3),
 Name /* the FoodItem's name */ VARCHAR(20) NOT NULL
    CONSTRAINT name_min_size CHECK (LENGTH(Name) >= 3),
 UnitType /* unit type (count, ml or gram) */ CHAR(1) NOT NULL
    CONSTRAINT check_unit CHECK (UnitType in ('c', 'm', 'g')),
 Cost /* cost of the FoodItem for customers */ VARCHAR(8000) NOT NULL DEFAULT '0'
    /* TODO check that Cost is correct format */,
 StockLevel /* the current amount of this item stored */ INTEGER NOT NULL DEFAULT 0,
 IsVegetarian /* whether the item is vegetarian */ BIT NOT NULL DEFAULT 0,
 IsVegan /* whether the item is vegan */ BIT NOT NULL DEFAULT 0,
 IsGlutenFree /* whether the item is gluten free */ BIT NOT NULL DEFAULT 0,
 CalPerUnit /* number of calories per single unit */ VARCHAR(8000)
    /* TODO check that CalPerUnit is a numeric string */);


CREATE TABLE IF NOT EXISTS Recipe
(Id /* unique identifier for a Recipe */ INTEGER PRIMARY KEY,
 Product /* the Id of the FoodItem created by the Recipe */ INTEGER UNIQUE NOT NULL REFERENCES FoodItem,
 AmountCreated /* the amount of the Product created by the Recipe */ INT NOT NULL);


CREATE TABLE IF NOT EXISTS RecipeContains
(Recipe /* Id of the Recipe */ INT NOT NULL REFERENCES Recipe(Id), /* TODO change to Recipe(Id)? */
 FoodItem /* FoodItem contained in the recipe */ INT NOT NULL REFERENCES FoodItem, /* TODO cascade stuff if FoodItem deleted */
 Amount /* the amount of the FoodItem in the Recipe */ INT NOT NULL,
 PRIMARY KEY (Recipe, FoodItem));


CREATE TABLE IF NOT EXISTS CustomerOrder
(Id /* unique identifier for an Order */ INTEGER PRIMARY KEY,
 Status /* the status of the order (Creating='c', Submitted='s', Completed='d', Cancelled='x', Refunded='r') */ CHAR(1) NOT NULL
    CONSTRAINT check_status CHECK (Status in ('c', 's', 'd', 'x', 'r')),
 Note /* any notes added to the order */ VARCHAR(8000),
 LastUpdated /* the time the order's status was last updated in unix time */ DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 Location /* the location the order was processed if known */ VARCHAR(8000),
 Weather /* the location the order was processed if known */ VARCHAR(8000));


CREATE TABLE IF NOT EXISTS OrderContains
(CustomerOrder /* Id of the Order*/ INTEGER NOT NULL REFERENCES CustomerOrder,
 FoodItem /* Id of the FoodItem contained in the Order */ INTEGER NOT NULL REFERENCES FoodItem,
 PRIMARY KEY (CustomerOrder, FoodItem));


CREATE TABLE IF NOT EXISTS OrderedFoodItem
(Id /* primary key ID */ INTEGER PRIMARY KEY,
 Code /* alphanumberic code of the FoodItem */ VARCHAR(10) NOT NULL
    CONSTRAINT code_min_size CHECK (LENGTH(Code) >= 3),
 Name /* the FoodItem's name */ VARCHAR(20) NOT NULL
    CONSTRAINT name_min_size CHECK (LENGTH(Name) >= 3),
 UnitType /* unit type (count, ml or gram) */ CHAR(1) NOT NULL
    CONSTRAINT check_unit CHECK (UnitType in ('c', 'm', 'g')),
 Cost /* cost of the FoodItem for customers */ VARCHAR(8000) NOT NULL DEFAULT '0'
    /* TODO check that Cost is correct format */,
 IsVegetarian /* whether the item is vegetarian */ BIT NOT NULL DEFAULT 0,
 IsVegan /* whether the item is vegan */ BIT NOT NULL DEFAULT 0,
 IsGlutenFree /* whether the item is gluten free */ BIT NOT NULL DEFAULT 0,
 CalPerUnit /* number of calories per single unit */ VARCHAR(8000)
    /* TODO check that CalPerUnit is a numeric string */);
