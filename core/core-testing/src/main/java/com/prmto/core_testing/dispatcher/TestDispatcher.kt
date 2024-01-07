package com.prmto.core_testing.dispatcher

import com.prmto.core_domain.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcher : DispatcherProvider {
    override val IO: CoroutineDispatcher = UnconfinedTestDispatcher()
    override val Main: CoroutineDispatcher = UnconfinedTestDispatcher()
    override val Default: CoroutineDispatcher = UnconfinedTestDispatcher()
}