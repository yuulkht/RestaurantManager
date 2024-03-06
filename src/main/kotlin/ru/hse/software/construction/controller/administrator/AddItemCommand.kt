package ru.hse.software.construction.controller.administrator

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.controller.Command
import ru.hse.software.construction.model.MenuItemFactory
import ru.hse.software.construction.model.WinterMenuItemFactory
import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.view.ConsoleOutputHandler

class AddItemCommand(
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
    private val menuItemFactory: MenuItemFactory = WinterMenuItemFactory()
) : Command {

    override fun process(programInfo: ProgramInfo) {
        outputHandler.displayMenuAllBase(programInfo)
        outputHandler.displayMessage("Введите название новой позиции:")
        val name = reader.readString()
        outputHandler.displayMessage("Введите цену:")
        val price = reader.readInt()
        outputHandler.displayMessage("Введите время приготовления (в минутах):")
        val preparationTime = reader.readInt()
        outputHandler.displayMessage("Введите количество:")
        val quantity = reader.readInt()

        val currentMenu = programInfo.restaurant.getMenu()

        if (name != null &&
            price != null &&
            preparationTime != null &&
            quantity != null &&
            !currentMenu.isInMenu(name) &&
            price > 0 &&
            preparationTime > 0 &&
            quantity > 0
        ) {
            val menuItem = menuItemFactory.createDish(name, price, preparationTime, quantity)
            currentMenu.addDish(menuItem)
            outputHandler.displayMessage("Позиция успешно добавлена в меню")
        }
        else {
            outputHandler.displayError("Данные некорректны или создаваемая позиция уже есть в меню")
        }
    }
}
