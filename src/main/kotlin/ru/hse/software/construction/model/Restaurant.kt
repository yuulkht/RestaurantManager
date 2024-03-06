package ru.hse.software.construction.model


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

    @JsonSerialize
    private var statisticsManager: StatisticsManager = StatisticsManager()

    fun getMenu(): Menu {
        return menu
    }

    fun setMenu(menu: Menu) {
        this.menu = menu
    }

    fun getStatisticsManager(): StatisticsManager {
        return statisticsManager
    }

    fun setStatisticsManager(statisticsManager: StatisticsManager) {
        this.statisticsManager = statisticsManager
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
        out.writeObject(menu)
        out.writeObject(statisticsManager)
        out.writeInt(amountOfRevenue)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        `in`.defaultReadObject()
        menu = `in`.readObject() as Menu
        statisticsManager = `in`.readObject() as StatisticsManager
        amountOfRevenue = `in`.readInt()
    }
}
