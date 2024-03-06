package ru.hse.software.construction.controller.administrator

import ru.hse.software.construction.ProgramInfo
import ru.hse.software.construction.controller.Command
import ru.hse.software.construction.view.ConsoleOutputHandler

class SeeStatisticsCommand (
    private val outputHandler: ConsoleOutputHandler = ConsoleOutputHandler(),
) : Command {

    override fun process(programInfo: ProgramInfo) {
        outputHandler.displayStatistics(programInfo.restaurant.getStatisticsManager())
    }
}
