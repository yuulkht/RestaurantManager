package ru.hse.software.construction.controller

import ru.hse.software.construction.ProgramInfo

abstract class BaseCommandHandler(private val nextHandler: CommandHandler?) : CommandHandler {
    override fun handle(command: String, info: ProgramInfo): Boolean {
        return nextHandler?.handle(command, info) ?: false
    }
}