package ru.hse.software.construction.controller.validation

import ru.hse.software.construction.ProgramInfo

class LoginHandler(private val nextHandler: CommandHandler?) : BaseCommandHandler(nextHandler) {
    override fun handle(command: String, info: ProgramInfo): Boolean {
        if (!info.authSession.isLoggedIn() && command != "register" && command != "login" && command != "q") {
            return true
        }
        return super.handle(command, info)
    }
}