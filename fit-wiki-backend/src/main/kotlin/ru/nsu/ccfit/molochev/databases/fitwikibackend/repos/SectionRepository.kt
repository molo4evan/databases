package ru.nsu.ccfit.molochev.databases.fitwikibackend.repos

import org.springframework.data.jpa.repository.JpaRepository
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Section
import java.util.*

interface SectionRepository: JpaRepository<Section, UUID> {
    fun findAllByParent(parent: Section): List<Section>

    fun findAllByParentEquals(parent: Section?): List<Section>

    fun findByName(name: String): Optional<Section>
}