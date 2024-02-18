package ru.hse.software.construction.model

class Administrator(
    private val login : String,
    private val hashedPassword : String
) : User {
    override fun getLogin(): String {
        return login
    }

    override fun getHashedPassword(): String {
        return hashedPassword
    }

}