package com.tft.scrapbatch.scrapper.service

import com.tft.scrapbatch.scrapper.TFTScrapper
import com.tft.scrapbatch.scrapper.entity.Champion
import com.tft.scrapbatch.scrapper.repository.ChampionRepository
import org.springframework.stereotype.Service

@Service
class ChampionService(
        val championRepository: ChampionRepository,
        val tftScrapper: TFTScrapper,
) {
    fun saveChampionInfos(season: String) {
        val newChampions: List<Champion> = tftScrapper.getChampions(season)

        val existedChampions: List<Champion> =
                championRepository.findAllBySeasonAndEngNameIn(season, newChampions.map { it.engName })

        for (newChampion in newChampions) {
            val existedChampion: Champion? =
                    existedChampions.firstOrNull { it.engName == newChampion.engName }

            existedChampion?.let { newChampion._id = it._id }
        }

        championRepository.saveAll(newChampions)
    }


}