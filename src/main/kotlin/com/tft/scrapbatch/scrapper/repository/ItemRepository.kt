package com.tft.scrapbatch.scrapper.repository


import com.tft.scrapbatch.scrapper.entity.Item
import org.springframework.data.mongodb.repository.MongoRepository

interface ItemRepository : MongoRepository<Item, String> {
    fun findAllBySeasonAndEngNameIn(season: String, engNames: List<String>): List<Item>
    fun findAllBySeason(season: String): List<Item>

}