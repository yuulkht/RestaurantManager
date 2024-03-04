package ru.hse.software.construction

import ru.hse.software.construction.controller.visitor.AddMoneyCommand
import ru.hse.software.construction.auth.AuthSession
import ru.hse.software.construction.auth.UserStorage
import ru.hse.software.construction.controller.*
import ru.hse.software.construction.controller.administrator.AddItemCommand
import ru.hse.software.construction.controller.administrator.ChangeQuantityCommand
import ru.hse.software.construction.controller.administrator.DeleteItemCommand
import ru.hse.software.construction.controller.visitor.CreateOrderCommand
import ru.hse.software.construction.controller.visitor.PayForOrderCommand
import ru.hse.software.construction.model.Restaurant
import ru.hse.software.construction.repository.RestaurantAppRepository
import ru.hse.software.construction.repository.StoragePathValidator
import ru.hse.software.construction.view.ConsoleOutputHandler

class ProgramInfo(
    var restaurant: Restaurant = Restaurant(),
    var authSession: AuthSession = AuthSession(),
    var userStorage: UserStorage = UserStorage(),
    var commands: MutableMap<String, Command> = mutableMapOf(),
    var restaurantPath: String = "",
    var usersPath: String = ""
) {
    fun isFilledInfo(): Boolean {
        val storagePathValidator = StoragePathValidator()
        val (restaurantPath, usersPath) = storagePathValidator.validatePaths()

        if (restaurantPath == null || usersPath == null) {
            ConsoleOutputHandler().displayError("Некорректные пути к файлам")
            return false
        }
        this.restaurantPath = restaurantPath
        this.usersPath = usersPath
        val repository = RestaurantAppRepository(restaurantPath, usersPath)
        val loadedRestaurant = repository.loadRestaurant()
        val loadedUserStorage = repository.loadUserStorage()

        userStorage.admins.addAll(loadedUserStorage?.admins ?: mutableListOf())
        userStorage.visitors.addAll(loadedUserStorage?.visitors ?: mutableListOf())

        restaurant.getOrderManager().stopAllChefs()
        restaurant = loadedRestaurant

        return true
    }


    init {
        commands["register"] = RegisterCommand(userStorage)
        commands["login"] = LoginCommand(userStorage)
        commands["logout"] = LogoutCommand()
        commands["addItem"] = AddItemCommand()
        commands["deleteItem"] = DeleteItemCommand()
        commands["changeQuantity"] = ChangeQuantityCommand()
        commands["createOrder"] = CreateOrderCommand()
        commands["payFor"] = PayForOrderCommand()
        commands["addMoney"] = AddMoneyCommand()

    }
}