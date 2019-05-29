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
){
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Backup

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id.hashCode()
        }
}