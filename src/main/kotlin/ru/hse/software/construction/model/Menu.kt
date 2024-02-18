package ru.hse.software.construction.model

class Menu (
    private var menuItems : MutableList<MenuItem> = mutableListOf()
) {
    fun addMenuItem(item : MenuItem) {
        menuItems.add(item)
    }

    fun deleteMenuItem(itemName: String) {
        val itemToRemove = menuItems.find { it.getName() == itemName }
        menuItems.remove(itemToRemove)
    }

    fun isInMenu(itemName: String): Boolean {
        return menuItems.any { it.getName() == itemName }
    }

    fun getMenuItemByName(itemName: String) : MenuItem? {
        return menuItems.find { it.getName() == itemName }
    }

    override fun toString(): String {
        val dishes = menuItems.filterIsInstance<Dish>()
        val drinks = menuItems.filterIsInstance<Drink>()

        val dishesString = dishes.joinToString(separator = "\n") { it.toString() }
        val drinksString = drinks.joinToString(separator = "\n") { it.toString() }

        return "Блюда:\n$dishesString\n\nНапитки:\n$drinksString"
    }

}