package ru.hse.software.construction.controller.visitor

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.controller.Command
import ru.hse.software.construction.model.*
import ru.hse.software.construction.view.ConsoleOutputHandler
import ru.hse.software.construction.reader.ConsoleUserReader

class CreateOrderCommand(
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
) : Command {
    override fun process(programInfo: ProgramInfo) {
        val order = Order(visitor = (programInfo.authSession.getUser() as? Visitor) ?: return)

        val currentMenu = programInfo.restaurant.getMenu()

        while (true) {
            outputHandler.displayOrderCommands()

            when (reader.readInt()) {
                1 -> addDishToOrder(order, programInfo, currentMenu)
                2 -> if (finishOrder(order, programInfo)) {
                    break
                }
                3 -> outputHandler.displayOrder(order)
                4 -> {
                    cancelOrder(order, programInfo)
                    break
                }
                else -> outputHandler.displayMessage("Некорректный выбор.")
            }
        }
    }

    private fun addDishToOrder(order: Order, programInfo: ProgramInfo, currentMenu : Menu) {
        outputHandler.displayMenu(programInfo)
        outputHandler.displayMessage("Введите название позиции:")
        val chosenItemName = reader.readString()

        chosenItemName?.let { itemName ->
            val chosenItem = currentMenu.getDishByName(itemName)
            if (chosenItem != null && chosenItem.quantity != 0) {
                order.addDish(chosenItem)
                chosenItem.quantity -= 1
            } else {
                outputHandler.displayError("Позиция не найдена в меню")
            }
        } ?: outputHandler.displayError("Некорректное название позиции")
    }

    private fun finishOrder(order: Order, programInfo: ProgramInfo): Boolean {
        if (order.getDishes().isEmpty()) {
            outputHandler.displayMessage("Ваш заказ пуст. Добавьте позиции перед завершением.")
            return false
        } else {
            outputHandler.displayOrder(order)
            outputHandler.displayMessage("Подтверждаете ли вы свой заказ? (y/n):")
            val confirmation = reader.readString()
            return if (confirmation.equals("y", ignoreCase = true)) {
                programInfo.restaurant.getOrders().add(order)
                outputHandler.displayMessage("Ваш заказ принят и отправлен на обработку.")
                val visitor = programInfo.authSession.getUser() as Visitor
                order.registerObserver(visitor)
                order.setStatus(OrderStatus.ACCEPTED)
                programInfo.restaurant.getOrderManager().processOrder(order)
                true
            } else {
                cancelOrder(order, programInfo)
                true
            }
        }
    }

    private fun cancelOrder(order: Order, programInfo: ProgramInfo) {
        val restaurant = programInfo.restaurant
        val menu = restaurant.getMenu()

        order.getDishes().forEach { dish ->
            val menuItem = menu.getDishByName(dish.name)
            if (menuItem != null) {
                menuItem.quantity += dish.quantity
            } else {
                menu.addDish(dish)
            }
        }

        restaurant.getOrders().remove(order)
        outputHandler.displayMessage("Ваш заказ отменен")
    }

}
