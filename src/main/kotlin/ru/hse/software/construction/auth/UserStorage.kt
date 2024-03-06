package ru.hse.software.construction.auth

import org.mindrot.jbcrypt.BCrypt
import ru.hse.software.construction.model.Administrator
import ru.hse.software.construction.model.Visitor

class UserStorage(
    val admins: MutableList<Administrator> = mutableListOf(),
    val visitors: MutableList<Visitor> = mutableListOf(),
) {
    fun addAdmin(admin: Administrator) {
        admins.add(admin)
    }

    fun addVisitor(visitor: Visitor) {
        visitors.add(visitor)
    }

    private fun getAdminByLogin(login: String): Administrator? {
        return admins.find { it.getLogin() == login }
    }

    private fun getVisitorByLogin(login: String): Visitor? {
        return visitors.find { it.getLogin() == login }
    }

    fun userExists(login: String): Boolean {
        return getAdminByLogin(login) != null || getVisitorByLogin(login) != null
    }

    fun validateVisitorPassword(login: String, password: String): Boolean {
        val user = getVisitorByLogin(login)
        return user != null && BCrypt.checkpw(password, user.getHashedPassword())
    }

    fun validateAdminPassword(login: String, password: String): Boolean {
        val user = getAdminByLogin(login)
        return user != null && BCrypt.checkpw(password, user.getHashedPassword())
    }
}
