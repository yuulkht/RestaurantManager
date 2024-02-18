package ru.hse.software.construction.controller

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.model.MenuItemFactory
import ru.hse.software.construction.model.MenuItemType
import ru.hse.software.construction.model.WinterMenuItemFactory
import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.view.ConsoleOutputHandler

class AddItemCommand(
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
    private val menuItemFactory: MenuItemFactory = WinterMenuItemFactory()
) : Command {

    override fun process(programInfo: ProgramInfo) {
        outputHandler.displayMenu(programInfo)
        outputHandler.displayMessage("Выберите, что именно вы хотите добавить в меню:")
        outputHandler.displayMessage("1. Блюдо")
        outputHandler.displayMessage("2. Напиток")
        val choice = reader.readInt()

        when (choice) {
            1 -> createMenuItem(programInfo, MenuItemType.DISH)
            2 -> createMenuItem(programInfo, MenuItemType.DRINK)
            else -> outputHandler.displayMessage("Некорректный выбор.")
        }
    }

    private fun createMenuItem(programInfo: ProgramInfo, type: MenuItemType) {
        outputHandler.displayMessage("Введите название:")
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
            val menuItem = menuItemFactory.createMenuItem(type, name, price, preparationTime, quantity)
            currentMenu.addMenuItem(menuItem)
            outputHandler.displayMessage("Позиция успешно добавлена в меню")
        }
        else {
            outputHandler.displayError("Данные некорректны или создаваемая позиция уже есть в меню")
        }
    }
}
