//
//  ViewController.swift
//  invoicer
//
//  Created by Lucca Beurmann on 21/05/25.
//

import UIKit
import invoicerShared
import FirebaseCore
import FirebaseAuth
import GoogleSignIn

class MainViewController: UIViewController {
    
    private let composeViewController: UIViewController
    
    required init() {
        self.composeViewController = IosMainKt.IosMainViewController()
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        addChild(composeViewController)
        composeViewController.view.translatesAutoresizingMaskIntoConstraints = true
        composeViewController.view.frame = view.bounds
        view.addSubview(composeViewController.view)
        composeViewController.didMove(toParent: self)
    }
}
