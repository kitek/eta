package pl.kitek.eta.common.data.source

import pl.kitek.eta.common.data.model.Eta
import pl.kitek.eta.common.data.model.EtaResponse
import pl.kitek.eta.common.data.source.remote.RemoteDataSource
import pl.kitek.eta.common.data.source.remote.rest.EtaService
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DataRepository {

    private val remote = RemoteDataSource(EtaService.getService())

    fun getEta(eta: Eta): Observable<EtaResponse> {
        return remote.getEta(eta.origin!!.name, eta.destination!!.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
