//
//  IosFirebaseHelper.swift
//  invoicer
//
//  Created by Lucca Beurmann on 21/05/25.
//

import invoicerShared
import FirebaseAuth
import GoogleSignIn

final class IosFirebaseHelper: FirebaseHelper {
    
    func signOut() {
        do {
            try Auth.auth().signOut()
            GIDSignIn.sharedInstance.signOut()
        } catch {
            
        }
    }
}
