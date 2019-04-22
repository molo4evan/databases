package ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions

import java.util.*

class NotFoundException(message: String): Exception(message){
    constructor(id: UUID): this(id.toString())
}

class UnauthorizedException: Exception()

class NotAcceptableException: Exception()

class ForbiddenException: Exception()