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

    fun addUser(user: User) {
        users.add(user)
        // FileUserStorageRepository().saveUserStorage(this)
    }

    private fun getUserByLogin(login: String): User? {
        return users.find { it.getLogin() == login }
    }

    fun userExists(login: String): Boolean {
        return users.any { it.getLogin() == login }
    }

    fun validateVisitorPassword(login: String, password: String): Boolean {
        val user = getUserByLogin(login)
        return user != null && user is Visitor && BCrypt.checkpw(password, user.getHashedPassword())
    }

    fun validateAdminPassword(login: String, password: String): Boolean {
        val user = getUserByLogin(login)
        return user != null && user is Administrator && BCrypt.checkpw(password, user.getHashedPassword())
    }
}
