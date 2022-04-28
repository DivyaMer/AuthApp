package com.app.authenticationapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.app.authenticationapp.BuildConfig
import com.app.authenticationapp.network.APIConstant
import com.app.authenticationapp.network.APIConstant.AUTHORIZATION
import com.app.authenticationapp.network.APIConstant.BASE_URL
import com.app.authenticationapp.network.BaseDataSource
import com.app.authenticationapp.network.NetworkService
import com.app.authenticationapp.prefs.UserPreference
import com.app.authenticationapp.utils.AppConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, userPreference: UserPreference): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient(userPreference))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideNetworkService(retrofit: Retrofit): NetworkService =
        retrofit.create(NetworkService::class.java)

    @Provides
    @Singleton
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstants.USER_PREFERENCE, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideUserPref(sharedPreferences: SharedPreferences, context: Context): UserPreference {
        return UserPreference(sharedPreferences, context)
    }

    @Provides
    @Singleton
    fun getBaseResponse(): BaseDataSource {
        return BaseDataSource()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()


    @Provides
    fun okHttpClient(userPreference: UserPreference): OkHttpClient {
        val levelType: HttpLoggingInterceptor.Level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        httpClient.addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()

            // Request customization: add request headers
            var apiKey: String = userPreference[APIConstant.BAERER, ""]
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Accept", "application/json; charset=iso-8859-1")
            // .header(HEADER_KEY, HEADER_VALUE) // <-- this is the important line
            if (apiKey.isNotEmpty()) {
                requestBuilder.addHeader(
                    AUTHORIZATION,
                    apiKey
                )
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        })

        httpClient.callTimeout(2, TimeUnit.MINUTES)
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.writeTimeout(5, TimeUnit.MINUTES)
        httpClient.addNetworkInterceptor(logging)

        val client = httpClient.build()
        return client

    }

}