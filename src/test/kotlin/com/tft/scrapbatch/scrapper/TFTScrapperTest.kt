package com.tft.scrapbatch.scrapper

import com.tft.scrapbatch.scrapper.service.AugmentService
import com.tft.scrapbatch.scrapper.service.ChampionService
import com.tft.scrapbatch.scrapper.service.ItemService
import com.tft.scrapbatch.scrapper.service.SynergyService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TFTScrapperTest {

    @Autowired
    lateinit var itemService: ItemService

    @Autowired
    lateinit var championService: ChampionService

    @Autowired
    lateinit var synergyService: SynergyService

    @Autowired
    lateinit var augmentService: AugmentService

    val season: String = "8"

    @Test
//    @Disabled
    fun test() {
        championService.saveChampionInfos(season)
    }

    @Test
//    @Disabled
    fun test2() {
        itemService.saveItemInfos(season)
    }

    @Test
//    @Disabled
    fun test3() {
        synergyService.saveSynergyInfos(season)
    }

    @Test
    fun test4() {
        augmentService.saveAugmentInfos(season)
    }
}