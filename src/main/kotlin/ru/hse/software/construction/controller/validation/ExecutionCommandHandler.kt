package ru.hse.software.construction.controller.validation

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.controller.validation.CommandHandler
import ru.hse.software.construction.repository.RestaurantAppRepository

class ExecutionCommandHandler : CommandHandler {
    override fun handle(command: String, info: ProgramInfo): Boolean {
        if (command == "q") {
            info.restaurant.getOrderManager().stopAllChefs()
            RestaurantAppRepository().saveUserStorage(info.userStorage)
            // TODO: решить проблемы с сереализацией
            RestaurantAppRepository().saveRestaurant(info.restaurant)
            return false
        }
        info.commands[command]!!.process(info)
        return true
    }
}