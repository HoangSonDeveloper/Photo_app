# Photo_app

## Architecture & Technical Choices
- Built with **Kotlin** and **Jetpack Compose** following the **MVVM** pattern.
- **Koin** for dependency injection.
- **Retrofit** + **OkHttp** for API calls.
- **Coil** for asynchronous images.

## Trade-offs & Time Constraints
- Simplistic error handling (Toast messages only) and no retry policies.

## Running the App
1. Clone the repository:
   ```bash
   git clone <repo_url>
   ```
2. Add your Pexels API key to `app/local.properties`:
   ```properties
   PEXELS_API_KEY=your_api_key_here
   ```
3. Sync Gradle, build, and run on an emulator or device (Android 5.0+).

## Future Improvements
- Add offline support (Room, DataStore, or cache-first strategy).
- Expand test coverage (Compose UI tests, ViewModel unit tests).
- Enhance UI/UX with animations, dynamic theming, and accessibility improvements.