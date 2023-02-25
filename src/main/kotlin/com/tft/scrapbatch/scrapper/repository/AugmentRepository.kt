package com.tft.scrapbatch.scrapper.repository


import com.tft.scrapbatch.scrapper.entity.Augment
import org.springframework.data.mongodb.repository.MongoRepository

interface AugmentRepository : MongoRepository<Augment, String> {
    fun findAllBySeasonAndEngNameIn(season: String, engNames: List<String>): List<Augment>

}