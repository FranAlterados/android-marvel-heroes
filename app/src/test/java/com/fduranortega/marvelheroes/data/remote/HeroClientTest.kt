package com.fduranortega.marvelheroes.data.remote

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

class HeroClientTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var heroService: HeroService

    @Throws(IOException::class)
    @Before
    fun mockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        heroService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()
            .create(HeroService::class.java)

    }

    @Throws(IOException::class)
    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }

    @Throws(IOException::class)
    @Test
    fun fetchHeroListFromNetworkTest() = runBlocking {
        enqueueResponse("/HeroListResponse.json")
        val response = heroService.fetchHeroList()
        val responseBody = requireNotNull((response as ApiResponse.Success).data)
        mockWebServer.takeRequest()

        MatcherAssert.assertThat(responseBody.data.results.size, CoreMatchers.`is`(20))
        MatcherAssert.assertThat(responseBody.data.results[0].id, CoreMatchers.`is`(1011334))
        MatcherAssert.assertThat(responseBody.data.results[0].name, CoreMatchers.`is`("3-D Man"))
        MatcherAssert.assertThat(responseBody.data.results[0].description, CoreMatchers.`is`(""))
        MatcherAssert.assertThat(
            responseBody.data.results[0].thumbnail.path,
            CoreMatchers.`is`("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784")
        )
        MatcherAssert.assertThat(responseBody.data.results[0].thumbnail.extension, CoreMatchers.`is`("jpg"))
        MatcherAssert.assertThat(responseBody.data.results[0].comics?.available, CoreMatchers.`is`(12))
        MatcherAssert.assertThat(
            responseBody.data.results[0].comics?.collectionURI,
            CoreMatchers.`is`("http://gateway.marvel.com/v1/public/characters/1011334/comics")
        )
    }


    @Throws(IOException::class)
    @Test
    fun fetchHeroFromNetworkTest() = runBlocking {
        enqueueResponse("/HeroResponse.json")
        val response = heroService.fetchHero(1011334)
        val responseBody = requireNotNull((response as ApiResponse.Success).data)
        mockWebServer.takeRequest()

        MatcherAssert.assertThat(responseBody.data.results.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(responseBody.data.results[0].id, CoreMatchers.`is`(1011334))
        MatcherAssert.assertThat(responseBody.data.results[0].name, CoreMatchers.`is`("3-D Man"))
        MatcherAssert.assertThat(responseBody.data.results[0].description, CoreMatchers.`is`(""))
        MatcherAssert.assertThat(
            responseBody.data.results[0].thumbnail.path,
            CoreMatchers.`is`("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784")
        )
        MatcherAssert.assertThat(responseBody.data.results[0].thumbnail.extension, CoreMatchers.`is`("jpg"))
        MatcherAssert.assertThat(responseBody.data.results[0].comics?.available, CoreMatchers.`is`(12))
        MatcherAssert.assertThat(
            responseBody.data.results[0].comics?.collectionURI,
            CoreMatchers.`is`("http://gateway.marvel.com/v1/public/characters/1011334/comics")
        )
    }

    @Throws(IOException::class)
    @Test
    fun fetchHeroExtraFromNetworkTest() = runBlocking {
        enqueueResponse("/HeroExtraResponse.json")
        val response = heroService.fetchHeroExtra("")
        val responseBody = requireNotNull((response as ApiResponse.Success).data)
        mockWebServer.takeRequest()

        MatcherAssert.assertThat(responseBody.data.results.size, CoreMatchers.`is`(12))
        MatcherAssert.assertThat(responseBody.data.results[0].id, CoreMatchers.`is`(22506))
        MatcherAssert.assertThat(
            responseBody.data.results[0].title,
            CoreMatchers.`is`("Avengers: The Initiative (2007) #19")
        )
        MatcherAssert.assertThat(
            responseBody.data.results[0].description,
            CoreMatchers.`is`("Join 3-D MAN, CLOUD 9, KOMODO, HARDBALL, and heroes around America in the battle that will decide the fate of the planet and the future of the Initiative program. Will the Kill Krew Army win the day?")
        )
        MatcherAssert.assertThat(
            responseBody.data.results[0].thumbnail.path,
            CoreMatchers.`is`("http://i.annihil.us/u/prod/marvel/i/mg/d/03/58dd080719806")
        )
        MatcherAssert.assertThat(responseBody.data.results[0].thumbnail.extension, CoreMatchers.`is`("jpg"))
    }


}