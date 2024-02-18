package ru.hse.software.construction.controller

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.view.ConsoleOutputHandler

class LoginHandler(private val nextHandler: CommandHandler?) : BaseCommandHandler(nextHandler) {
    override fun handle(command: String, info: ProgramInfo): Boolean {
        if (!info.authSession.isLoggedIn() && (command != "register" && command != "login")) {
            return true
        }
        return super.handle(command, info)
    }
}