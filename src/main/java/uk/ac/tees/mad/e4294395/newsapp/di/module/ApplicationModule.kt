package uk.ac.tees.mad.e4294395.newsapp.di.module

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import uk.ac.tees.mad.e4294395.newsapp.data.api.ApiKeyInterceptor
import uk.ac.tees.mad.e4294395.newsapp.data.api.NetworkService
import uk.ac.tees.mad.e4294395.newsapp.di.BaseUrl
import uk.ac.tees.mad.e4294395.newsapp.di.DatabaseName
import uk.ac.tees.mad.e4294395.newsapp.di.NetworkApiKey
import uk.ac.tees.mad.e4294395.newsapp.utils.AppConstant.API_KEY
import uk.ac.tees.mad.e4294395.newsapp.utils.AppConstant.BASE_URL
import uk.ac.tees.mad.e4294395.newsapp.utils.AppConstant.DATABASE_NAME
import uk.ac.tees.mad.e4294395.newsapp.utils.DefaultDispatcherProvider
import uk.ac.tees.mad.e4294395.newsapp.utils.DispatcherProvider
import uk.ac.tees.mad.e4294395.newsapp.utils.internetcheck.InternetConnected
import uk.ac.tees.mad.e4294395.newsapp.utils.internetcheck.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.AppRoomDataBase
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.AppRoomDatabaseService
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.DatabaseService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = BASE_URL

    @NetworkApiKey
    @Provides
    fun provideApiKey(): String = API_KEY

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@NetworkApiKey apiKey: String): ApiKeyInterceptor =
        ApiKeyInterceptor(apiKey)

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(apiKeyInterceptor).build()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideDispatcher() : DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return InternetConnected(context)
    }

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = DATABASE_NAME

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): AppRoomDataBase {
        return Room.databaseBuilder(
            context,
            AppRoomDataBase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(appDatabase: AppRoomDataBase): DatabaseService {
        return AppRoomDatabaseService(appDatabase)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context) : WorkManager {
        return WorkManager.getInstance(context)
    }
}