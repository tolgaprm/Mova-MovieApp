package com.prmto.mova_movieapp.core.repository

import com.prmto.mova_movieapp.core.domain.repository.ConnectivityObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNetworkConnectivityObserver : ConnectivityObserver {

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return flow {
            emit(ConnectivityObserver.Status.Unavaliable)
            delay(500)
            emit(ConnectivityObserver.Status.Avaliable)
        }
    }

}