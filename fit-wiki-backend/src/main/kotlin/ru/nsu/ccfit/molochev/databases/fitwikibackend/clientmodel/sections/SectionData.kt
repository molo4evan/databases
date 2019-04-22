package ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.sections

import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Section
import java.util.*

class SectionData (
        var id: UUID,
        var name: String,
        var parentId: UUID?
) {
    constructor(section: Section): this(section.id, section.name, section.parent?.id)
}