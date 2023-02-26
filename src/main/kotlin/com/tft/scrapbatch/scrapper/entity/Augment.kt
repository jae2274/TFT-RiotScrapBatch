package com.tft.scrapbatch.scrapper.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
data class Augment(

        @Id
        var _id: String? = null,
        val season: String,
        val name: String,
        val augmentId: String? = null,
        val tier: Int,
        val tierName: String,
        val desc: String,
        val imageUrl: String,
        override val isFixed: Boolean = false,
        override val engName: String,
) : TFTData
