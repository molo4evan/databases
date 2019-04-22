package ru.nsu.ccfit.molochev.databases.fitwikibackend.control

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.BackupData
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.ForbiddenException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Backup
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.BackupService
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.UserService
import java.util.*

@RestController
@RequestMapping("backups")
class BackupController: CheckingController() {
    @Autowired
    private lateinit var backupService: BackupService

    @GetMapping("")
    fun getBackups(@RequestHeader(value = "WWW-Authenticate") token: String): List<Backup>{
        checkUserIsAdmin(token)
        return backupService.getBackups()
    }

    @PostMapping("")
    fun backup(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @RequestBody name: String
    ): Backup{
        checkUserIsAdmin(token)

        //TODO: make backup

        return backupService.backup(name)
    }

    @PostMapping("/restore/{id}")
    fun restore(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @PathVariable id: UUID
    ) {
        checkUserIsAdmin(token)

        val backup = backupService.getBackup(id)

        //TODO: make restore
    }

    @PostMapping("/delete/{id}")
    fun deleteBackup(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @PathVariable id: UUID
    ) {
        checkUserIsAdmin(token)
        backupService.deleteBackup(id)
    }
}