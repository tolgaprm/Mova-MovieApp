package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider.WatchProviderType
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.link_extractor.BaseLinkExtractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class GetProviderLinksUseCase @Inject constructor(
    @Named("streamProvider") private val streamProviderLinkExtractor: BaseLinkExtractor,
    @Named("buyProvider") private val buyProviderLinkExtractor: BaseLinkExtractor,
    @Named("rentProvider") private val rentProviderLinkExtractor: BaseLinkExtractor,
) {

    suspend fun invoke(url: String): Map<WatchProviderType, List<String>> {

        return withContext(Dispatchers.IO) {
            val streamProviderLinks = async {
                streamProviderLinkExtractor.getWatchProvidersLinks(url = url)
            }
            val buyProviderLinks = async {
                buyProviderLinkExtractor.getWatchProvidersLinks(url = url)
            }
            val rentProviderLinks = async {
                rentProviderLinkExtractor.getWatchProvidersLinks(url = url)
            }

            mapOf(
                WatchProviderType.STREAM to streamProviderLinks.await(),
                WatchProviderType.BUY to buyProviderLinks.await(),
                WatchProviderType.RENT to rentProviderLinks.await()
            )
        }
    }
}