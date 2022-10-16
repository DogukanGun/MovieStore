package com.dag.moviestore.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dag.moviestore.network.BaseNetworkLogger
import com.dag.moviestore.network.MovieStoreService
import com.dag.moviestore.network.calladapter.NetworkResponseAdapterFactory
import com.dag.moviestore.base.general.BaseDialogBoxUtil
import com.dag.moviestore.base.general.HeaderManager
import com.dag.moviestore.base.general.MovieStoreActivityListener
import com.dag.moviestore.base.general.MovieStoreApplication
import com.dag.moviestore.base.ui.MovieStoreNavigator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMovieStoreActivityListener() = MovieStoreActivityListener()

    @Provides
    @Singleton
    fun provideChuckerInterceptor(): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(MovieStoreApplication.appInstance).build()
    }

    @Provides
    @Singleton
    fun provideHeaderManager(): HeaderManager = HeaderManager()

    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        headerManager: HeaderManager
    ) = Interceptor { chain ->
        val request = chain.request()

        val requestBuilder = request.newBuilder()
        headerManager.headers.forEach {
            requestBuilder.removeHeader(it.key)
            requestBuilder.addHeader(it.key, it.value)
        }
        val updatedRequest = requestBuilder
            .method(request.method, request.body)
            .build()
        return@Interceptor chain.proceed(updatedRequest)
    }

    @Provides
    @Singleton
    fun provideBaseOkHttpClientBuilder(
        cache: Cache,
        chuckerInterceptor: ChuckerInterceptor,
        baseNetworkLogger: BaseNetworkLogger,
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(baseNetworkLogger)
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(60000L, TimeUnit.MILLISECONDS)
            .readTimeout(60000L, TimeUnit.MILLISECONDS)
            .writeTimeout(60000L, TimeUnit.MILLISECONDS)
    }

    @Provides
    @Singleton
    fun provideBaseOkHttpClientAuthenticated(
        okHttpClientBuilder: OkHttpClient.Builder,
        headerInterceptor: Interceptor
    ): OkHttpClient {
        return okHttpClientBuilder
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideMovieStoreServiceAuthenticated(
        retrofit: Retrofit
    ): MovieStoreService = retrofit.create(MovieStoreService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitAuthenticated(
        baseOkHttpClient: Lazy<OkHttpClient>,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .baseUrl(MovieStoreApplication.baseUrl)
            .callFactoryDelegate(baseOkHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideCache(application: Application):Cache{
        val cacheSize = 10*1024*1024*2
        return Cache(application.cacheDir,cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideBaseDialogBoxUtil(
        navigator: MovieStoreNavigator,
        service: MovieStoreService,
        cleanActivityListener: MovieStoreActivityListener
    ) = BaseDialogBoxUtil(navigator,service,cleanActivityListener)

    @Provides
    @Singleton
    fun provideClNavigator(
        activityListener: MovieStoreActivityListener
    ) = MovieStoreNavigator(activityListener)
}
private fun Retrofit.Builder.callFactoryDelegate(client: Lazy<OkHttpClient>) = callFactory(
    object : Call.Factory {
        override fun newCall(request: Request) = client.get().newCall(request)
    }
)
private fun createLegacyRetrofit(gson: Gson, client: Lazy<OkHttpClient>): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .baseUrl(MovieStoreApplication.baseUrl)
        .callFactoryDelegate(client)
        .build()
}
