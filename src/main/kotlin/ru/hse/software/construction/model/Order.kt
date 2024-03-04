package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import ru.hse.software.construction.view.ConsoleStyle
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

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
    @JsonSerialize
    private var visitor: Visitor,
    @JsonSerialize
    private var dishes: MutableList<Dish> = mutableListOf(),
    @JsonIgnore
    private var status: OrderStatus = OrderStatus.CREATED,
    @JsonIgnore
    private var observer: OrderObserver? = null
) : Observable, Serializable {

    fun setStatus(newStatus: OrderStatus) {
        status = newStatus
        notifyObserver()
    }

    fun addDish(dish: Dish) {
        val existingDish = dishes.find { it.name == dish.name }
        if (existingDish != null) {
            existingDish.quantity++
        } else {
            dishes.add(dish.copy(quantity = 1))
        }
    }


    fun getDishes(): MutableList<Dish> {
        return dishes
    }

    @JsonIgnore
    fun getProcessingTime(): Int {
        return dishes.sumOf { dish ->
            dish.preparationTime * dish.quantity
        }
    }

    fun getVisitor(): Visitor {
        return visitor
    }

    fun getStatus(): OrderStatus {
        return status
    }

    @JsonIgnore
    fun getTotalCost(): Int {
        return dishes.sumOf { dish ->
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
        dishes.forEach { dish ->
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
