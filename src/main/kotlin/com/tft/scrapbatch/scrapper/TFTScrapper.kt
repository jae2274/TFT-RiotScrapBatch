package com.tft.scrapbatch.scrapper

import com.tft.scrapbatch.scrapper.entity.Champion
import com.tft.scrapbatch.scrapper.entity.HtmlDoc
import com.tft.scrapbatch.scrapper.entity.Item
import com.tft.scrapbatch.scrapper.entity.Trait
import com.tft.scrapbatch.scrapper.support.HtmlProvider
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Consumer

@Component
class TFTScrapper(
    private val htmlProvider: HtmlProvider
) {

    val localeKrCookie = HtmlDoc.Cookie("locale", "ko_KR")
    val localeEnCookie = HtmlDoc.Cookie("locale", "en_us")

    fun getChampions(season: String): List<Champion> {


        val doc: Document =
            htmlProvider.getDoc("https://lolchess.gg/synergies/set$season", listOf(localeKrCookie))

        val champions = mutableListOf<Champion>()
        val championElements =
            doc.select("div.container > div.row:nth-child(1) .guide-synergy-table__synergy__champions a div")

        val tootipUrls = mutableSetOf<String>()

        for (element in championElements) {
            val tooltilUrl = element.attr("data-tooltip-url")

            if (tootipUrls.contains(tooltilUrl))
                continue

            tootipUrls.add(tooltilUrl)

            val imageUrl = element.select("img").attr("src")

            val championDoc =
                htmlProvider.getDoc(tooltilUrl, listOf(localeKrCookie))

            val championEngName = tooltilUrl.split("/").let { it[it.size - 1].split("_")[0] }

            val championName =
                championDoc.select(".p-2.text-left.font-size-11 div:nth-child(1) span.mr-2.align-middle").text()

            val costStr =
                championDoc.select(".p-2.text-left.font-size-11 div:nth-child(1) span.align-middle:not(.mr-2)")
                    .text()

            val cost = Integer.valueOf(costStr)

            val traits = mutableListOf<Trait>()
            val traitImages =
                championDoc.select(".p-2.text-left.font-size-11 div:nth-child(2) img.align-middle")
            val attachRange =
                championDoc.select(".p-2.text-left.font-size-11 div:nth-child(3) i.fa.fa-square-full").size

            for (traitImage in traitImages) {
                val synergyName = traitImage.attr("alt")
                val synergyEngName = traitImage.attr("src").split("/")
                    .let {
                        it[it.size - 1].split("_")[0]
                    }

                traits.add(
                    Trait(synergyName = synergyName, synergyEngName = synergyEngName)
                )
            }

            val skillName =
                championDoc.select(".p-2.text-left.font-size-11 div:nth-child(4) div.text-yellow").text()
            val skillExplanation = championDoc.select(".p-2.text-left.font-size-11 div:nth-child(5)").text()

            val powersByLevel = mutableListOf<Map<Int, Champion.PowerByLevel>>()

            for (element in championDoc.select(".p-2.text-left.font-size-11 div.d-block")) {
                val powerExplain = element.text()
                try {
                    val powerType =
                        powerExplain.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                            .trim { it <= ' ' }
                    val powerLevels =
                        powerExplain.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()[1].split("/".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()

                    val powerByLevel = mutableMapOf<Int, Champion.PowerByLevel>()

                    for (i in powerLevels.indices) {
                        val powerByLevel1 = Champion.PowerByLevel(
                            effectName = powerType,
                            effectPower = powerLevels[i].trim { it <= ' ' }
                        )

                        powerByLevel[i + 1] = powerByLevel1
                    }

                    powersByLevel.add(powerByLevel)
                } catch (e: ArrayIndexOutOfBoundsException) {
                    println(powerExplain)
                }
            }


            val explainMana =
                championDoc.select(".p-2.text-left.font-size-11 div:nth-child(4) div:not(.text-yellow)").text()

            var initMana = 0
            var maxMana = 0

            if (!explainMana.contains("없음")) {
                val s = explainMana.split(": ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val split =
                    s[1].split("/".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                try {
                    initMana = Integer.valueOf(split[0].replace("\\D".toRegex(), "").trim { it <= ' ' })
                    maxMana = Integer.valueOf(split[1].replace("\\D".toRegex(), "").trim { it <= ' ' })
                } catch (e: NumberFormatException) {
                    println(explainMana)
                }
            }

            val champion = Champion(
                championName = championName,
                championEngName = championEngName,
                cost = cost,
                traits = traits,
                attachRange = attachRange,
                skillName = skillName,
                skillExplanation = skillExplanation,
                powersByLevel = powersByLevel,
                initMana = initMana,
                maxMana = maxMana,
                season = season,
                imageUrl = imageUrl
            )

            champions.add(champion)
        }

        return champions
    }

    fun getItems(season: String): List<Item> {


        val doc: Document = htmlProvider.getDoc("https://lolchess.gg/items", listOf(localeKrCookie))


        val items = mutableListOf<Item>()

        for (itemDoc in doc.select(".guide-items-table > tbody > tr")) {
            val imageTag = itemDoc.select("td:nth-child(1) > img")
            val imageUrl = imageTag.attr("src")

            val tooltipUrl = imageTag.attr("data-tooltip-url")


            val tooltipDoc = htmlProvider.getDoc(tooltipUrl, listOf(localeKrCookie))
            val itemRoot: Elements = tooltipDoc.select("div.py-1")

            val itemName = itemRoot.select("div > p").text()
            val select = itemRoot.select("div > div")[0]
            val htmlTEst = select.html()

            val itemEffect = htmlTEst.replace("<br>", "")
            val itemSpec = itemRoot.select("div.text-gray.line-height-1").html().replace("<br>", "")
            val childItems: MutableList<String> = LinkedList()
            itemRoot.select("div.text-gray:not(.line-height-1) > img").forEach(Consumer { element: Element ->
                childItems.add(
                    element.attr("alt")
                )
            })


            val tooltipDocForEng = htmlProvider.getDoc(tooltipUrl, listOf(localeEnCookie))

            val itemEngName = tooltipDocForEng.select("div.py-1").select("div > p").text()

            val item: Item = Item(
                itemName = itemName,
                itemEngName = itemEngName,
                itemEffect = itemEffect,
                itemSpec = itemSpec,
                imageUrl = imageUrl,
                childItems = childItems,
                season = season
            )

            items.add(item)
        }

        return items
    }

}