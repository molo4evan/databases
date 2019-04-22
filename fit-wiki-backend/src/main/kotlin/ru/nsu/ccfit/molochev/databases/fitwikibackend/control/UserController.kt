package ru.nsu.ccfit.molochev.databases.fitwikibackend.control

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.users.*
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.ForbiddenException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.NotAcceptableException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.User
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.User.Role.*
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.UserService
import java.util.*

@RestController
@RequestMapping("users")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/register")
    fun register(@RequestBody registrationData: RegistrationData): LoginResponse {
        if (userService.containsUser(registrationData.username)) throw NotAcceptableException()

        var user = User(
                username = registrationData.username,
                password = registrationData.password,
                photoURL = registrationData.photoURL
        )
        user = userService.addUser(user)
        val token = userService.initSession(user.id)
        return LoginResponse(user.id, token)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        val user = userService.loginUser(request.username, request.password)
        val token = userService.initSession(user.id)
        return LoginResponse(user.id, token)
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader(value = "WWW-Authenticate") token: String) {
        userService.closeSession(token)
    }

    @GetMapping("")
    fun getUsers(@RequestHeader(value = "WWW-Authenticate") token: String): List<UserData> {
        val user = userService.validateUser(token)

        if (user.role != MODERATOR && user.role != ADMIN) {
            throw ForbiddenException()
        }

        return userService.getUsers().map(::UserData)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID): UserData {
        val user = userService.getUserByID(id)
        return UserData(user)
    }

    @GetMapping("/me")
    fun getUser(@RequestHeader(value = "WWW-Authenticate") token: String): MyselfData {
        return MyselfData(userService.validateUser(token))
    }

    @PostMapping("/{id}/role")
    fun changeUserStatus(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @PathVariable id: UUID,
            @RequestBody newStatus: User.Role
    ): UserData {
        if (newStatus == ADMIN) throw ForbiddenException()
        val changer = userService.validateUser(token)
        if (!changer.isPrivileged()) throw ForbiddenException()
        val user = userService.getUserByID(id)
        if (!user.roleCanBeChanged(newStatus)) throw ForbiddenException()
        user.role = newStatus
        return UserData(userService.editUser(user))
    }

    @PostMapping("/{id}/delete")
    fun deleteUser(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @PathVariable id: UUID
    ) {
        val deleter = userService.validateUser(token)
        val user = userService.getUserByID(id)
        if (deleter.role == ADMIN || deleter.id == user.id){
            userService.deleteUser(id)
        } else throw ForbiddenException()
    }

    @PostMapping("/me")
    fun editUser(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @RequestBody registrationData: RegistrationData
    ): UserData {
        val user = userService.validateUser(token)
        user.username = registrationData.username
        user.password = registrationData.password
        user.photoURL = registrationData.photoURL
        return UserData(userService.editUser(user))
    }
}