package ru.nsu.ccfit.molochev.databases.fitwikibackend.repos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.PostingID
import ru.nsu.ccfit.molochev.databases.fitwikibackend.model.Rating

interface RatingRepository: JpaRepository<Rating, PostingID> {
}