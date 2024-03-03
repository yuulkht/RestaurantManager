package ru.hse.software.construction

import ru.hse.software.construction.controller.validation.CommandValidationHandler
import ru.hse.software.construction.controller.validation.ExecutionCommandHandler
import ru.hse.software.construction.controller.validation.LoginHandler
import ru.hse.software.construction.view.ConsoleOutputHandler

// TODO указать файл для работы с рестораном
class Program {
    companion object {

        private val info: ProgramInfo = ProgramInfo()
        private val isFilledInfo = info.isFilledInfo()
        private var exit = false

        private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler()
        private var commandHandlers = LoginHandler(CommandValidationHandler(ExecutionCommandHandler()))

        @JvmStatic
        fun main(args: Array<String>) {
            if (!isFilledInfo) {
                outputHandler.displayError("Проблема с доступом к базе данных ресторана")
                info.restaurant.getOrderManager().stopAllChefs()
                exit = true
            }
            if (!exit) {
                outputHandler.displayMessage("Добро пожаловать в приложение Ресторан!")
            }
            while (!exit) {
                if (!info.authSession.isLoggedIn()) {
                    outputHandler.displayAuthCommands()
                }
                else if (info.authSession.isAdmin()) {
                    outputHandler.displayAdminCommands()
                }
                else {
                    outputHandler.displayVisitorCommands()
                }
                outputHandler.displayMessage("Введите команду (или 'q' для выхода): ")
                val command = readln()

                if (!commandHandlers.handle(command, info)) {
                    exit = true
                }
            }
        }
    }
}