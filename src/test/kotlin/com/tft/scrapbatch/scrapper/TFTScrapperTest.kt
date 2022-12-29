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
//    @Disabled
    fun test() {
        service.saveChampionInfos(season)
        service.setChampionId(season)
    }

    @Test
//    @Disabled
    fun test2() {
        service.saveItemInfos(season)
        service.setItemId(season)
    }

}