package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize

data class Dish(
    @JsonSerialize
    val name: String = "",
    @JsonSerialize
    var price: Int = 1,
    @JsonSerialize
    var preparationTime: Int = 1,
    @JsonSerialize
    var quantity: Int = 1
){ //: MenuItem {

//    override fun setQuantity(value: Int) {
//        quantity = value
//    }
//
//    override fun getName(): String {
//        return name
//    }
//
//    override fun getPrice(): Int {
//        return price
//    }
//
//    override fun getPreparationTime(): Int {
//        return preparationTime
//    }
//
//    override fun getQuantity(): Int {
//        return quantity
//    }

    override fun toString(): String {
        return "Позиция: $name, цена: $price, время приготовления: $preparationTime, количество: $quantity"
    }

}