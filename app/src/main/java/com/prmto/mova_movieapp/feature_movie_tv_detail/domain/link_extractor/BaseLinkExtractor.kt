package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.link_extractor

import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.HtmlLinkExtractor
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import timber.log.Timber
import java.io.IOException

abstract class BaseLinkExtractor : HtmlLinkExtractor {

    protected abstract val parentElements: String
    override suspend fun getWatchProvidersLinks(url: String): List<String> {
        return try {
            val doc = Jsoup.connect(url).get()
            getLinksFor(parentElements = doc.select(parentElements))
        } catch (e: IOException) {
            Timber.e(e)
            emptyList()
        }
    }

    private fun getLinksFor(parentElements: Elements): List<String> {
        val linkElements =
            parentElements.first()?.parent()?.select(".ott_filter_best_price a[href]")

        return linkElements?.let {
            val links = it.map { element ->
                element.attr("href")
            }
            links
        } ?: emptyList()
    }
}
