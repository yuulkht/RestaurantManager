package ru.hse.software.construction.model

class Restaurant (
    private var menu : Menu = Menu(),
    private var orders : MutableList<Order> = mutableListOf(),
    private val orderManager : OrderManager = OrderManager(),
    private var amountOfRevenue : Int = 0
) {
    fun processPayment(order : Order) {
        order.getVisitor().payForOrder(order.getTotalCost())
        amountOfRevenue += order.getTotalCost()
        orders.remove(order)
    }

    fun addMenuItem(item : MenuItem) {
        menu.addMenuItem(item)
    }

    fun deleteMenuItem(item : MenuItem) {
        menu.deleteMenuItem(item)
    }

    fun getMenu() : Menu {
        return menu
    }

    fun getAmountOfRevenue() : Int {
        return amountOfRevenue
    }

}