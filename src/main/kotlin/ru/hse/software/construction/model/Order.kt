package ru.hse.software.construction.model

import ru.hse.software.construction.view.ConsoleStyle

interface Observable {
    fun registerObserver(observer : OrderObserver)
    fun notifyObserver()
}

enum class OrderStatus {
    CREATED,
    ACCEPTED,
    PROCESSING,
    READY,
}



class Order(
    private val visitor: Visitor,
    private var dishes: MutableMap<String, Dish> = mutableMapOf(),
    private var status: OrderStatus = OrderStatus.CREATED,
    private var observer: OrderObserver? = null
) : Observable{
    fun setStatus(newStatus: OrderStatus) {
        status = newStatus
        notifyObserver()
    }

    fun addDish(dish: Dish) {
        val dishName = dish.name
        val existingDish = dishes[dishName]
        if (existingDish != null) {
            existingDish.quantity++
        } else {
            dishes[dishName] = dish.copy(quantity = 1)
        }
    }

    fun getDishes(): List<Dish> {
        return dishes.values.toList()
    }

    fun getProcessingTime(): Int {
        return dishes.values.sumOf { dish ->
            dish.preparationTime * dish.quantity
        }
    }

    fun getVisitor(): Visitor {
        return visitor
    }

    fun getStatus(): OrderStatus {
        return status
    }

    fun getTotalCost(): Int {
        return dishes.values.sumOf { dish ->
            dish.price * dish.quantity
        }
    }

    fun showOrderStatus(): String {
        val statusString = when (status) {
            OrderStatus.CREATED -> "создан"
            OrderStatus.ACCEPTED -> "принят"
            OrderStatus.PROCESSING -> "готовится"
            OrderStatus.READY -> "готов"
        }
        return "${ConsoleStyle.PURPLE}Заказ для ${visitor.getLogin()}. Статус заказа: $statusString${ConsoleStyle.RESET}"
    }

    override fun registerObserver(observer: OrderObserver) {
        this.observer = observer
    }

    override fun notifyObserver() {
        observer?.updateOrderStatus(this)
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendLine("${ConsoleStyle.CYAN}--------------------------------------------------${ConsoleStyle.RESET}")
        builder.appendLine("|${ConsoleStyle.YELLOW}               Заказ для ${visitor.getLogin()}                  ${ConsoleStyle.RESET}|")
        builder.appendLine("${ConsoleStyle.CYAN}--------------------------------------------------${ConsoleStyle.RESET}")
        builder.appendLine("| Название       | Цена    | Количество | Подитог  |")
        builder.appendLine("${ConsoleStyle.CYAN}--------------------------------------------------${ConsoleStyle.RESET}")
        dishes.values.forEach { dish ->
            val name = dish.name.padEnd(15)
            val price = dish.price.toString().padEnd(8)
            val quantity = dish.quantity.toString().padEnd(11)
            val subtotal = (dish.price * dish.quantity).toString().padEnd(9)
            builder.appendLine("| $name| $price| $quantity| ${ConsoleStyle.BLUE}$subtotal${ConsoleStyle.RESET}|")
        }
        builder.appendLine("${ConsoleStyle.CYAN}--------------------------------------------------${ConsoleStyle.RESET}")
        builder.appendLine("|                                      Итого: ${ConsoleStyle.BOLD}${ConsoleStyle.GREEN}${getTotalCost()}   ${ConsoleStyle.RESET}|")
        builder.appendLine("${ConsoleStyle.CYAN}--------------------------------------------------${ConsoleStyle.RESET}")
        return builder.toString()
    }

}
