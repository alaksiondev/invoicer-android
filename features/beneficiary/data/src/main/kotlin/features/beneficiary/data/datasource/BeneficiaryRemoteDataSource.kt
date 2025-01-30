package features.beneficiary.data.datasource

import features.beneficiary.data.model.BeneficiariesData
import features.beneficiary.data.model.BeneficiaryData
import features.beneficiary.data.model.CreateBeneficiaryData
import features.beneficiary.data.model.UpdateBeneficiaryData
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

internal interface BeneficiaryRemoteDataSource {
    suspend fun createBeneficiary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String,
    )

    suspend fun getBeneficiaries(
        limit: Long,
        page: Long
    ): BeneficiariesData

    suspend fun getBeneficiaryDetails(
        id: String
    ): BeneficiaryData

    suspend fun deleteBeneficiary(
        id: String
    )

    suspend fun updateBeneficiary(
        id: String,
        name: String,
        bankName: String,
        bankAddress: String,
        swift: String,
        iban: String,
    )
}

internal class BeneficiaryRemoteDataSourceImpl(
    private val httpWrapper: HttpWrapper,
    private val dispatcher: CoroutineDispatcher
) : BeneficiaryRemoteDataSource {

    override suspend fun createBeneficiary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String
    ) = withContext<Unit>(dispatcher) {
        httpWrapper.client.post(
            url = buildUrl {
                host = BASE_URL
                path("/v1/beneficiary")
            },
            block = {
                contentType(ContentType.Application.Json)
                setBody(
                    CreateBeneficiaryData(
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

    override suspend fun getBeneficiaries(
        limit: Long,
        page: Long
    ): BeneficiariesData =
        withContext(dispatcher) {
            httpWrapper.client.get(
                url = buildUrl
                {
                    host = BASE_URL
                    path("/v1/beneficiary")
                },
                block = {
                    contentType(ContentType.Application.Json)
                    parameter("page", page.toString())
                    parameter("limit", limit.toString())
                }
            )
        }.body()

    override suspend fun getBeneficiaryDetails(id: String): BeneficiaryData =
        withContext(dispatcher) {
            httpWrapper.client.get(
                url = buildUrl
                {
                    host = BASE_URL
                    path("/v1/beneficiary/${id}")
                },
                block = {
                    contentType(ContentType.Application.Json)
                }
            )
        }.body()

    override suspend fun deleteBeneficiary(id: String) {
        withContext(dispatcher) {
            httpWrapper.client.delete(
                url = buildUrl
                {
                    host = BASE_URL
                    path("/v1/beneficiary/${id}")
                },
                block = {
                    contentType(ContentType.Application.Json)
                }
            )
        }
    }

    override suspend fun updateBeneficiary(
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
                    path("/v1/beneficiary/${id}")
                },
                block = {
                    contentType(ContentType.Application.Json)
                    setBody(
                        UpdateBeneficiaryData(
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
