//
//  IosGoogle.swift
//  invoicer
//
//  Created by Lucca Beurmann on 22/05/25.
//

import invoicerShared
import FirebaseCore
import FirebaseAuth
import GoogleSignIn

final class IosGoogleFirebaseHelperImpl: IosGoogleFirebaseHelper {
    
    private let hostViewController: UIViewController
    
    init(hostViewController: UIViewController) {
        self.hostViewController = hostViewController
    }
    
    func getGoogleIdToken() async throws ->  invoicerShared.IosGoogleResult {
        do {
            try configureGoogleSignIn()
            let userTokens = try await signInUser()
            let credential = GoogleAuthProvider.credential(withIDToken: userTokens.idToken, accessToken: userTokens.accessToken)
            try await firebaseSignIn(with: credential)
            return try await IosGoogleResultSuccess(token: fetchCurrentUserIdToken())
            
        } catch let configurationError as ConfigurationError {
            return IosGoogleResultError(exception: KotlinException(message: configurationError.errorDescription))
            
        } catch let signInError as SignInError {
            switch signInError {
            case .cancelled:
                return IosGoogleResultCancelled()
            case .failed:
                return IosGoogleResultError(exception: KotlinException(message: signInError.errorDescription))
            }
            
        } catch let firebaseSignInError as FirebaseSignInError {
            return IosGoogleResultError(exception: KotlinException(message: firebaseSignInError.errorDescription))
            
        } catch let firebaseUserError as FirebaseUserError {
            return IosGoogleResultError(exception: KotlinException(message: firebaseUserError.errorDescription))
        }
    }
    
    // MARK: - Private methods
    
    private func configureGoogleSignIn() throws {
        guard let clientID = FirebaseApp.app()?.options.clientID else {
            throw ConfigurationError.missingClientId
        }
        
        let config = GIDConfiguration(clientID: clientID)
        GIDSignIn.sharedInstance.configuration = config
    }
    
    private func signInUser() async throws -> UserTokens {
        do {
            let result = try await GIDSignIn.sharedInstance.signIn(withPresenting: hostViewController)
            
            guard let idToken = result.user.idToken else {
                throw SignInError.failed("Failed to get idToken")
            }
            
            return UserTokens(accessToken: result.user.accessToken.tokenString, idToken: idToken.tokenString)
            
        } catch let error as GIDSignInError {
            switch error.code {
            case .canceled:
                throw SignInError.cancelled
            default:
                throw SignInError.failed(error.localizedDescription)
            }
        }
    }
    
    private func firebaseSignIn(with credential: AuthCredential) async throws {
        do {
            try await Auth.auth().signIn(with: credential)
        } catch let error {
            throw FirebaseSignInError.failed(error.localizedDescription)
        }
    }
    
    private func fetchCurrentUserIdToken() async throws -> String {
        do {
            guard let user = Auth.auth().currentUser else {
                throw FirebaseUserError.userNotAvailable
            }
            
            return try await user.getIDToken()
        } catch let error as LocalizedError {
            throw FirebaseUserError.tokenNotAvailable(error.errorDescription ?? "Failed to fetch user idToken")
        }
    }
}


// MARK: - Custom errors
private enum ConfigurationError: LocalizedError {
    case missingClientId
    
    var errorDescription: String? {
        switch self {
        case .missingClientId:
            return "Missing client ID."
        }
    }
}

private enum SignInError: LocalizedError {
    case cancelled
    case failed(String)
}

private enum FirebaseSignInError: LocalizedError {
    case failed(String)
}

private enum FirebaseUserError: LocalizedError {
    case userNotAvailable
    case tokenNotAvailable(String)
    
    var errorDescription: String? {
        switch self {
        case .userNotAvailable:
            return "User not available"
        case .tokenNotAvailable (let customMessage):
            return customMessage
        }
    }
}

// MARK: - Return types

private struct UserTokens {
    let accessToken: String
    let idToken: String
}
