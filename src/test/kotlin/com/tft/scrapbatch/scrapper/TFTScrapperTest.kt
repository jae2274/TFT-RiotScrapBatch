package com.tft.scrapbatch.scrapper

import com.tft.scrapbatch.scrapper.service.ScrapperService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TFTScrapperTest {

    @Autowired
    lateinit var service: ScrapperService

    val season: String = "8"

    @Test
    fun test() {
        service.saveChampionInfos(season)
    }

    @Test
    fun test2() {
        service.saveItemInfos(season)
    }

}