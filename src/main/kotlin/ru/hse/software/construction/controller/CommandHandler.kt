package ru.hse.software.construction.controller

import ru.hse.software.construction.ProgramInfo

interface CommandHandler {
    fun handle(command: String, info: ProgramInfo): Boolean
}