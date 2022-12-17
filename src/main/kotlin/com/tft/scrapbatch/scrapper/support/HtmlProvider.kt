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
    fun getHtml(url: String): String {
        return htmlDocRepository.findByUrl(url)?.html
            ?: let {
                val html = Jsoup.connect(url).cookie("locale", "ko_KR").get().html()
                htmlDocRepository.save(HtmlDoc(url = url, html = html))
                Thread.sleep(2000)
                html
            }
    }

    fun getDoc(url: String): Document {
        return Jsoup.parse(getHtml(url))
    }
}