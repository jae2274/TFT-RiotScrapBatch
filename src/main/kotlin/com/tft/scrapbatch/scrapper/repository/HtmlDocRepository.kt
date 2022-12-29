package com.tft.scrapbatch.scrapper.repository


import com.tft.scrapbatch.scrapper.entity.HtmlDoc
import com.tft.scrapbatch.scrapper.entity.QHtmlDoc.htmlDoc
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor


interface HtmlDocRepository : MongoRepository<HtmlDoc, String>, QuerydslPredicateExecutor<HtmlDoc> {
    fun findByUrl(url: String): HtmlDoc?

    fun findByUrlAndCookies(
        url: String,
        cookies: List<HtmlDoc.Cookie>
    ): HtmlDoc? {
        val htmlDocsByUrl: Iterable<HtmlDoc> = findAll(htmlDoc.url.eq(url))


        return htmlDocsByUrl.firstOrNull { htmlDoc: HtmlDoc ->
            cookies.size == htmlDoc.cookies.size && cookies.toSet() == htmlDoc.cookies.toSet()
        }
    }
}