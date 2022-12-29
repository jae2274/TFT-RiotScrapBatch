package com.tft.scrapbatch.scrapper.repository


import com.tft.scrapbatch.scrapper.entity.ItemSet
import org.springframework.data.mongodb.repository.MongoRepository

interface ItemSetRepository : MongoRepository<ItemSet, String> {

}