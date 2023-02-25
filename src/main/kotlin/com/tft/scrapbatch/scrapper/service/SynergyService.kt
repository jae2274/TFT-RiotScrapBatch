package com.tft.scrapbatch.scrapper.service

import com.tft.scrapbatch.scrapper.TFTScrapper
import com.tft.scrapbatch.scrapper.entity.Synergy
import com.tft.scrapbatch.scrapper.repository.SynergyRepository
import org.springframework.stereotype.Service

@Service
class SynergyService(
        val synergyRepository: SynergyRepository,
        val tftScrapper: TFTScrapper,
) {
    fun saveSynergyInfos(season: String) {
        val newSynergies = tftScrapper.getSynergies(season)

        val existedSynergies: List<Synergy> =
                synergyRepository.findAllBySeasonAndEngNameIn(season, newSynergies.map { it.engName })

        for (newSynergy in newSynergies) {
            val existedSynergy: Synergy? = existedSynergies.firstOrNull { it.name == newSynergy.name }

            existedSynergy?.let { newSynergy._id = it._id }
        }

        synergyRepository.saveAll(newSynergies)
    }
}