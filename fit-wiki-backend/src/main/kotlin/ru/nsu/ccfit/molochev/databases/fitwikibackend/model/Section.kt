package ru.nsu.ccfit.molochev.databases.fitwikibackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "sections")
class Section(
        var name: String,
        @Id @GeneratedValue var id: UUID = UUID.randomUUID(),

        @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "parent_id")
        var parent: Section? = null,

        @JsonIgnore
        @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var children: MutableSet<Section> = mutableSetOf()
)