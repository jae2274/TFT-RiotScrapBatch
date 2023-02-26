package com.tft.scrapbatch.scrapper.entity

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document
data class Item(
        @Id
        var _id: String? = null,
        val itemName: String = "",
        val itemEffect: String = "",
        val itemSpec: String = "",
        val imageUrl: String = "",
        val childItems: List<String> = listOf(),
        val season: String = "",
        val itemId: String? = null,
        override val isFixed: Boolean = false,
        override val engName: String,
        val engName2: String = "",
) : TFTData