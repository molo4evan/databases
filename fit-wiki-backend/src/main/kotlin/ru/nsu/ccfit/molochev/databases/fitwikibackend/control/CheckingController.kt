package ru.nsu.ccfit.molochev.databases.fitwikibackend.control

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.ForbiddenException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.User
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.UserService

@Component
abstract class CheckingController {
    @Autowired
    private lateinit var userService: UserService

    protected fun checkUserIsAdmin(token: String){
        if (!userService.validateUser(token).isAdmin()) throw ForbiddenException()
    }

    protected fun checkUserIsPrivileged(token: String){
        if (!userService.validateUser(token).isPrivileged()) throw ForbiddenException()
    }
    
    protected fun checkUserIsBanned(token: String){
        if (userService.validateUser(token).isBanned()) throw ForbiddenException()
    }

    protected fun getUser(token: String): User {
        return userService.validateUser(token)
    }
}