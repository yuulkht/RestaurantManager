package ru.hse.software.construction.model

interface MenuItemFactory {
    fun createDish(name: String, price: Int, preparationTime: Int, quantity: Int) : Dish

}

class WinterMenuItemFactory : MenuItemFactory {
    override fun createDish(name: String, price: Int, preparationTime: Int, quantity: Int): Dish {
        return Dish(name, price, preparationTime, quantity)
    }
}
