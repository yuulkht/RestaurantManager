package ru.hse.software.construction.view

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.model.Order


interface OutputHandler {
    fun displayAdminCommands()
    fun displayVisitorCommands()
    fun displayAuthCommands()
    fun displayMessage(message: String)
    fun displayError(message: String)
    fun displayMenu(info: ProgramInfo)
    fun displayMenuAllBase(info: ProgramInfo)
    fun displayOrder(order: Order)
    fun displayOrderStatus(order: Order)
}

// Реализация интерфейса для вывода в консоль
class ConsoleOutputHandler : OutputHandler {
    override fun displayAdminCommands() {
        println("${ConsoleStyle.BLUE}Список команд для администратора: ${ConsoleStyle.RESET}")
        println()

        println("addItem. Добавить новую позицию в меню ресторана")
        println("deleteItem. Удалить позицию из меню")
        println("changeQuantity. Изменить доступное количество у позиции в меню")
        println("logout. Выйти из системы")

    }

    override fun displayVisitorCommands() {
        println("${ConsoleStyle.BLUE}Список команд для посетителя: ${ConsoleStyle.RESET}")
        println()

        println("createOrder. Создать новый заказ")
        println("payFor. Заплатить за готовые заказы")
        println("addMoney. Пополнить счет")
        println("logout. Выйти из системы")

    }

    override fun displayAuthCommands() {
        println()
        println("Для использования приложения необходим вход в систему!")
        println()
        println("register - Зарегистрироваться")
        println("login - Войти")
    }

    override fun displayMessage(message: String) {
        println(message)
    }

    override fun displayError(message: String) {
        println("${ConsoleStyle.RED}Ошибка: $message${ConsoleStyle.RESET}")
    }

    override fun displayMenu(info: ProgramInfo) {
        println("${ConsoleStyle.BLUE}Текущее меню:${ConsoleStyle.RESET}")
        println(info.restaurant.getMenu())
    }

    override fun displayMenuAllBase(info: ProgramInfo) {
        println("${ConsoleStyle.BLUE}Все позиции ресторана:${ConsoleStyle.RESET}")
        println(info.restaurant.getMenu().getAllBase())
    }

    override fun displayOrder(order: Order) {
        println(order)
    }

    override fun displayOrderStatus(order: Order) {
        println(order.showOrderStatus())
    }
}