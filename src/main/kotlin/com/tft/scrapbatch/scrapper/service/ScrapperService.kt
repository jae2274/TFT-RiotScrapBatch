package com.tft.scrapbatch.scrapper.service

import com.tft.scrapbatch.scrapper.TFTScrapper
import com.tft.scrapbatch.scrapper.entity.Champion
import com.tft.scrapbatch.scrapper.entity.CharacterSet
import com.tft.scrapbatch.scrapper.entity.Item
import com.tft.scrapbatch.scrapper.entity.ItemSet
import com.tft.scrapbatch.scrapper.repository.ChampionRepository
import com.tft.scrapbatch.scrapper.repository.CharacterSetRepository
import com.tft.scrapbatch.scrapper.repository.ItemRepository
import com.tft.scrapbatch.scrapper.repository.ItemSetRepository
import com.tft.scrapbatch.scrapper.support.CompareStringUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ScrapperService {
    @Autowired
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var championRepository: ChampionRepository

    @Autowired
    lateinit var characterSetRepository: CharacterSetRepository

    @Autowired
    lateinit var itemSetRepository: ItemSetRepository

    @Autowired
    lateinit var tftScrapper: TFTScrapper

    fun saveChampionInfos(season: String) {
        val newChampions: List<Champion> = tftScrapper.getChampions(season)

        val existedChampions: List<Champion> =
            championRepository.findAllBySeasonAndChampionEngNameIn(season, newChampions.map { it.championEngName })

        for (newChampion in newChampions) {
            val existedChampion: Champion? =
                existedChampions.firstOrNull { it.championEngName == newChampion.championEngName }

            existedChampion?.let { newChampion._id = it._id }
        }

        championRepository.saveAll(newChampions)
    }

    fun saveItemInfos(season: String) {
        val newItems = tftScrapper.getItems(season)

        val existedItems: List<Item> =
            itemRepository.findAllBySeasonAndItemEngNameIn(season, newItems.map { it.itemEngName })

        for (newItem in newItems) {
            val existedItem: Item? = existedItems.firstOrNull { it.itemEngName == newItem.itemEngName }

            existedItem?.let { newItem._id = it._id }
        }

        itemRepository.saveAll(newItems)
    }

    fun setChampionId(season: String): Boolean {
        val champions: List<Champion> = championRepository.findAllBySeason(season)

        val tftSeason: String = season.split(".")
            .let {
                if (it.size == 1)
                    "TFTSet${it[0]}"
                else
                    "TFTSet${it[0]}_2"
            }

        val characterSet: CharacterSet? = characterSetRepository.findByIdOrNull(tftSeason)

        return if (characterSet != null) {
            matchChampionEngNames(champions, characterSet.characters)

            val notCompletedMatches = champions.filter { it.similarity < 1 }.reversed()
            val notMatchedNames = notCompletedMatches.map { checkNotNull(it.championId) }.toMutableList()

            matchChampionEngNames(notCompletedMatches, notMatchedNames)

            characterSet.isProcessed = true
            characterSetRepository.save(characterSet)
            championRepository.saveAll(champions)

            true
        } else {
            false
        }
    }

    private fun matchChampionEngNames(champions: List<Champion>, championIds: List<String>) {
        val notMatchedNames = championIds.toMutableList()

        for (champion in champions) {
            val mostSimilarityString: String = CompareStringUtil.getMostSimilarity(
                champion.championEngName,
                notMatchedNames.map { it.split("_")[1] }
            )

            val howMuchSimilarity: Double =
                CompareStringUtil.similarity(champion.championEngName, mostSimilarityString)

            val realEngName: String = notMatchedNames.first { it.split("_")[1] == mostSimilarityString }
            champion.championId = realEngName
            champion.similarity = howMuchSimilarity

            notMatchedNames.remove(realEngName)
        }
    }


    fun setItemId(season: String): Boolean {
        val items: List<Item> = itemRepository.findAllBySeason(season)

        val tftSeason: String = season.split(".")
            .let {
                if (it.size == 1)
                    "TFTSet${it[0]}"
                else
                    "TFTSet${it[0]}_2"
            }

        val itemSet: ItemSet? = itemSetRepository.findByIdOrNull(tftSeason)

        return if (itemSet != null) {
            matchItemEngNames(items, itemSet.items)

            var maxSimiliarity = 1.0

            for (i in 0..10) {
                val notCompletedMatches = items.filter { it.similarity < maxSimiliarity }.reversed()
                val notMatchedNames = notCompletedMatches.map { it.itemId }.filterNotNull().toMutableList()

                matchItemEngNames(notCompletedMatches, notMatchedNames)

                maxSimiliarity -= 0.1
            }

            itemSet.isProcessed = true
            itemSetRepository.save(itemSet)
            itemRepository.saveAll(items)

            true
        } else {
            false
        }
    }

    private fun matchItemEngNames(items: List<Item>, itemIds: List<String>) {
        val notMatchedNames = itemIds.toMutableList()

        for (item in items) {
            if (item.isFixed)
                continue

            val mostSimilarityString: String = CompareStringUtil.getMostSimilarity(
                item.itemEngName,
                notMatchedNames.map { it.split("_Item_")[1] }
            )

            val howMuchSimilarity: Double =
                CompareStringUtil.similarity(
                    item.itemEngName.replace("’", "").replace("//", "").replace("-", "").replace("'", "")
                        .replace(".", "")
                        .replace(" ", ""),
                    mostSimilarityString
                )

            val realItemId: String? = notMatchedNames.firstOrNull { it.split("_Item_")[1] == mostSimilarityString }
            item.itemId = realItemId
            item.similarity = howMuchSimilarity
            item.isFixed = howMuchSimilarity >= 0.99

            notMatchedNames.remove(realItemId)
        }
    }
}