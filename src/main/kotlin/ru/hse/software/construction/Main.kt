package ru.hse.software.construction

import ru.hse.software.construction.controller.*
import ru.hse.software.construction.view.ConsoleOutputHandler

class Program {
    companion object {

        private val info: ProgramInfo = ProgramInfo()
        private val isFilledInfo = info.isFilledInfo()

        private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler()
        private var commandHandlers = LoginHandler(CommandValidationHandler(ExecutionCommandHandler()))

        @JvmStatic
        fun main(args: Array<String>) {
            if (!isFilledInfo) {
                outputHandler.displayError("Проблема с доступом к базе данных ресторана")
                info.exit = true
            }
            if (!info.exit) {
                outputHandler.displayMessage("Добро пожаловать в приложение Ресторан!")
            }
            while (!info.exit) {
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
                    return
                }
            }
        }
    }
}