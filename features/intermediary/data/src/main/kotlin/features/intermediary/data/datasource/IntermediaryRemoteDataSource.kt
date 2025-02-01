package features.intermediary.data.datasource

import features.intermediary.data.model.CreateIntermediaryData
import features.intermediary.data.model.IntermediariesData
import features.intermediary.data.model.IntermediaryData
import features.intermediary.data.model.UpdateIntermediaryData
import foundation.network.client.BASE_URL
import foundation.network.client.HttpWrapper
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.buildUrl
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal interface IntermediaryRemoteDataSource {
    suspend fun createIntermediary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String,
    )

    suspend fun getIntermediaries(
        limit: Long,
        page: Long
    ): IntermediariesData

    suspend fun getIntermediaryDetails(
        id: String
    ): IntermediaryData

    suspend fun deleteIntermediary(
        id: String
    )

    suspend fun updateIntermediary(
        id: String,
        name: String,
        bankName: String,
        bankAddress: String,
        swift: String,
        iban: String,
    )
}

internal class IntermediaryRemoteDataSourceImpl(
    private val httpWrapper: HttpWrapper,
    private val dispatcher: CoroutineDispatcher
) : IntermediaryRemoteDataSource {

    override suspend fun createIntermediary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String
    ) = withContext<Unit>(dispatcher) {
        httpWrapper.client.post(
            url = buildUrl {
                host = BASE_URL
                path("/v1/intermediary")
            },
            block = {
                contentType(ContentType.Application.Json)
                setBody(
                    CreateIntermediaryData(
                        name = name,
                        swift = swift,
                        iban = iban,
                        bankName = bankName,
                        bankAddress = bankAddress
                    )
                )
            }
        )
    }

    override suspend fun getIntermediaries(
        limit: Long,
        page: Long
    ): IntermediariesData =
        withContext(dispatcher) {
            httpWrapper.client.get(
                url = buildUrl
                {
                    host = BASE_URL
                    path("/v1/intermediary")
                },
                block = {
                    contentType(ContentType.Application.Json)
                    parameter("page", page.toString())
                    parameter("limit", limit.toString())
                }
            )
        }.body()

    override suspend fun getIntermediaryDetails(id: String): IntermediaryData =
        withContext(dispatcher) {
            httpWrapper.client.get(
                url = buildUrl
                {
                    host = BASE_URL
                    path("/v1/intermediary/${id}")
                },
                block = {
                    contentType(ContentType.Application.Json)
                }
            )
        }.body()

    override suspend fun deleteIntermediary(id: String) {
        withContext(dispatcher) {
            httpWrapper.client.delete(
                url = buildUrl
                {
                    host = BASE_URL
                    path("/v1/intermediary/${id}")
                },
                block = {
                    contentType(ContentType.Application.Json)
                }
            )
        }
    }

    override suspend fun updateIntermediary(
        id: String,
        name: String,
        bankName: String,
        bankAddress: String,
        swift: String,
        iban: String
    ) {
        withContext(dispatcher) {
            httpWrapper.client.put(
                url = buildUrl {
                    host = BASE_URL
                    path("/v1/intermediary/${id}")
                },
                block = {
                    contentType(ContentType.Application.Json)
                    setBody(
                        UpdateIntermediaryData(
                            name = name,
                            swift = swift,
                            iban = iban,
                            bankName = bankName,
                            bankAddress = bankAddress
                        )
                    )
                }
            )
        }
    }
}
