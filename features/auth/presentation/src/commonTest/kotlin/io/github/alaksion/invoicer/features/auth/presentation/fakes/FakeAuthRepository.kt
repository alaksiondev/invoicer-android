package io.github.alaksion.invoicer.features.auth.presentation.fakes

import io.github.alaksion.invoicer.foundation.auth.domain.model.AuthToken
import io.github.alaksion.invoicer.foundation.auth.domain.repository.AuthRepository

class FakeAuthRepository : AuthRepository {

    var signUpCalls = 0
        private set

    var signUpError: Throwable? = null

    override suspend fun signUp(
        email: String,
        confirmEmail: String,
        password: String
    ) {
        signUpCalls++
        signUpError?.let { throw it }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): AuthToken {
        TODO("Not yet implemented")
    }

    override suspend fun googleSignIn(token: String): AuthToken {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshSession(refreshToken: String): AuthToken {
        TODO("Not yet implemented")
    }
}