package ru.hse.software.construction.controller

import ru.hse.software.construction.ProgramInfo

class ExecutionCommandHandler : CommandHandler {
    override fun handle(command: String, info: ProgramInfo): Boolean {
        if (command == "q") {
            info.exit = true
            return false
        }
        // info.commands[command]!!.process(info)
        return true
    }
}