package ru.hse.software.construction.auth

import org.mindrot.jbcrypt.BCrypt
import ru.hse.software.construction.model.Administrator
import ru.hse.software.construction.model.User
import ru.hse.software.construction.model.Visitor

class UserStorage(
    private val users: MutableList<User> = mutableListOf()
) {
    // for serializer
    fun getUsers(): MutableList<User> {
        return users
    }

    fun addUser(login: String, password: String, isAdmin: Boolean = false) {
        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
        if (isAdmin) {
            users.add(Administrator(login, hashedPassword))
        } else {
            users.add(Visitor(login, hashedPassword))
        }
        // FileUserStorageRepository().saveUserStorage(this)
    }

    private fun getUserPassword(login: String): String? {
        val user = users.find { it.getLogin() == login }
        return user?.getHashedPassword()
    }

    fun userExists(login: String): Boolean {
        return users.any { it.getLogin() == login }
    }

    fun validatePassword(login: String, password: String): Boolean {
        val hashedPassword = getUserPassword(login)
        return hashedPassword != null && BCrypt.checkpw(password, hashedPassword)
    }
}
