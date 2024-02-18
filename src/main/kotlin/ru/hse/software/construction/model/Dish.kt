package ru.hse.software.construction.model

class Dish(
    private val name: String,
    private var price: Int,
    private var preparationTime: Int,
    private var quantity: Int
) : MenuItem {

    override fun setQuantity(value: Int) {
        quantity = value
    }

    override fun getName(): String {
        return name
    }

    override fun getPrice(): Int {
        return price
    }

    override fun getPreparationTime(): Int {
        return preparationTime
    }

    override fun getQuantity(): Int {
        return quantity
    }

    override fun toString(): String {
        return "Блюдо: $name, цена: $price, время приготовления: $preparationTime"
    }

}