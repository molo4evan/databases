package ru.nsu.ccfit.molochev.databases.fitwikibackend.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.ForbiddenException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.exceptions.NotFoundException
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Section
import ru.nsu.ccfit.molochev.databases.fitwikibackend.repos.SectionRepository
import java.util.*

@Service
class SectionService {
    @Autowired
    private lateinit var sectionRepository: SectionRepository

    fun getSectionById(id: UUID): Section {
        val section = sectionRepository.findById(id)
        if (section is Section) return section
        throw NotFoundException(id)
    }

    fun getSubSections(id: UUID): List<Section>{
        val parent = sectionRepository.findById(id)
        if (parent is Section){
            return sectionRepository.findAllByParent(parent)
        } else {
            throw NotFoundException(id)
        }
    }

    fun getParent(id: UUID): Section? {
        val section = sectionRepository.findById(id)
        if (section is Section) return section.parent
        else throw NotFoundException(id)
    }

    fun getRootSections() =  sectionRepository.findAllByParentEquals(null)

    fun addSection(section: Section): Section{
        if (sectionRepository.existsById(section.id)){
            throw ForbiddenException()
        }
        return sectionRepository.save(section)
    }

    fun editSection(section: Section): Section{
        if (!sectionRepository.existsById(section.id)){
            throw NotFoundException(section.id)
        }
        return sectionRepository.save(section)
    }

    fun deleteSection(id: UUID){
        sectionRepository.deleteById(id)
    }
}