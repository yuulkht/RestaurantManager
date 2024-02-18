package ru.hse.software.construction.model

interface MenuItem {
    fun setPrice(value: Int)
    fun setPreparationTime(value: Int)
    fun setQuantity(value: Int)

    fun getPrice() : Int
    fun getPreparationTime() : Int
    fun getQuantity() : Int
}