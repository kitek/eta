package pl.kitek.eta.common.data.source.remote.rest

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        if (chain == null) return null

        val request = chain.request()
        val start = System.nanoTime()
        val response = chain.proceed(request)
        val responseString = response.body().string()

        Timber.i("REST %s %s %.1f ms HTTP %d %s",
                request.url().pathSegments().joinToString("/"),
                request.url().query(),
                (System.nanoTime() - start) / 1e6,
                response.code(),
                responseString
        )

        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseString)).build()
    }
}
