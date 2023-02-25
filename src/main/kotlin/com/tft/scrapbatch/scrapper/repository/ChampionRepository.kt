package com.tft.scrapbatch.scrapper.repository


import com.tft.scrapbatch.scrapper.entity.Champion
import org.springframework.data.mongodb.repository.MongoRepository

interface ChampionRepository : MongoRepository<Champion, String> {
    fun findAllBySeasonAndEngNameIn(season: String, engNames: List<String>): List<Champion>
    fun findAllBySeason(season: String): List<Champion>
}