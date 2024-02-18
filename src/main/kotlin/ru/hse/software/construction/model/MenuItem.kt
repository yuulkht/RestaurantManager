package ru.hse.software.construction.model

interface MenuItem {
    fun setQuantity(value: Int)

    fun getName() : String
    fun getPrice() : Int
    fun getPreparationTime() : Int
    fun getQuantity() : Int
}