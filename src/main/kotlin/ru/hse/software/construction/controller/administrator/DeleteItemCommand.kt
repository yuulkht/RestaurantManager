package ru.hse.software.construction.controller.administrator

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.controller.Command
import ru.hse.software.construction.reader.ConsoleUserReader
import ru.hse.software.construction.view.ConsoleOutputHandler

class DeleteItemCommand (
    private val reader: ConsoleUserReader = ConsoleUserReader(),
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
) : Command {

    override fun process(programInfo: ProgramInfo) {
        outputHandler.displayMenu(programInfo)
        outputHandler.displayMessage("Выберите, что именно вы хотите удалить из меню, введя название позиции:")
        val chosenItem = reader.readString()

        val currentMenu = programInfo.restaurant.getMenu()

        if (chosenItem != null && currentMenu.isInMenu(chosenItem)) {
            currentMenu.deleteDish(chosenItem)
            outputHandler.displayMessage("Позиция успешно удалена из меню")
        } else {
            outputHandler.displayError("Позиция не найдена в меню")
        }
    }
}
