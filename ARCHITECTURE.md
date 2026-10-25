# Architecture — Study App

## Stack
- Language: Kotlin
- UI: Jetpack Compose (Material 3)
- Build: Gradle Kotlin DSL + Android Gradle Plugin; compatible Gradle Wrapper
- Min SDK: TBD; Target SDK: latest stable
- Storage: Room (sessions, rules, groups cache), DataStore (prefs/onboarding)
- Scheduling: WorkManager (inexact, reboot-aware); exact alarms only when justified + permission
- Audio: Media3
- Widget: Glance
- Backend (optional, real): for Focus Groups only; no service-role secrets in APK

## Modules
- `app` single module initially; split by feature later.

## Layers
- UI: Compose screens + ViewModels
- Domain: use cases
- Data: repositories, Room DAOs, DataStore

## Key abstractions
- `BlockingEngine` — pluggable detection/interception strategies
- `SessionManager` — monotonic time, persisted state, reboot recovery
- `UsageStatsProvider` — supported usage APIs only

## Online
- Server-side auth/validation for groups; minimal profile data; no precise location/school/schedule exposure.
