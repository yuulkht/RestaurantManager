package ru.hse.software.construction.model

enum class MenuItemType {
    DISH,
    DRINK
}

interface MenuItemFactory {
    fun createMenuItem(type: MenuItemType, name: String, price: Int, preparationTime: Int, quantity: Int) : MenuItem

}

class WinterMenuItemFactory : MenuItemFactory {
    override fun createMenuItem(type: MenuItemType, name: String, price: Int, preparationTime: Int, quantity: Int): MenuItem {
        return when (type) {
            MenuItemType.DISH -> Dish(name, price, preparationTime, quantity)
            MenuItemType.DRINK -> Drink(name, price, preparationTime, quantity)
        }
    }
}
