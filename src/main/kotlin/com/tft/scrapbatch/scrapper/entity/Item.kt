package com.tft.scrapbatch.scrapper.entity

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document
data class Item(
        @Id
        var _id: String? = null,
        var itemName: String = "",
        var itemEffect: String = "",
        var itemSpec: String = "",
        var imageUrl: String = "",
        var childItems: List<String> = listOf(),
        var season: String = "",
        var itemId: String? = null,
        var isFixed: Boolean = false,
        override val engName: String,
) : TFTData