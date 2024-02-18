package ru.hse.software.construction.view

import ru.hse.software.construction.ProgramInfo


interface OutputHandler {
    fun displayAdminCommands()
    fun displayVisitorCommands()
    fun displayAuthCommands()
    fun displayMessage(message: String)
    fun displayError(message: String)
    fun displayMenu(info: ProgramInfo)
}

// Реализация интерфейса для вывода в консоль
class ConsoleOutputHandler : OutputHandler {
    override fun displayAdminCommands() {
        println("Список АДМИН команд: ")
        println()

        println("1 - Зафиксировать продажу билета")
        println("2 - Вернуть билет")
        println("3 - Выбрать сеанс и посмотреть свободные места")
        println("4 - Редактировать информацию о фильмах в прокате")
        println("5 - Редактировать информацию о сеансах")
        println("6 - Отметить занятые места в зале")
        println("logout - Выйти из системы")

    }

    override fun displayVisitorCommands() {
        println("Список ВИЗИТОР команд: ")
        println()

        println("1 - Зафиксировать продажу билета")
        println("2 - Вернуть билет")
        println("3 - Выбрать сеанс и посмотреть свободные места")
        println("4 - Редактировать информацию о фильмах в прокате")
        println("5 - Редактировать информацию о сеансах")
        println("6 - Отметить занятые места в зале")
        println("logout - Выйти из системы")

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
        println("Ошибка: $message")
    }

    override fun displayMenu(info: ProgramInfo) {
        println("Текущее меню:")
        println(info.restaurant.getMenu())
    }
}