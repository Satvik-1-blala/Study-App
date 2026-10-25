# Architecture — Study App

## Stack
- Language: Kotlin
- UI: Jetpack Compose
- Build: Gradle (Kotlin DSL), Android Gradle Plugin
- Min SDK: TBD, Target SDK: latest stable

## Modules (planned)
- `app` — single module initially; split later if needed.

## Layers
- UI: Compose screens + ViewModels
- Domain: use cases
- Data: Room (local), repositories

## State management
- ViewModel + StateFlow; Compose collects state.

## Offline-first
- All core data stored locally (Room + DataStore).
