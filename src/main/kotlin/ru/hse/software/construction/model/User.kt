package ru.hse.software.construction.model

interface User {
    fun getLogin() : String
    fun getHashedPassword() : String
}