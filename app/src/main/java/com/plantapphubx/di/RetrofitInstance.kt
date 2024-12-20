package com.plantapphubx.di

import com.plantapphubx.data.remote.ServiceInterface
import com.plantapphubx.data.repository.ServiceRepositoryImpl
import com.plantapphubx.domain.usecase.FetchCategoriesUseCase
import com.plantapphubx.domain.usecase.FetchQuestionsUseCase
import com.plantapphubx.utils.Constants
import com.plantapphubx.utils.CustomHttpLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor(CustomHttpLogger())
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method, original.body)

                chain.proceed(request.build())
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ServiceInterface {
        return retrofit.create(ServiceInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ServiceInterface): ServiceRepositoryImpl {
        return ServiceRepositoryImpl(apiService)
    }

    @Provides
    fun provideFetchQuestionsUseCase(repository: ServiceRepositoryImpl): FetchQuestionsUseCase {
        return FetchQuestionsUseCase(repository)
    }

    @Provides
    fun provideFetchCategoriesUseCase(repository: ServiceRepositoryImpl): FetchCategoriesUseCase {
        return FetchCategoriesUseCase(repository)
    }
}