# What is Invoicer App?
The Invoicer app is a mobile client for the Invoicer API, which is a service that allows users to create and manage invoices. The app provides a user-friendly interface for interacting with the Invoicer API, allowing users to create, view, and manage invoices on their mobile devices.
Invoicer as a whole is a full-stack study use case based on my personal needs working as a contractor for foreigner companies. Most companies require an Invoice to properly
pay you for your work. Because writing an invoice manually over and over again is a boring thing I've decided to create my own solution to address this problem.

## What can Invoicer do?
The Invoicer app allows users to:
1. Create and manage invoices
2. Create and manage payment Beneficiaries and Intermediaries
3. Create accounts
4. Sign in with Google
5. Store Invoice PDFs in an S3 compatible storage (providing files is still in progress)


## Tech stack
Invoicer is built using the following technologies:
- Android
- Kotlin
- Jetpack Compose
- Firebase
- Koin
- Ktor
- Kover
- Detekt

### Tech stack breakdown
1. UI is Built with Compose for faster development, component reusability, solid design system capabilities and standardization, concise and easy to read code.
2. Dependency Injection is done with Koin for its simplicity and ease of use. It's also KMP compatible, which is a plus for future development. Other options were also considered (Kodein & Hilt) but were discarded due to limitations or implementation overhead.
3. Ktor is used for networking. It is a lightweight and flexible HTTP client that allows for easy customization and extension. It also has a great support for coroutines, which makes it a good fit for modern Android development. It's also KMP compatible, which means a possible migration will be less painful.
4. Kover is used to generate code coverage reports for two main reasons: Easy to configure, extensible, and maintained by Jetbrains.
5. Detekt is used for static code analysis. It is a powerful tool that helps to identify code smells and potential bugs in the codebase. It is also highly configurable and can be easily integrated into the build process.
6. Firebase is used for authentication and analytics. It's free and easy to use, while also being a very solid and complete solution for mobile apps.

### App Architecture
Invoices is built using a clean architecture approach to separate concerns and avoid tight coupling between components. Feature code in general is split in three layers:
1. Presentation/UI: Responsible for displaying data and handle user interaction.
2. Domain: Determine a bridge between screens and datasources and also holds the business logic. This layer guarantees that the app is decoupled from the data source and can be easily tested.
3. Data: Responsible for data management and persistence. This layer is responsible for fetching data from the API and storing it in the local database. It also handles caching and data synchronization.

#### Where are the UseCases?
UseCases are not widely used in the app, but they are used in some cases to encapsulate business logic and make it easier to test. The app uses a repository pattern to abstract the data source and provide a clean API for the presentation layer. Sometimes use cases are nothing but a proxy class between presentation consumers and the repository interface, so I don't think they are always required, except for cases where there are business rules to be applied.

#### State Management Arhitecture
App is built using a unidirectional data flow architecture. This means that the state of the app is always flowing in one direction, from the view model to the view. This makes it easier to reason about the state of the app and helps to avoid bugs caused by state changes happening in multiple places. MVVM was my choice to orchestrate state management due to it's simplicity, wide adoption, native integrations with the Android framework (hello ViewModelStores). AndroidX ViewModels also comes with built-in support for coroutines through a lifecycle aware coroutine scope, which is a great fit for modern Android development.

#### ViewModel driven events
Events published by the VM to its respective View are handled by SharedFlows, a simple stream API that allows for easy communication between components.

### Testing principles
Invoices contains 3 types of tests:
- Unit Tests
- Snapshot Tests
- E2E Tests powered by Maestro

# Get Started
## Setup
1. Follow the steps at (Invoicer-Infra repository)[https://github.com/alaksiondev/invoicer-infra]] to setup a local environment for the Invoicer project. This will set up a local instance of the Invoicer API and a PostgreSQL database.
2. Clone this repository to your local machine.
3. Create `local.properties` file and sent the following properties:
```properties
# For testing reasons we use the same URL for both debug and production.
# Set the URL to the local instance of the Invoicer API created by the Invoicer-Infra repository DockerCompose file.
# Ideally you should target the NGINX address but for testing purposes you can use the API address.
DEBUG_APP_URL="URL for the local instance of the Invoicer API"
PROD_APP_URL="URL for the local instance of the Invoicer API"
FIREBASE_WEB_ID=""
```

### Firebase setup
Use the same Firebase project used by the Invoicer API.
1. Go to Firebase console and open the Firebase project created for the Invoicer API Service.
2. Follow instructions and add the Invoicer to the Firebase project.
3. Download the `google-services.json` file and place it in the `app/` directory of the project.
4. Enable Google Analytics
5. Enable Firebase Authentication
6. Enable Google Sign in method
7. Go to Authentication -> Sign-in-method -> Google -> Web SDK Config -> Copy WebClient ID and set it in the `local.properties` file as `FIREBASE_WEB_ID`. This is required for the Google Sign in to work properly.

## Run the app
Once the setup is done install the app on your device or emulator and run it. You should be able to interact and test it.