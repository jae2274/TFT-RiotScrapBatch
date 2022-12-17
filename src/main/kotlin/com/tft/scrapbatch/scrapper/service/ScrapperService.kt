package com.tft.scrapbatch.scrapper.service

import com.tft.scrapbatch.scrapper.TFTScrapper
import com.tft.scrapbatch.scrapper.entity.Champion
import com.tft.scrapbatch.scrapper.repository.ChampionRepository
import com.tft.scrapbatch.scrapper.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScrapperService {
    @Autowired
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var championRepository: ChampionRepository

    @Autowired
    lateinit var tftScrapper: TFTScrapper

    fun saveChampionInfos(season: String) {
        val championNames: List<Champion> = tftScrapper.getChampions(season)

        for (championName in championNames) {
            println(championName)
        }

        championRepository.saveAll(championNames)
    }

    fun saveItemInfos(season: String) {
        val items = tftScrapper.getItems(season)

        for (item in items) {
            println(item)
        }
        itemRepository.saveAll(items)
    }
}