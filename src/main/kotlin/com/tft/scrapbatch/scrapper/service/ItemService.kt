package com.tft.scrapbatch.scrapper.service

import com.tft.scrapbatch.scrapper.TFTScrapper
import com.tft.scrapbatch.scrapper.entity.Item
import com.tft.scrapbatch.scrapper.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService(
        val itemRepository: ItemRepository,
        val tftScrapper: TFTScrapper,
) {
    fun saveItemInfos(season: String) {
        val newItems = tftScrapper.getItems(season)

        val existedItems: List<Item> =
                itemRepository.findAllBySeasonAndEngNameIn(season, newItems.map { it.engName })

        for (newItem in newItems) {
            val existedItem: Item? = existedItems.firstOrNull { it.engName == newItem.engName }

            existedItem?.let { newItem._id = it._id }
        }

        itemRepository.saveAll(newItems)
    }


}