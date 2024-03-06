package ru.hse.software.construction.view

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.model.Order
import ru.hse.software.construction.model.StatisticsManager


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

    fun displayOrderCommands()
    fun displayStatistics(statisticsManager: StatisticsManager)
}

class ConsoleOutputHandler : OutputHandler {
    override fun displayAdminCommands() {
        println("${ConsoleStyle.BLUE}Список команд для администратора: ${ConsoleStyle.RESET}")
        println()

        val maxWidth = 15 // Максимальная ширина ключа

        println("%-${maxWidth}s %s".format("addItem.", "Добавить новую позицию в меню ресторана"))
        println("%-${maxWidth}s %s".format("deleteItem.", "Удалить позицию из меню"))
        println("%-${maxWidth}s %s".format("changeQuantity.", "Изменить доступное количество у позиции в меню"))
        println("%-${maxWidth}s %s".format("seeStatistics.", "Посмотреть статистику ресторана"))
        println("%-${maxWidth}s %s".format("logout.", "Выйти из системы"))
    }

    override fun displayVisitorCommands() {
        println("${ConsoleStyle.BLUE}Список команд для посетителя: ${ConsoleStyle.RESET}")
        println()

        val maxWidth = 13 // Максимальная ширина ключа

        println("%-${maxWidth}s %s".format("createOrder.", "Создать новый заказ"))
        println("%-${maxWidth}s %s".format("payFor.", "Заплатить за готовые заказы"))
        println("%-${maxWidth}s %s".format("addMoney.", "Пополнить счет"))
        println("%-${maxWidth}s %s".format("logout.", "Выйти из системы"))
    }

    override fun displayAuthCommands() {
        val maxWidth = 10 // Максимальная ширина ключа
        println()
        println("Для использования приложения необходим вход в систему!")
        println()

        println("%-${maxWidth}s %s".format("register.", "Зарегистрироваться"))
        println("%-${maxWidth}s %s".format("login.", "Войти"))
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
        println("Выручка ресторана: ${info.restaurant.getAmountOfRevenue()}")
    }

    override fun displayOrder(order: Order) {
        println(order)
    }

    override fun displayOrderStatus(order: Order) {
        println(order.showOrderStatus())
    }

    override fun displayOrderCommands() {
        println("Сформируйте ваш заказ:")
        println("1. Добавить новую позицию")
        println("2. Завершить выбор")
        println("3. Просмотреть заказ")
        println("4. Отменить заказ")
    }

    override fun displayStatistics(statisticsManager: StatisticsManager) {
        println("${ConsoleStyle.BLUE}${ConsoleStyle.BOLD}Статистика ресторана:${ConsoleStyle.RESET}")
        println()

        val averageRating = statisticsManager.getRestaurantRating()
        println("${ConsoleStyle.YELLOW}Рейтинг ресторана: ${ConsoleStyle.RESET}${"%.2f".format(averageRating)}")
        println("")

        println("${ConsoleStyle.YELLOW}Средние оценки для блюд:${ConsoleStyle.RESET}")
        statisticsManager.getAverageRatings().forEach { (dish, rating) ->
            println("${dish.name}: ${"%.2f".format(rating)}")
        }
        println("")

        println("${ConsoleStyle.YELLOW}Популярность блюд по количеству заказов:${ConsoleStyle.RESET}")
        statisticsManager.getPopularDishesByCount().forEach { (dish, count) ->
            println("${dish.name}: $count")
        }
        println("")
    }

}
