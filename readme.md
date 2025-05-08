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

## Run the app
Once the setup is done install the app on your device or emulator and run it. You should be able to interact and test it.