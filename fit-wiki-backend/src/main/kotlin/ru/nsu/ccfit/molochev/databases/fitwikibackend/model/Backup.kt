package ru.nsu.ccfit.molochev.databases.fitwikibackend.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "backups")
class Backup (
        var name: String,
        var creation: Timestamp = Timestamp.from(Date().toInstant()),
        @Id @GeneratedValue
        var id: UUID = UUID.randomUUID()
)