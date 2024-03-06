package ru.hse.software.construction.controller.visitor

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.controller.Command
import ru.hse.software.construction.model.*
import ru.hse.software.construction.view.ConsoleOutputHandler
import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.view.ConsoleStyle
import java.math.BigDecimal

class PayForOrderCommand(
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
) : Command {
    override fun process(programInfo: ProgramInfo) {
        val visitor = programInfo.authSession.getUser() as? Visitor
        when {
            visitor == null -> outputHandler.displayError("Не удалось определить пользователя.")
            getCurrentOrders(visitor, programInfo).isEmpty() -> outputHandler.displayMessage("У вас нет готовых заказов для оплаты.")
            else -> {
                val visitorOrders = getCurrentOrders(visitor, programInfo)
                outputHandler.displayMessage("Ваши активные заказы:")
                visitorOrders.forEachIndexed { index, order ->
                    outputHandler.displayMessage("${index + 1}. $order")
                }
                val totalCost = visitorOrders.sumOf { it.getTotalCost() }
                outputHandler.displayMessage("Баланс вашего счета: ${visitor.getBalance()}")
                outputHandler.displayMessage("Общая стоимость ваших заказов: $totalCost")
                if (payForOrders(visitorOrders, visitor, programInfo)) {
                    outputHandler.displayMessage("Обновленный баланс вашего счета: ${visitor.getBalance()}")
                    programInfo.restaurant.setAmountOfRevenue(programInfo.restaurant.getAmountOfRevenue() + totalCost)
                    outputHandler.displayMessage("${ConsoleStyle.GREEN}Заказ(ы) успешно оплачены${ConsoleStyle.RESET}")
                    rateOrders(visitorOrders, programInfo)
                    removeOrdersFromRestaurant(visitorOrders, programInfo)
                }
                else {
                    outputHandler.displayMessage("На счете недостаточно средств для оплаты")
                }


            }
        }
    }

    private fun getCurrentOrders(visitor: Visitor, info: ProgramInfo): List<Order> {
        return info.restaurant.getOrders().filter { it.getVisitor() == visitor && it.getStatus() == OrderStatus.READY}
    }

    private fun payForOrders(orders: List<Order>, visitor: Visitor, programInfo: ProgramInfo) : Boolean {
        val totalCost = orders.sumOf { it.getTotalCost() }
        return visitor.payForOrder(BigDecimal.valueOf(totalCost.toLong()))
    }

    private fun removeOrdersFromRestaurant(orders: List<Order>, programInfo: ProgramInfo) {
        val restaurant = programInfo.restaurant
        orders.forEach { order ->
            restaurant.getOrders().remove(order)
        }
    }

    private fun rateOrders(orders: List<Order>, programInfo: ProgramInfo) {
        outputHandler.displayMessage("${ConsoleStyle.BLUE} Оцените свои заказы! ${ConsoleStyle.RESET}")
        orders.forEach { order ->
            outputHandler.displayOrder(order)
            outputHandler.displayMessage("Введите вашу оценку от 1 до 5:")
            val rating = reader.readInt()
            outputHandler.displayMessage("Введите ваш комментарий (необязательно):")
            val description = reader.readString()
            if (rating != null && rating in 1..5 && description != null) {
                programInfo.restaurant.getStatisticsManager().addReview(rating, description, order)
                outputHandler.displayMessage("Спасибо за вашу оценку!")
            } else {
                outputHandler.displayError("Некорректные данные")
            }
        }
    }


}
