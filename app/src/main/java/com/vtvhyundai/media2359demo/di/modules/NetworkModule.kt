package com.vtvhyundai.media2359demo.di.modules

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Loggable
import com.vtvhyundai.media2359demo.BuildConfig
import com.vtvhyundai.media2359demo.BuildConfig.BASE_URL
import com.vtvhyundai.media2359demo.network.HeaderInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.io.File
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
        .create()

    @Provides
    @Singleton
    fun provideScalarsConverterAdapter(): ScalarsConverterFactory = ScalarsConverterFactory.create()

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        when (BuildConfig.DEBUG) {
            true -> httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            false -> httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): HeaderInterceptor =
        HeaderInterceptor()

    @Provides
    @Singleton
    fun provideCacheFile(context: Context): File = context.filesDir

    @Provides
    @Singleton
    fun provideCache(file: File): Cache = Cache(file, 10 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverter: GsonConverterFactory,
        coroutineCallAdapterFactor: CoroutineCallAdapterFactory,
        scalarsConverterFactory: ScalarsConverterFactory
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverter)
        .addConverterFactory(scalarsConverterFactory)
        .addCallAdapterFactory(coroutineCallAdapterFactor).build()

    @Provides
    @Singleton
    fun provideCoroutineCallAdapterFactor(): CoroutineCallAdapterFactory =
        CoroutineCallAdapterFactory()


    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        cache: Cache
    ): OkHttpClient = getUnsafeOkHttpClient(logger, headerInterceptor, cache)

    @SuppressLint("TrustAllX509TrustManager")
    fun getUnsafeOkHttpClient(
        logger: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        cache: Cache
    ): OkHttpClient {
        return try { // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {

                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(
                    sslSocketFactory,
                    (trustAllCerts[0] as X509TrustManager)
                )
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .addInterceptor(logger)
                .addInterceptor(headerInterceptor)
                .addNetworkInterceptor(CurlInterceptor(Loggable { message: String ->
                    Timber.e(
                        message
                    )
                }))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}