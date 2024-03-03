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
){

    override fun toString(): String {

        return "Позиция: %-15s цена: %-5d время приготовления: %-5d количество: %-5d".format(
            name, price, preparationTime, quantity)
    }

}