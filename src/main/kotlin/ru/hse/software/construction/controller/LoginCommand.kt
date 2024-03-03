package ru.hse.software.construction.controller

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.auth.UserStorage
import ru.hse.software.construction.model.Administrator
import ru.hse.software.construction.model.User
import ru.hse.software.construction.model.Visitor
import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.view.ConsoleOutputHandler

class LoginCommand(
    val userStorage: UserStorage = UserStorage(),
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler()
) : Command {
    override fun process(programInfo: ProgramInfo) {
        execute(programInfo)
    }

    private fun execute(info: ProgramInfo) {
        if (info.authSession.isLoggedIn()) {
            outputHandler.displayMessage("Вход в систему уже выполнен")
        } else {
            outputHandler.displayMessage("Введите логин:")
            val login = reader.readAuthData()
            outputHandler.displayMessage("Введите пароль:")
            val password = reader.readAuthData()

            val admin = userStorage.admins.find { it.getLogin() == login && userStorage.validateAdminPassword(login, password) }
            val visitor = userStorage.visitors.find { it.getLogin() == login && userStorage.validateVisitorPassword(login, password) }

            if (admin != null) {
                info.authSession.login(admin)
                outputHandler.displayMessage("Вход админа в систему выполнен успешно.")
            }
            else if (visitor != null) {
                info.authSession.login(visitor)
                outputHandler.displayMessage("Вход посетителя в систему выполнен успешно.")
            }
            else {
                outputHandler.displayMessage("Неверный логин или пароль.")
            }
        }
    }
}