package com.tft.scrapbatch.scrapper.entity

import com.querydsl.core.annotations.QueryEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Entity

@Entity
@QueryEntity
@Document
data class HtmlDoc(
    @Id
    var _id: String? = null,
    var url: String = "",
    var html: String = "",
    var cookies: List<Cookie> = listOf()
) {
    @Entity
    @QueryEntity
    data class Cookie(
        var name: String = "",
        var value: String = "",
    )
}
