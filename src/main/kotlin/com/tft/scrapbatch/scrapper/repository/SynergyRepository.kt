package com.tft.scrapbatch.scrapper.repository

import com.tft.scrapbatch.scrapper.entity.Synergy
import org.springframework.data.mongodb.repository.MongoRepository

interface SynergyRepository : MongoRepository<Synergy, String> {
    fun findAllBySeasonAndEngNameIn(season: String, engNames: List<String>): List<Synergy>
}