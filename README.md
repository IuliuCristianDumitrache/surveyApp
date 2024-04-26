# SurveyApp

This documentation provides an overview of the functionality and implementation details of the Survey app. The Survey app allows users to answer a series of questions and submit their responses to a server.


<img src="https://github.com/IuliuCristianDumitrache/surveyApp/blob/main/surveyApp.gif" width="400" height="790">

## Requirements
- Initial Screen
  Display a button labeled "Start survey".
  Upon pressing the button, navigate to the Questions Screen.
  
- Questions Screen
  - Display survey questions in a horizontal pager.
  - Provide previous and next buttons to navigate between questions.
  - Disable previous button when on the first question and next button when on the last question.
  - Display a counter of already submitted questions on top of each question.
  - Submit button should be disabled when no answer text exists or when the answer has already been submitted.
  - Upon pressing the submit button, post the response to the server.
  - Handle success and failure responses from the server:
    - Success (200 status): Display a notification banner with the text "Success!" for a few seconds.
    - Failure (400 status): Display a notification banner with the text "Failure..." and a retry button.
  - Keep track of successfully submitted questions in memory to maintain the submitted answer and disable the submit button when revisiting a submitted question.


## Architecture
The application connects to an API and utilizes /questions endpoint to fetch question data and utilizes the /question/submit to post a response

Follows the Model-View-ViewModel (MVI) architecture pattern for better separation of concerns and maintainability. Components include:
MVI stands for Model-View-Intent :

- **Model:** instead of having a separate state for the View, ViewModel, and the Data layer, the Model will be the single source of truth of the overall state, and it’s immutable to ensure that it will be updated from only one place.
- **View:**  represent the UI layer, the activity, or the fragment, it defines the user actions and renders the state emitted by the model.
- **Intent:**  do not confuse it with the Android Intent, it’s the intention to perform an action either by the user or the app itself.

## Third-party Libraries
1. **Retrofit2:**
   - *Implementation:* com.squareup.retrofit2:retrofit:2.9.0
     - Retrofit is a type-safe HTTP client for Android and Java used to make API calls in a structured and organized manner.
   - *Implementation:* com.squareup.retrofit2:converter-gson:2.9.0
     - Gson converter for Retrofit, used for converting JSON response from API calls to Java/Kotlin objects.
   
2. **OkHttp Logging interceptor:**
   - *Implementation:* com.squareup.okhttp3:logging-interceptor:4.9.1
     - OkHttp is an HTTP client for Java and Kotlin. The logging interceptor is used for logging HTTP request and response data for debugging purposes.
   
3. **Dagger Hilt:**
   - *Implementation:* com.google.dagger:hilt-android:2.44
     - Dagger Hilt is a dependency injection library for Android. It simplifies the process of managing dependencies and facilitates modularization.
   - *Implementation:* androidx.hilt:hilt-navigation-compose:1.1.0
     - Hilt Navigation Compose provides integration between Jetpack Navigation and Hilt for dependency injection in Jetpack Compose-based applications.
   - *Kapt:* com.google.dagger:hilt-android-compiler:2.44, androidx.hilt:hilt-compiler:1.1.0
     - Annotation processors for Dagger Hilt to generate code for dependency injection.
   - *Implementation:* androidx.hilt:hilt-work:1.1.0
     - Hilt Work provides support for dependency injection in Android WorkManager classes.
     
4. **ViewModel:**
   - *Implementation:* androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0
     - ViewModel component of the Android Architecture Components used for managing UI-related data in a lifecycle-conscious way.
   - *Implementation:* androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0, androidx.lifecycle:lifecycle-runtime-compose:2.7.0
     - ViewModel utilities for integration with Jetpack Compose.
       
5. **Material Design 3:**
    - *implementation:* androidx.compose.material3:material3:1.1.2
    
6. **Testing:**
    - *TestImplementation:* org.mockito:mockito-core:4.0.0
    - *TestImplementation:* junit:junit:4.13.2
      - Mocking library for Kotlin used in unit testing to simulate behavior of objects and dependencies.


## Building the Project
### Prerequisites
- Android Studio installed on your development machine.

### Steps
1. Clone the repository from GitHub.
2. Open the project in Android Studio.
3. Build and run the project on your desired Android device or emulator.

