# Products App

This is an Android project split into modules and flavors. The goal was to separate logic properly and give each flavor its own responsibility.

## Project structure

- `app/`: main module with UI and app setup
- `domain/`: business logic and use cases
- `data/`: API + Room + repository implementation

### Flavors

There are 2 product flavors defined:

- `form`: shows a form screen with multiple input validations
- `list`: loads a paged list of products from an API and local DB. When the user click on item list it should open details screen of the item list.

You can switch between flavors in Android Studio using the **Build Variants** tool window.

## How to run it

Clone the project:

```bash
git clone https://github.com/felipeajc/products.git
cd products
```

---

## How to Build & Run

Make sure to have Java 17 configured (see `gradle.properties`).

### Install dependencies
```bash
./gradlew clean build
```

### Run specific flavor
```bash
./gradlew :app:installListDebug
# or
./gradlew :app:installFormDebug
```

---

Then open it in Android Studio and wait for Gradle sync.

To run a specific flavor:

- `formDebug` → Form screen with validation
- `listDebug` → Product list with paging

## What’s inside

### Compose

UI is built 100% with Jetpack Compose.

Stuff like:

- `SubcomposeAsyncImage` from Coil for image loading with placeholder/error states
- Scrollable form and list screens
- Custom layout sizing (e.g. images using 30–40% of screen height)
- Material3 components

### Data layer

- **Room** for local DB
- **Retrofit** for network requests
- Paging source that loads from local or remote
- Hilt modules in `data/di`

### Domain layer

- Use cases wrap logic like fetching products or validating fields
- Domain models decouple UI from data layer

### Architecture

- Clean Architecture
- MVVM (ViewModel + StateFlow)
- Clean module separation (domain doesn’t know about data or UI)
- Dependency injection with Hilt

## Running tests

Basic unit tests are in place. You can run them with:

```bash
./gradlew test
```

For UI/instrumented tests (if needed):

```bash
./gradlew connectedAndroidTest
```

## Why flavors?

Each flavor has different resources (like `strings.xml`) and launch Activities. This was useful for testing different flows in isolation without needing runtime switches or extra config.

## Some tech choices

- `Dispatchers.IO` used for data layer
- `stateIn`, `debounce`, `flatMapLatest` used to manage UI state with Flow
- Image sizes dynamically calculated based on screen size
- Custom error messages for each form field
- Kotlin docs

## Dev setup

- Kotlin 2.0.21
- Android Gradle Plugin 8.1.1
- Compile SDK 36, min SDK 26
- OpenJDK 17

## Dependencies

- Jetpack Compose (Material3)
- Coil
- Retrofit + Gson
- Room
- Hilt (DI)
- Paging 3
- Coroutines
- Kotlin Flow

## Future work
## To-Do
- Fix issue where the software keyboard overlaps form fields. Need to improve scrolling or use `imePadding()` where necessary.

## Author

Felipe Carvalho  
https://github.com/felipeajc

## Notes

This is just a test project for a coding challenge. No license or production-level features here.

