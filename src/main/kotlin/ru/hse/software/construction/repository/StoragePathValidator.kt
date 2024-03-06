package ru.hse.software.construction.repository

import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.reader.UserReader
import ru.hse.software.construction.view.ConsoleOutputHandler
import java.io.File

class StoragePathValidator(
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
    private val reader: UserReader = ConsoleUserReader()
) {

    fun validatePaths(): Pair<String?, String?> {
        outputHandler.displayMessage("Введите путь к файлу с данными ресторана:")
        val restaurantPath = reader.readString()
        if (restaurantPath == null || !File(restaurantPath).exists()) {
            outputHandler.displayError("Некорректный путь к файлу ресторана.")
            return Pair(null, null)
        }

        outputHandler.displayMessage("Введите путь к файлу с данными пользователей:")
        val usersPath = reader.readString()
        if (usersPath == null || !File(usersPath).exists()) {
            outputHandler.displayError("Некорректный путь к файлу пользователей.")
            return Pair(null, null)
        }

        return Pair(restaurantPath, usersPath)
    }
}
