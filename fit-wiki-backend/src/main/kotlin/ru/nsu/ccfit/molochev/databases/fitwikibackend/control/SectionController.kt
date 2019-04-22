package ru.nsu.ccfit.molochev.databases.fitwikibackend.control

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.sections.NewSectionData
import ru.nsu.ccfit.molochev.databases.fitwikibackend.clientmodel.sections.SectionData
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Section
import ru.nsu.ccfit.molochev.databases.fitwikibackend.services.SectionService
import java.util.*

@RestController
@RequestMapping("sections")
class SectionController: CheckingController() {
    @Autowired
    private lateinit var sectionService: SectionService

    @GetMapping("")
    fun getRootSections(): List<SectionData>{
        return  sectionService.getRootSections().map(::SectionData)
    }

    @GetMapping("/{id}")
    fun getSection(@PathVariable id: UUID): SectionData {
        return SectionData(sectionService.getSectionById(id))
    }

    @GetMapping("/{id}/subsections")
    fun getSubSections(@PathVariable id: UUID): List<SectionData> {
        val section = sectionService.getSectionById(id)
        return section.children.map(::SectionData)
    }

    @GetMapping("/{id}/parent")
    fun getParent(@PathVariable id: UUID): SectionData? {
        return sectionService.getParent(id)?.let { SectionData(it) }
    }

    @PostMapping("")
    fun addSection(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @RequestBody sectionRequest: NewSectionData
    ): SectionData {
        checkUserIsAdmin(token)
        val parent = sectionRequest.parent?.let(sectionService::getParent)
        val section = Section(name = sectionRequest.name, parent = parent)
        return SectionData(sectionService.addSection(section))
    }

    @PostMapping("{id}")
    fun editSectionName(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @PathVariable id: UUID,
            @RequestBody name: String
    ): SectionData {
        checkUserIsAdmin(token)
        val section = sectionService.getSectionById(id)
        section.name = name
        return SectionData(sectionService.editSection(section))
    }

    @PostMapping("{id}/delete")
    fun deleteSection(
            @RequestHeader(value = "WWW-Authenticate") token: String,
            @PathVariable id: UUID
    ) {
        checkUserIsAdmin(token)
        sectionService.deleteSection(id)
    }
}