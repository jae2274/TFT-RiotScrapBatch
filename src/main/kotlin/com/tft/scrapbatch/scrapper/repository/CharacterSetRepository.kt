package com.tft.scrapbatch.scrapper.repository


import com.tft.scrapbatch.scrapper.entity.CharacterSet
import org.springframework.data.mongodb.repository.MongoRepository

interface CharacterSetRepository : MongoRepository<CharacterSet, String> {
}