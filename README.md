News App Setup Instructions
Overview
This is a News App developed for CIS4034-N Mobile App Development. It uses Kotlin, Jetpack Compose, Room, Retrofit, Hilt, and WorkManager to fetch and display news from the News API.

Prerequisites
Android Studio (latest version)
JDK 17
Android Emulator or physical device (API 21+)
News API key (included in app/build.gradle for debug)
Setup Instructions
Clone the repository: git clone <your-github-repo-url>
Open the project in Android Studio.
Sync the project with Gradle.
Ensure the News API key is set in app/build.gradle (API_KEY field).
Build and run the app on an emulator or device.
Running the App
Starts with a splash screen (MainActivity.kt).
Features:
Top headlines with pagination (TopHeadlineScreen.kt, PaginationViewmodel.kt)
Search news (SearchNewsScreen.kt)
Country selection (CountryListScreen.kt)
Language selection (LanguageListScreen.kt)
News sources (SourcesScreen.kt)
Offline caching with Room (AppRoomDataBase.kt)
Periodic updates via WorkManager (NewsWorkManager.kt)
Dependencies
Jetpack Compose
Retrofit
Room
Hilt
Paging
WorkManager
Coil (for images)
Notes
Requires internet permission (AndroidManifest.xml).
GDPR-compliant: no personal user data stored.
Screen postings in Documentation/ScreenRecordings/.
Agile documentation in Documentation/Sprint_Documentation.xlsx.
