package ru.nsu.ccfit.molochev.databases.fitwikibackend.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.NotFoundException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Backup
import ru.nsu.ccfit.molochev.databases.fitwikibackend.repos.BackupRepository
import java.util.*

@Service
class BackupService {
    @Autowired
    private lateinit var backupRepository: BackupRepository

    fun getBackups() = backupRepository.findAll().toList()

    fun backup(name: String): Backup {
        val backup = Backup(name)
        return backupRepository.save(backup)
    }

    fun getBackup(id: UUID): Backup{
        val backup = backupRepository.findById(id)
        if (!backup.isPresent) throw NotFoundException(id)
        return backup.get()
    }

    fun deleteBackup(id: UUID){
        backupRepository.deleteById(id)
    }
}