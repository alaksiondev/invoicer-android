package foundation.auth.domain.repository

import foundation.auth.domain.model.StoredTokens

interface TokenRepository {
    suspend fun getTokens(): StoredTokens
}