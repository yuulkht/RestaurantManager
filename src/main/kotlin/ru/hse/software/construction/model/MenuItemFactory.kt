package ru.hse.software.construction.model

interface MenuItemFactory {
    fun createDish(name: String, price: Int, preparationTime: Int, quantity: Int) : MenuItem
    fun createDrink(name: String, price: Int, preparationTime: Int, quantity: Int) : MenuItem
}

class ConcreteMenuItemFactory : MenuItemFactory {
    override fun createDish(name: String, price: Int, preparationTime: Int, quantity: Int): MenuItem {
        return Dish(name, price, preparationTime, quantity)
    }

    override fun createDrink(name: String, price: Int, preparationTime: Int, quantity: Int): MenuItem {
        return Drink(name, price, preparationTime, quantity)
    }
}
