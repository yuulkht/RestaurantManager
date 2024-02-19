package ru.hse.software.construction.controller

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.auth.UserStorage
import ru.hse.software.construction.model.Administrator
import ru.hse.software.construction.model.User
import ru.hse.software.construction.model.Visitor
import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.view.ConsoleOutputHandler

class LoginCommand(
    private val userStorage: UserStorage,
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

            if (userStorage.validateAdminPassword(login, password)) {
                val user = Administrator(login, password)
                info.authSession.login(user)
                outputHandler.displayMessage("Вход в систему выполнен успешно.")
            }
            else if (userStorage.validateVisitorPassword(login, password)) {
                val user = Visitor(login, password)
                info.authSession.login(user)
                outputHandler.displayMessage("Вход в систему выполнен успешно.")
            }
            else {
                outputHandler.displayMessage("Неверный логин или пароль.")
            }
        }
    }
}