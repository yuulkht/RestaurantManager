package ru.hse.software.construction

import ru.hse.software.construction.auth.AuthSession
import ru.hse.software.construction.auth.UserStorage
import ru.hse.software.construction.controller.Command
import ru.hse.software.construction.model.Restaurant

class ProgramInfo(
    var restaurant: Restaurant = Restaurant(),
    var authSession: AuthSession = AuthSession(),
    var userStorage: UserStorage = UserStorage(),
    var exit : Boolean = false,
    var commands: MutableMap<String, Command> = mutableMapOf()
) {
    fun isFilledInfo() : Boolean {
        return true
    }

    init {
        //commands["register"] = RegisterCommand(info.userStorage)
        //commands["login"] = LoginCommand(info.userStorage)
        //commands["logout"] = LogoutCommand()
    }
}