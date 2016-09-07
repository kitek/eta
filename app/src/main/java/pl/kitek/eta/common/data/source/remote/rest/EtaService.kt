package pl.kitek.eta.common.data.source.remote.rest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import pl.kitek.eta.BuildConfig
import pl.kitek.eta.common.data.model.EtaResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface EtaService {

    companion object {
        fun getService(): EtaService {

            val okBuilder = OkHttpClient.Builder()
            okBuilder.addInterceptor(AuthInterceptor())
            if (BuildConfig.DEBUG) {
                okBuilder.addInterceptor(LoggingInterceptor())
            }

            val gson = GsonBuilder().create()

            val builder = Retrofit.Builder()
                    .client(okBuilder.build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BuildConfig.API_ENDPOINT_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return builder.create(EtaService::class.java)
        }
    }

    @GET("json") fun getEta(@Query("origins") origins: String, @Query("destinations") destinations: String): Observable<EtaResponse>
}
