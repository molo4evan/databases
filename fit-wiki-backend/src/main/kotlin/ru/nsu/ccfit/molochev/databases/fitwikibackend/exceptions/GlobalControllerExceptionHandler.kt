package ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions

import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(NotAcceptableException::class)
    fun handleNotAccepted(){}

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(){}

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(){}

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbidden(){}
}