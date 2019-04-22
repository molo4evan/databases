package ru.nsu.ccfit.molochev.databases.fitwikibackend.repos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Backup
import java.util.*

interface BackupRepository: JpaRepository<Backup, UUID> {
}