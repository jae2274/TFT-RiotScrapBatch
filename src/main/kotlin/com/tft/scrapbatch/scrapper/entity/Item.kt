package com.tft.scrapbatch.scrapper.entity

import com.querydsl.core.annotations.QueryEntity
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@QueryEntity
@Document
data class Item(
    @Id
    var _id: String? = null,
    var itemName: String = "",
    var itemEngName: String = "",
    var itemId: String? = null,
    var itemEffect: String = "",
    var itemSpec: String = "",
    var imageUrl: String = "",
    var childItems: List<String> = listOf(),
    var season: String = "",
)