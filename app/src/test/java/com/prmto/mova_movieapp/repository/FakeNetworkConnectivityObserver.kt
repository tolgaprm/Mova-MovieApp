package com.prmto.mova_movieapp.repository

import com.prmto.mova_movieapp.domain.repository.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNetworkConnectivityObserver : ConnectivityObserver {

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return flow {
            emit(ConnectivityObserver.Status.Avaliable)
        }
    }

}