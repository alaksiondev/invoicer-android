package features.beneficiary.data.datasource

import features.beneficiary.data.model.BeneficiariesData
import features.beneficiary.data.model.CreateBeneficiaryData
import foundation.network.client.BASE_URL
import foundation.network.client.HttpWrapper
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
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
                path("/beneficiary")
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
                    path("/beneficiary")
                },
                block = {
                    contentType(ContentType.Application.Json)
                    parameter("page", page.toString())
                    parameter("limit", limit.toString())
                }
            )
        }.body()
}
