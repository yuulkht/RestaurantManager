package ru.hse.software.construction

import ru.hse.software.construction.controller.*
import ru.hse.software.construction.view.ConsoleOutputHandler

class Program {
    companion object {

        private val info: ProgramInfo = ProgramInfo()
        private val isFilledInfo = info.isFilledInfo()

        private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler()
        private var exit: Boolean = false
        private var commandHandlers = LoginHandler(CommandValidationHandler(ExecutionCommandHandler()))

        @JvmStatic
        fun main(args: Array<String>) {
            if (!isFilledInfo) {
                outputHandler.displayError("проблема с доступом к базе данных кинотеатра")
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
                    return
                }
            }
        }
    }
}