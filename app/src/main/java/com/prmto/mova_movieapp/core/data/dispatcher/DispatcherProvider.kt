package com.prmto.mova_movieapp.core.data.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val IO: CoroutineDispatcher
    val Main: CoroutineDispatcher
    val Default: CoroutineDispatcher
}