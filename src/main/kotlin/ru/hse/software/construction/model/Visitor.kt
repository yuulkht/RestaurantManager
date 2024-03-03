package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal

class Visitor (
    private val login : String = "",
    private val hashedPassword : String = "",
    private var balance : BigDecimal = BigDecimal.valueOf(100)
) : User {
    override fun getLogin(): String {
        return login
    }

    override fun getHashedPassword(): String {
        return hashedPassword
    }

    fun getBalance() : BigDecimal {
        return balance
    }

    fun setBalance(amount : BigDecimal) {
        balance = amount
    }

    fun addBalance(amount: BigDecimal) {
        balance += amount
    }

    fun payForOrder(payment : BigDecimal) : Boolean {
        return if (balance < payment) {
            false
        } else {
            balance -= payment
            true
        }
    }

}