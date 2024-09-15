# Android Clean Architecture

An Android e-commerce shipping app replicating a real-world project environment, utilizing Clean Architecture, Kotlin, and Jetpack Compose with a clean code structure.

In addition, the project:
* Is **continually updated** to stay aligned with the latest technologies.
* Is **unit-tested** to ensure robust code quality.



https://github.com/user-attachments/assets/39eec4c7-7158-4915-bec5-dc65efffd4ea



## Features

1. **Search**: Users can search for products by name or ID, allowing them to quickly find specific items within the app.
2. **Filter**: Users can filter products based on categories.
3. **Connectivity Status**: Displays a banner to inform users of their current connectivity status.
4. **Auto Sync**: Synchronization and caching are managed to provide a smooth user experience.
5. **Favorites**: Users can add products to a favorites list for easy access later.
6. **Pull to Refresh**: Users can refresh content by pulling down on the screen.

## Motivation Behind the App
This repository was created with the intention of sharing knowledge, stepping outside of my comfort zone, and using it as a platform to implement new challenges and ideas.

## Product Mock Server API

**API:** [Product Mock Server](https://fakestoreapi.com/)

This API serves as the primary data source for the app, providing a mock database of product information. It mimics real-world data operations such as fetching, updating, and managing product data.

## 💡 Architectural Insights
Architecture is **dynamic** and **ever-evolving**. There are always several solutions to any given problem, and what works best depends on the specific requirements and constraints of your project.

![Architecture Diagram](https://user-images.githubusercontent.com/20803775/214686254-9405504c-05d2-417e-9cf5-669a1a57e8a6.png)

## Clean Architecture

The core principles of the Clean Architecture approach are summarized as follows:

1. **Layered Application Code**: The application code is separated into layers that define the separation of concerns.
2. **Strict Dependency Rule**: Each layer can only interact with the layers below it.
3. **Generic Code at Lower Layers**: The lower layers handle policies and rules, while the upper layers handle implementation details like databases, network managers, and UI.

![Clean Architecture Image](https://raw.githubusercontent.com/AliAsadi/Android-Clean-Architecture/master/screenshot/architecture0.png)

## Built With 🛠
- **[Kotlin](https://kotlinlang.org/)**: First-class and official programming language for Android development.
- **[Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)**: For asynchronous programming and more.
- **[Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)**: A cold asynchronous data stream that sequentially emits values and completes with success or failure.
- **[StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)**: A LiveData replacement for handling state updates.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: A modern toolkit for building native UIs.
- **[Android Architecture Components](https://developer.android.com/topic/libraries/architecture)**: Libraries to design robust, testable, and maintainable apps.
  - **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)**: Stores UI-related data that survives configuration changes.
  - **[SavedStateHandle](https://developer.android.com/reference/androidx/lifecycle/SavedStateHandle)**: A handle to saved state passed to `ViewModel`.
  - **[Navigation Compose](https://developer.android.com/guide/navigation/navigation-getting-started)**: For easier navigation between composables.
  - **[Room](https://developer.android.google.cn/jetpack/androidx/releases/room)**: Provides an abstraction layer over SQLite for easier and more robust database access.

- **Dependency Injection**:
  - **[Hilt](https://dagger.dev/hilt)**: A simpler way to integrate Dagger dependency injection into Android applications.
- **[Retrofit](https://square.github.io/retrofit/)**: A type-safe HTTP client for Android and Java.
- **[Mockito](https://github.com/mockito/mockito)**: For mocking and unit testing.

## 💎 Code Style

The code style in this project is maintained using [Detekt](https://detekt.dev/).

**To run a style check:**

```bash
./gradlew detektTask

## Contributing
Let me know if you need any further adjustments!
