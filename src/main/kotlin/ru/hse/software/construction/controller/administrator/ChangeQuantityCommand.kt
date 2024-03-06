package ru.hse.software.construction.controller.administrator

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.controller.Command
import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.view.ConsoleOutputHandler

class ChangeQuantityCommand (
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
) : Command {

    override fun process(programInfo: ProgramInfo) {
        outputHandler.displayMenuAllBase(programInfo)
        outputHandler.displayMessage("Выберите, у какой именно позиции вы хотите изменить количество, введя ее название:")
        val chosenItem = reader.readString()
        outputHandler.displayMessage("Введите новое количество:")
        val newQuantity = reader.readInt()

        val currentMenu = programInfo.restaurant.getMenu()

        if (chosenItem != null &&
            newQuantity != null &&
            newQuantity > 0 &&
            currentMenu.isInMenu(chosenItem)) {
            currentMenu.getDishByName(chosenItem)?.quantity = (newQuantity)
            outputHandler.displayMessage("Количество успешно изменено")
        } else {
            outputHandler.displayError("Данные для изменения позиции некорректны")
        }
    }
}
