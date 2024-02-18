package ru.hse.software.construction.controller

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.model.MenuItemFactory
import ru.hse.software.construction.model.MenuItemType
import ru.hse.software.construction.model.WinterMenuItemFactory
import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.view.ConsoleOutputHandler

class ChangeQuantityCommand (
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
) : Command {

    override fun process(programInfo: ProgramInfo) {
        outputHandler.displayMenu(programInfo)
        outputHandler.displayMessage("Выберите, у какой именно позиции вы хотите изменить количество, введя ее название:")
        val chosenItem = reader.readString()
        outputHandler.displayMessage("Введите новое количество:")
        val newQuantity = reader.readInt()

        val currentMenu = programInfo.restaurant.getMenu()

        if (chosenItem != null &&
            newQuantity != null &&
            newQuantity >= 0 &&
            currentMenu.isInMenu(chosenItem)) {
            currentMenu.getMenuItemByName(chosenItem)?.setQuantity(newQuantity)
            outputHandler.displayMessage("Количество успешно изменено")
        } else {
            outputHandler.displayError("Данные для изменения позиции некорректны")
        }
    }
}
