package com.fduranortega.marvelheroes.data.repository

import com.fduranortega.marvelheroes.data.local.HeroLocalDataSource
import com.fduranortega.marvelheroes.data.remote.HeroRemoteDataSource
import com.fduranortega.marvelheroes.data.util.MockUtil.mockHero
import com.fduranortega.marvelheroes.data.util.MockUtil.mockHeroId
import com.fduranortega.marvelheroes.data.util.MockUtil.mockHeroList
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before

import org.junit.Test

class HeroRepositoryTest {

    private lateinit var repository: HeroRepository
    private val localDataSource: HeroLocalDataSource = mock()
    private val remoteDataSource: HeroRemoteDataSource = mock()

    @Before
    fun setup() {
        repository = HeroRepositoryImpl(remoteDataSource, localDataSource)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(localDataSource, remoteDataSource)
    }

    @Test
    fun getHeroListFromRemote() = runBlocking {

        whenever(localDataSource.getHeroList(0)).thenReturn(emptyList())
        whenever(remoteDataSource.getHeroList(0)).thenReturn(flow { emit(mockHeroList()) })

        val response = repository.getHeroList(0).first()

        verify(localDataSource).getHeroList(0)
        verify(remoteDataSource).getHeroList(0)
        verify(localDataSource).saveHeroList(mockHeroList(), 0)

        assertEquals(response, mockHeroList())
    }

    @Test
    fun getHeroListFromLocal() = runBlocking {

        whenever(localDataSource.getHeroList(0)).thenReturn(mockHeroList())

        val response = repository.getHeroList(0).first()

        verify(localDataSource).getHeroList(0)

        assertEquals(response, mockHeroList())
    }

    @Test
    fun getHeroFromRemote() = runBlocking {
        whenever(localDataSource.getHero(mockHeroId())).thenReturn(null)
        whenever(remoteDataSource.getHero(mockHeroId())).thenReturn(flow { emit(mockHero()) })

        val response = repository.getHero(mockHeroId()).first()

        verify(localDataSource).getHero(mockHeroId())
        verify(remoteDataSource).getHero(mockHeroId())
        verify(localDataSource).saveHero(mockHero())

        assertEquals(response, mockHero())
    }

    @Test
    fun getHeroFromLocal() = runBlocking {

        whenever(localDataSource.getHero(mockHeroId())).thenReturn(mockHero())
        whenever(remoteDataSource.getHero(mockHeroId())).thenReturn(flow { emit(mockHero()) })

        val response = repository.getHero(mockHeroId()).first()

        verify(localDataSource).getHero(mockHeroId())
        verify(remoteDataSource).getHero(mockHeroId())
        verify(localDataSource).saveHero(mockHero())

        assertEquals(response, mockHero())
    }
}