//
//  AppDelegate.swift
//  invoicer
//
//  Created by Lucca Beurmann on 21/05/25.
//

import UIKit
import invoicerShared
import FirebaseCore
import FirebaseAuth
import GoogleSignIn

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    private unowned var mainViewController: MainViewController!

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        setUpMainViewController()
        initApp()
        initFirebase()
        return true
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey: Any] = [:]) -> Bool {
        return GIDSignIn.sharedInstance.handle(url)
    }
    
    private func setUpMainViewController() {
        window = UIWindow(frame: UIScreen.main.bounds)
        let mainViewController = MainViewController()
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        self.mainViewController = mainViewController
    }
    
    private func initApp() {
        AppInit(initializers: KotlinArray(size: 1, init: { KotlinInt in
            KoinModule(
                storage: IosLocalStorage(),
                analyticsTracker: IosAnalyticsTracker(),
                firebaseHelper: IosFirebaseHelper(),
                googleFirebaseHelper: IosGoogleFirebaseHelperImpl(hostViewController: self.mainViewController)
            )
        })).startAppModules()
    }
    
    private func initFirebase() {
        FirebaseApp.configure()
    }
}

