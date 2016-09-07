package pl.kitek.eta.common.data.source.remote

import pl.kitek.eta.common.data.source.remote.rest.EtaService

class RemoteDataSource(private val api: EtaService) {

    fun getEta(origins: String, destinations: String) = api.getEta(origins, destinations)

}
