package ru.hse.software.construction.model

class Menu (
    private var menuItems : MutableList<MenuItem> = mutableListOf()
) {
    fun addMenuItem(item : MenuItem) {
        menuItems.add(item)
    }

    fun deleteMenuItem(item : MenuItem) {
        menuItems.remove(item)
    }

    override fun toString(): String {
        val dishes = menuItems.filterIsInstance<Dish>()
        val drinks = menuItems.filterIsInstance<Drink>()

        val dishesString = dishes.joinToString(separator = "\n") { it.toString() }
        val drinksString = drinks.joinToString(separator = "\n") { it.toString() }

        return "Блюда:\n$dishesString\n\nНапитки:\n$drinksString"
    }

}