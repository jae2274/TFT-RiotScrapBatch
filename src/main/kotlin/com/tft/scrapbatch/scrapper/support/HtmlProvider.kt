package com.tft.scrapbatch.scrapper.support

import com.tft.scrapbatch.scrapper.entity.HtmlDoc
import com.tft.scrapbatch.scrapper.repository.HtmlDocRepository
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class HtmlProvider(
    private val htmlDocRepository: HtmlDocRepository
) {
    fun getHtml(url: String, cookies: List<HtmlDoc.Cookie>): String {
        return htmlDocRepository.findByUrlAndCookies(url, cookies)?.html
            ?: let {
                val html = Jsoup.connect(url)
                    .cookies(
                        cookies.associate { Pair(it.name, it.value) }
                    )
                    .get().html()
                htmlDocRepository.save(HtmlDoc(url = url, html = html, cookies = cookies))
                Thread.sleep(2000)
                html
            }
    }

    fun getDoc(url: String, cookies: List<HtmlDoc.Cookie>): Document {
        return Jsoup.parse(getHtml(url, cookies))
    }
}