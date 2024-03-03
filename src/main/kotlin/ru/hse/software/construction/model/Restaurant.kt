package ru.hse.software.construction.model


import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.*
import java.math.BigDecimal

class Restaurant : Serializable {
    @JsonIgnore
    private var orders: MutableList<Order> = mutableListOf()

    @JsonIgnore
    private val orderManager: OrderManager = OrderManager()

    @Volatile
    private var amountOfRevenue: Int = 0

    @JsonSerialize
    private var menu: Menu = Menu()

    fun processPayment(order: Order) {
        order.getVisitor().payForOrder(BigDecimal.valueOf(order.getTotalCost().toLong()))
        amountOfRevenue += order.getTotalCost()
        orders.remove(order)
    }

    fun getMenu(): Menu {
        return menu
    }

    fun setMenu(menu: Menu) {
        this.menu = menu
    }

    fun getAmountOfRevenue(): Int {
        return amountOfRevenue
    }

    fun setAmountOfRevenue(amount: Int) {
        this.amountOfRevenue = amount
    }

    fun getOrders() : MutableList<Order> {
        return orders
    }

    fun getOrderManager() : OrderManager {
        return orderManager
    }

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.defaultWriteObject()
        // Сериализуем только меню и выручку
        out.writeObject(menu)
        out.writeInt(amountOfRevenue)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        `in`.defaultReadObject()
        // Восстанавливаем только меню и выручку
        menu = `in`.readObject() as Menu
        amountOfRevenue = `in`.readInt()
    }
}
