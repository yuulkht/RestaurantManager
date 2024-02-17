package ru.hse.software.construction.model

class Drink (
    private val name: String,
    private var price: Int,
    private var preparationTime: Int,
    private var quantity: Int
) : MenuItem {
    override fun setPrice(value: Int) {
        price = value
    }

    override fun setPreparationTime(value: Int) {
        preparationTime = value
    }

    override fun setQuantity(value: Int) {
        quantity = value
    }

    override fun toString(): String {
        return "Напиток: $name, цена: $price, время приготовления: $preparationTime"
    }

}