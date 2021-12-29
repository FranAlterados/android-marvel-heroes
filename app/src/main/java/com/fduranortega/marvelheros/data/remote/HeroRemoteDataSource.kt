package com.fduranortega.marvelheros.data.remote

import com.fduranortega.marvelheros.data.model.bo.HeroBO
import com.fduranortega.marvelheros.data.model.mapper.toBO
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface HeroRemoteDataSource {
    suspend fun getHeroList(page: Int): Flow<List<HeroBO>>
    suspend fun getHero(id: Int): Flow<HeroBO?>
}

class HeroRemoteDataSourceImpl @Inject constructor(
    private val heroClient: HeroClient
) : HeroRemoteDataSource {
    override suspend fun getHeroList(page: Int) = flow {
        val response = heroClient.fetchHeroList(page)
        response
            .suspendOnSuccess {
                emit(data.toBO())
            }
            .suspendOnError {
                emit(emptyList<HeroBO>())
            }
            .suspendOnException {
                emit(emptyList<HeroBO>())
            }
    }

    override suspend fun getHero(id: Int) = flow {
        val response = heroClient.fetchHero(id)
        response
            .suspendOnSuccess {
                emit(data.toBO().firstOrNull())
            }
            .suspendOnError {
                emit(null)
            }
            .suspendOnError {
                emit(null)
            }
    }
}