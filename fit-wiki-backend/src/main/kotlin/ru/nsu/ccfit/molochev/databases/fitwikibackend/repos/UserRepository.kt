package ru.nsu.ccfit.molochev.databases.fitwikibackend.repos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.User
import java.util.*

interface UserRepository: JpaRepository<User, UUID> {
    fun findByUsername(username: String): Optional<User>

    fun existsByUsername(username: String): Boolean
}