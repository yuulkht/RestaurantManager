package ru.hse.software.construction

import AddMoneyCommand
import ru.hse.software.construction.auth.AuthSession
import ru.hse.software.construction.auth.UserStorage
import ru.hse.software.construction.controller.*
import ru.hse.software.construction.controller.administrator.AddItemCommand
import ru.hse.software.construction.controller.administrator.ChangeQuantityCommand
import ru.hse.software.construction.controller.administrator.DeleteItemCommand
import ru.hse.software.construction.model.Restaurant
import ru.hse.software.construction.repository.RestaurantAppRepository

class ProgramInfo(
    var restaurant: Restaurant = Restaurant(),
    var authSession: AuthSession = AuthSession(),
    var userStorage: UserStorage = UserStorage(),
    var commands: MutableMap<String, Command> = mutableMapOf(),
    // TODO поменять файлы на изменяемые
) {
    fun isFilledInfo(): Boolean {
        val loadedRestaurant = RestaurantAppRepository().loadRestaurant()
        val loadedUserStorage = RestaurantAppRepository().loadUserStorage()

        userStorage.admins.addAll(loadedUserStorage?.admins ?: mutableListOf())
        userStorage.visitors.addAll(loadedUserStorage?.visitors ?: mutableListOf())


        return if (loadedRestaurant != null) {
            restaurant.getOrderManager().stopAllChefs()
            restaurant = loadedRestaurant
            true
        } else {
            false
        }
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