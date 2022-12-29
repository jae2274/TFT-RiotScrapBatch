package com.tft.scrapbatch.scrapper.repository

import com.tft.scrapbatch.scrapper.entity.HtmlDoc
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class HtmlDocRepositoryTest {
    @Autowired
    lateinit var htmlDocRepository: HtmlDocRepository

    @Test
    fun test() {
        var findByUrlAndCookies = htmlDocRepository.findByUrlAndCookies(
            "https://lolchess.gg/tooltip/item/set8/2812",
            listOf(HtmlDoc.Cookie("locale", "ko_KR"))
        )

        assert(findByUrlAndCookies == null)
        println(findByUrlAndCookies)



        findByUrlAndCookies = htmlDocRepository.findByUrlAndCookies(
            "https://lolchess.gg/tooltip/item/set8/2812",
            listOf()
        )

        assert(findByUrlAndCookies == null)
        println(findByUrlAndCookies)



        findByUrlAndCookies = htmlDocRepository.findByUrlAndCookies(
            "https://lolchess.gg/tooltip/item/set8/2812",
            listOf(
                HtmlDoc.Cookie("locale", "ko_KR"),
                HtmlDoc.Cookie(
                    "XSRF-TOKEN",
                    "eyJpdiI6InZEejBKYlRHWGdGSmVZZ1pSNnV1RGc9PSIsInZhbHVlIjoiTTFMajBQaDBNdFYzRXcwajJtczJRWEdiYzZkSkVBVUlFbnRKQWk0TjI2WTU5ZlVSRW9vMWd5bWdlK0ZOSHhyWlUzbkxhK0Nod0xQOUFCdFdBZXB5YkZ2QmN1VTNYMDV3aHVSRndvL1dDVkloL3lBMWt2bjdsTkdleEU0aUlPNUQiLCJtYWMiOiIxNzdhOTU0ZGM0M2FmM2Q3MzAxNmJhY2NlMGFjNjM5YTNmYzEyZjAzNzVkN2UwMzU3YjdjOWFiNDRhZWVhMmY2IiwidGFnIjoiIn0%3D"
                )
            )
        )

        assert(findByUrlAndCookies != null)
        println(findByUrlAndCookies)



        findByUrlAndCookies = htmlDocRepository.findByUrlAndCookies(
            "https://lolchess.gg/tooltip/item/set8/2812",
            listOf(HtmlDoc.Cookie("locale", "en_us"))
        )

        assert(findByUrlAndCookies == null)
        println(findByUrlAndCookies)
    }
}