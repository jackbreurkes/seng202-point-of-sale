/*
FoodItem (Code, Name, UnitType, StockLevel, IsVegetarian, IsVegan, IsGlutenFree, CalPerUnit) primkey Code
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

