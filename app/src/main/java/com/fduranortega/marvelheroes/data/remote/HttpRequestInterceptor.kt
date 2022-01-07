package com.fduranortega.marvelheroes.data.remote

import com.fduranortega.marvelheroes.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest

class HttpRequestInterceptor : Interceptor {

  companion object {
    const val MD5 = "MD5"
    const val API_KEY_LABEL = "apikey"
    const val HASH_LABEL = "hash"
    const val TIMESTAMP_LABEL = "ts"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val timestamp = System.currentTimeMillis()
    val publicApiKey = BuildConfig.PUBLIC_API_KEY
    val privateApiKey = BuildConfig.PRIVATE_API_KEY

    val hash = md5("$timestamp$privateApiKey$publicApiKey")
    val originalRequest = chain.request()
    val url = originalRequest.url
      .newBuilder()
      .addQueryParameter(TIMESTAMP_LABEL, timestamp.toString())
      .addQueryParameter(API_KEY_LABEL, publicApiKey)
      .addQueryParameter(HASH_LABEL, hash)
      .build()
    val request = originalRequest.newBuilder().url(url).build()
    Timber.d(request.toString())
    return chain.proceed(request)
  }

  private fun md5(input: String): String {
    val md = MessageDigest.getInstance(MD5)
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
  }
}
