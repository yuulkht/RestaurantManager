package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonSerialize

data class Menu (
    @JsonSerialize
    var dishes : MutableList<Dish> = mutableListOf()
) {
    fun addDish(dish : Dish) {
        dishes.add(dish)
    }

    fun deleteDish(dishName: String) {
        val itemToRemove = dishes.find { it.name == dishName }
        dishes.remove(itemToRemove)
    }

    fun isInMenu(dishName: String): Boolean {
        return dishes.any { it.name == dishName }
    }

    fun getDishByName(dishName: String) : Dish? {
        return dishes.find { it.name == dishName }
    }

    @JsonIgnore
    fun getAllBase() : String {
        val dishes = dishes.toList()

        val dishesString = dishes.joinToString(separator = "\n") { it.toString() }

        return "\n$dishesString\n"
    }

    override fun toString(): String {
        val dishesWithQuantity = dishes.filter { it.quantity > 0 }
        val dishesString = dishesWithQuantity.joinToString(separator = "\n") { it.toString() }
        return "\n$dishesString\n"
    }

}