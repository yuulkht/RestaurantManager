package ru.hse.software.construction.controller

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.view.ConsoleOutputHandler

class CommandValidationHandler(private val nextHandler: CommandHandler?) : BaseCommandHandler(nextHandler) {
    override fun handle(command: String, info: ProgramInfo): Boolean {
        if (command == "q" || command == "register" || command == "login") {
            return super.handle(command, info)
        }

        if (!info.commands.containsKey(command)) {
            ConsoleOutputHandler().displayMessage("Неопознанная команда")
            return true
        }

        else if (info.authSession.isAdmin() && (command == "addItem" || command == "deleteItem" || command == "changeItem")) {
            return super.handle(command, info)
        }

        else if (!info.authSession.isAdmin() && !(command == "addItem" || command == "deleteItem" || command == "changeItem")) {
            return super.handle(command, info)
        }

        else {
            ConsoleOutputHandler().displayMessage("У вас нет доступа к введенной команде")
            return true
        }
    }
}