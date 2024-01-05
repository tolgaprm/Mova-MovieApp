package com.prmto.core_domain.repository

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {


    fun observe(): Flow<Status>

    enum class Status {
        Avaliable,
        Unavaliable,
        Losing,
        Lost
    }
}

fun ConnectivityObserver.Status.isAvaliable(): Boolean {
    return this == ConnectivityObserver.Status.Avaliable
}