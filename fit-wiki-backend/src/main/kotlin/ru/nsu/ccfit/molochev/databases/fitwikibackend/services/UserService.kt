package ru.nsu.ccfit.molochev.databases.fitwikibackend.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.ForbiddenException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.NotFoundException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.UnauthorizedException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.User
import ru.nsu.ccfit.molochev.databases.fitwikibackend.repos.UserRepository
import ru.nsu.ccfit.molochev.databases.fitwikibackend.utils.SessionPool
import java.lang.Exception
import java.util.*

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    fun getUsers() = userRepository.findAll().toList()

    fun getUserByID(id: UUID): User {
        val user = userRepository.findById(id)
        if (user != Optional.empty<User>()) return user.get()
        throw NotFoundException(id)
    }

    fun loginUser(username: String, password: String): User {
        val userOpt = userRepository.findByUsername(username)
        if (userOpt == Optional.empty<User>()) throw NotFoundException(username)
        val user = userOpt.get()
        if (user.password != password) throw ForbiddenException()
        return user
    }

    fun containsUser(username: String) = userRepository.existsByUsername(username)

    fun addUser(user: User): User {
        if (userRepository.existsById(user.id)){
            throw NotFoundException(user.id)
        }
        return userRepository.save(user)
    }

    fun editUser(user: User): User {
        if (!userRepository.existsById(user.id)){
            throw NotFoundException(user.id)
        }
        return userRepository.save(user)
    }

    fun deleteUser(id: UUID){
        userRepository.deleteById(id)
    }

    fun initSession(id: UUID): String{
        if (SessionPool.sessions.containsValue(id)){
            val entries = SessionPool.sessions.entries
            for (entry in entries) {
                if (entry.value == id) return entry.key
            }
            throw Exception("Should not happen")
        } else {
            val token = UUID.randomUUID().toString()
            SessionPool.sessions[token] = id
            return token
        }
    }

    fun closeSession(token: String){
        SessionPool.sessions.remove(token)
    }

    fun validateUser(token: String): User {
        val id = SessionPool.sessions[token] ?: throw UnauthorizedException()
        return getUserByID(id)
    }
}