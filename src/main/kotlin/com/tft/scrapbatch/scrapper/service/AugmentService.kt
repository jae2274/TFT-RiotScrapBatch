package com.tft.scrapbatch.scrapper.service

import com.tft.scrapbatch.scrapper.TFTScrapper
import com.tft.scrapbatch.scrapper.entity.Augment
import com.tft.scrapbatch.scrapper.repository.AugmentRepository
import org.springframework.stereotype.Service

@Service
class AugmentService(
        val augmentRepository: AugmentRepository,
        val tftScrapper: TFTScrapper,
) {

    fun saveAugmentInfos(season: String) {
        val newAugments = tftScrapper.getAugments(season)

        val existedAugments: List<Augment> =
                augmentRepository.findAllBySeasonAndEngNameIn(season, newAugments.map { it.engName })

        for (newAugment in newAugments) {
            val existedItem: Augment? = existedAugments.firstOrNull { it.engName == newAugment.engName }

            existedItem?.let { newAugment._id = it._id }
        }

        augmentRepository.saveAll(newAugments)
    }
}