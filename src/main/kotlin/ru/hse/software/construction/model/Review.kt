package ru.hse.software.construction.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize

class Review(
    @JsonSerialize
    private val rating: Int = 1,
    @JsonSerialize
    private val description: String = "",
    @JsonSerialize
    private val order: Order
) {
    fun getRating(): Int {
        return rating
    }
    fun getDescription(): String {
        return description
    }
    fun getOrder() : Order{
        return order
    }

}