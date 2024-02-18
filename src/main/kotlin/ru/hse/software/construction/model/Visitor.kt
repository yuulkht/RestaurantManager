package ru.hse.software.construction.model

class Visitor (
    private val login : String,
    private val hashedPassword : String,
    private var balance : Int = 100
) : User {
    override fun getLogin(): String {
        return login
    }

    override fun getHashedPassword(): String {
        return hashedPassword
    }

    fun payForOrder(payment : Int) {
        balance -= payment
    }

    fun getBalance() : Int {
        return balance
    }

}