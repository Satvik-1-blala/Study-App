# Test Plan — Study App

## Unit tests
- Timer calculations and state transitions (monotonic time, pause/resume, reboot recovery).
- BlockingEngine rules, idempotency, degraded status.
- URL/domain normalization, subdomains, IDN, malformed URLs.
- Session deduplication.

## Instrumented tests
- Compose onboarding flow, navigation, widget placement/resize/refresh/reboot/theme.
- Room migrations.
- Foreground service + notification permission denial.

## Manual / device
- Real-device testing for each supported blocking target and app version.
- Accessibility, reduced motion, scalable text, contrast.
- Battery/performance with animated backgrounds.

## CI
- GitHub Actions: build debug APK + run unit tests on each milestone; upload artifact only on success.
- Report results in TEST_RESULTS.md.
