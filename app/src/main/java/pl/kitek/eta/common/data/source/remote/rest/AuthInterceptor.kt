package pl.kitek.eta.common.data.source.remote.rest

import okhttp3.Interceptor
import okhttp3.Response
import pl.kitek.eta.BuildConfig

class AuthInterceptor : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain?): Response? {
        if (chain == null) return null
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder().addQueryParameter("key", BuildConfig.API_ENDPOINT_KEY).build()
        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
