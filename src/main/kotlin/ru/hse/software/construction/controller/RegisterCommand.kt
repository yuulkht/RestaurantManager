package ru.hse.software.construction.controller

import org.mindrot.jbcrypt.BCrypt
import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.auth.UserStorage
import ru.hse.software.construction.model.Administrator
import ru.hse.software.construction.model.Visitor
import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.view.ConsoleOutputHandler

class RegisterCommand(
    private val userStorage: UserStorage,
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler()
) : Command {
    override fun process(programInfo: ProgramInfo) {
        execute(programInfo)
    }

    private fun execute(info: ProgramInfo) {
        if (info.authSession.isLoggedIn()) {
            outputHandler.displayMessage("Вы уже зарегистрированы")
        } else {
            outputHandler.displayMessage("Введите логин:")
            val login = reader.readAuthData()

            if (userStorage.userExists(login)) {
                outputHandler.displayMessage("Пользователь с таким логином уже существует")
                return
            }

            outputHandler.displayMessage("Введите пароль:")
            val password = reader.readAuthData()

            outputHandler.displayMessage("Введите специальный код (для регистрации администратора):")
            val adminCode = reader.readAuthData()

            if (adminCode == "1234") {
                val admin = Administrator(login, BCrypt.hashpw(password, BCrypt.gensalt()))
                userStorage.addAdmin(admin)
                info.authSession.login(admin)
            } else {
                val visitor = Visitor(login, BCrypt.hashpw(password, BCrypt.gensalt()))
                userStorage.addVisitor(visitor)
                info.authSession.login(visitor)
            }

            outputHandler.displayMessage("Регистрация выполнена успешно.")
        }
    }
}
