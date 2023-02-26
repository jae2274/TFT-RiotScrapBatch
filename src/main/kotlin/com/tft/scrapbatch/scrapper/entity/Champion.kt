package com.tft.scrapbatch.scrapper.entity

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id


@Document
data class Champion(
        @Id
        var _id: String? = null,
        val championName: String = "",
        val cost: Int = 0,
        val traits: List<Trait> = listOf(),
        val attachRange: Int = 0,
        val skillName: String = "",
        val skillExplanation: String = "",
        val powersByLevel: List<Map<Int, PowerByLevel>> = listOf(),
        val initMana: Int = 0,
        val maxMana: Int = 0,
        val imageUrl: String = "",
        val season: String = "",
        override val isFixed: Boolean = false,
        override val engName: String,
) : TFTData {
    data class PowerByLevel(
            val effectName: String = "",
            val effectPower: String = "",
    )
}