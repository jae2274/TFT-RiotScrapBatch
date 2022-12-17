package com.tft.scrapbatch.scrapper.repository


import com.tft.scrapbatch.scrapper.entity.HtmlDoc
import org.springframework.data.mongodb.repository.MongoRepository

interface HtmlDocRepository : MongoRepository<HtmlDoc, String> {
    fun findByUrl(url: String): HtmlDoc?
}