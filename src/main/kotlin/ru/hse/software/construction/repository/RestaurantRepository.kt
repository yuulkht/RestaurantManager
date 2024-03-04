package ru.hse.software.construction.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ru.hse.software.construction.auth.UserStorage
import ru.hse.software.construction.model.Restaurant
import ru.hse.software.construction.view.ConsoleOutputHandler
import java.io.File
import java.io.IOException

class RestaurantAppRepository(
    private var pathToRestaurantFile: String,
    private var pathToUserStorageFile: String,
    private val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())
) {

    fun saveRestaurant(restaurant: Restaurant) {
        try {
            val jsonString = objectMapper.writeValueAsString(restaurant)
            File(pathToRestaurantFile).writeText(jsonString)
        } catch (e: Exception) {
            val outputHandler = ConsoleOutputHandler()
            outputHandler.displayError("Проблема с файлом: $e")
        }
    }

    fun loadRestaurant(): Restaurant {
        return try {
            val jsonString = File(pathToRestaurantFile).readText()
            objectMapper.readValue(jsonString)
        } catch (e: Exception) {
            val outputHandler = ConsoleOutputHandler()
            outputHandler.displayError("Проблема с чтением файла: $e")
            Restaurant()
        }
    }

    fun saveUserStorage(userStorage: UserStorage) {
        try {
            val jsonString = objectMapper.writeValueAsString(userStorage)
            File(pathToUserStorageFile).writeText(jsonString)
        } catch (e: Exception) {
            val outputHandler = ConsoleOutputHandler()
            outputHandler.displayError("Проблема с файлом: $e")
        }
    }

    fun loadUserStorage(): UserStorage? {
        return try {
            val jsonString = File(pathToUserStorageFile).readText()
            return objectMapper.readValue<UserStorage>(jsonString, UserStorage::class.java)
        } catch (e: Exception) {
            val outputHandler = ConsoleOutputHandler()
            outputHandler.displayError("Проблема с чтением файла: $e")
            return UserStorage()
        }
    }

}