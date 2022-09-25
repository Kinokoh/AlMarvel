package com.chaliez.alma.test.data.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import com.chaliez.alma.test.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest

//https://developer.marvel.com/documentation/authorization
class AuthentGetUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("ts", BuildConfig.APITS) //timestamp, default
            .addQueryParameter("apikey", BuildConfig.APIPublic) //public key
            .addQueryParameter("hash", md5Hash(BuildConfig.APITS+BuildConfig.APIPrivate+BuildConfig.APIPublic)) //hash md5 (ts+privateKey+publicKey)
            .build()

        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder().url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun md5Hash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}