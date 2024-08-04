package com.hanif.e_commerce.data.di

import android.content.Context
import androidx.room.Room
import com.hanif.e_commerce.data.datasource.localdb.MyDatabase
import com.hanif.e_commerce.data.datasource.localdb.dao.ProductDao
import com.hanif.e_commerce.data.datasource.remote.ApiService
import com.hanif.e_commerce.data.repo.ProductImpl
import com.hanif.e_commerce.domain.repo.Product
import com.hanif.e_commerce.domain.usecase.ProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val DB = "product_db"

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MyDatabase {
        return Room.databaseBuilder(
            appContext,
            MyDatabase::class.java,
            DB
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideYourDao(db: MyDatabase) = db.productDao()

    @Provides
    @Singleton
    fun provideProductRepository(dao: ProductDao, apiService: ApiService): Product {
        return ProductImpl(apiService, dao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: Product): ProductUseCase {
        return ProductUseCase(
            repository
        )
    }
}