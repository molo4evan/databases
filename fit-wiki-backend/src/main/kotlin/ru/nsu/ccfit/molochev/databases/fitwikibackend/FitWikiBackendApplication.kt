package ru.nsu.ccfit.molochev.databases.fitwikibackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FitWikiBackendApplication

fun main(args: Array<String>) {
    runApplication<FitWikiBackendApplication>(*args)
}