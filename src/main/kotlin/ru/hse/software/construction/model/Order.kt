package ru.hse.software.construction.model

enum class OrderStatus {
    ACCEPTED,
    PROCESSING,
    READY
}

class Order (
    private var menuItems: MutableList<MenuItem> = mutableListOf(),
    private val visitor: Visitor,
    private var status: OrderStatus = OrderStatus.ACCEPTED
) {
    fun setStatus(newStatus: OrderStatus) {
        status = newStatus
    }
    fun addMenuItem(item: MenuItem) {
        menuItems.add(item)
    }

    fun getProcessingTime() : Int {
        return menuItems.sumOf { menuItem ->
            menuItem.getPreparationTime()
        }
    }

    fun getVisitor() : Visitor {
        return visitor
    }

    fun getTotalCost() : Int {
        return menuItems.sumOf { menuItem ->
            menuItem.getPrice()
        }
    }

    override fun toString(): String {
        val statusString = when (status) {
            OrderStatus.ACCEPTED -> "принят"
            OrderStatus.PROCESSING -> "готовится"
            OrderStatus.READY -> "готов"
        }
        return "Заказ для ${visitor.getLogin()}. Статус заказа: $statusString"
    }
}