# Product Requirements Document — Study App

## Product identity
Premium, calm, distraction-free Android utility. Original identity (not Regain). Dark-mode first (obsidian #0D0E11, surfaces #121318). Emerald accent #00E676. Error #EF4444, caution #F59E0B, secondary text #9CA3AF. Roboto/bundled sans-serif; tabular/monospaced digits for countdowns. Rounded cards, restrained borders, generous spacing, minimal shadows. Short horizontal onboarding transitions, gentle spring selection feedback, timer transitions, subtle haptics. Reduced-motion option. Accessible touch targets, content descriptions, contrast, scalable text. Free alternate themes + nature/Lo-Fi animated backgrounds as performance permits. No mandatory mascot; minimalist digital dashboard. Never shame users for breaks.

## Mandatory features (14 core + extensions)
1. First-launch onboarding wizard (Screen A-F), resumable.
2. Main nav: Home/Command Center, Planner, Blocks, Insights, Focus Groups, Music/Themes, Settings.
3. Command Center/Home (streak, goal/subject, large monospaced countdown, dial in 5-min increments, Strict Mode status, restricted app count, today's goal/progress, Start focus, session UI, emergency exit, persisted state, monotonic time, reboot handling, foreground service with notification).
4. Focus timer & Pomodoro (countdown, stopwatch, presets/custom durations, rounds, auto-transition notifications, no duplicate timers/records, Room history, background/process death/reboot/time-change/notification-denial handling, unit tests).
5. App blocking engine (select supported apps, named profiles, focus + scheduled blocking, BlockingEngine abstraction, idempotent, informed consent for Accessibility, no secret collection, branded blocking screen with Return to study, real interception events, degraded status, preserve emergency/system access).
6. Granular feed filtering (YouTube Shorts via in-app browser/URL filtering; Instagram Reels/TikTok/Reddit/etc marked supported/partial/experimental/unsupported; whitelisted routes; optional focused in-app browser with domain allowlist; no unauthorized proxy/cert).
7. YouTube Study Mode (educational allowlist via channels/links/playlists/domains; controlled in-app route; restrict distracting nav/recommendations/Shorts/comments where possible; explain limits; test approved/disallowed/invalid links, playback failures, handoffs; no auth/paywall bypass).
8. Website blocker (add/remove domains/URLs, optional categories, domain rules in supported routes, subdomain/normalization/redirect/IDN/malformed handling, explain scope).
9. Strict Mode & Focus Guard (session policy choice, confirmation, prevent casual cancel, no absolute uninstall prevention, device-owner as separate opt-in, never block emergency, test revocation/force-stop/reboot/recovery; Focus Guard detects restricted app attempts, optional vibration/reminder, configurable sensitivity, local events).
10. Task planner & schedules (weekly timetable, day/start/end/subject/profile/repeat, at least one free recurring schedule, WorkManager, exact alarms only when justified+permission, TZ/reboot/overlap/missed handling, next session display).
11. Screen-time insights (Usage Access bridge, supported usage stats, daily/weekly totals, categories, focus time, sessions, blocking triggers, weekly continuity bars/charts, measured vs estimates, no server upload by default, clear local analytics).
12. Home-screen widget (Glance: session state, remaining time, today's goal, theme accent; buttons open screens/trigger supported actions; realistic update expectations; test placement/resize/refresh/reboot/theme).
13. Focus Groups & social streaks (real online: group create/join/leave/invite, real identities, presence with expiry/heartbeat, session status, optional group sessions, leaderboards with verified records + scoring rules, server-side auth/validation, anti-manipulation, report/block/moderation, minimal profile data, protect minors; no fabricated data; honest setup state if no backend).
14. Focus music (local/offline ambient tracks where licensed; Lo-Fi/white noise/rain-nature/space ambience/calm non-lyrical; Media3; playback controls/volume/background/audio focus; licensed/original assets; no binaural cognitive claims).
15. Free themes & motion backgrounds (multiple free themes incl Lo-Fi/nature, static + lightweight animations, pause when not visible, reduced-motion/battery/perf options, core features theme-independent).
16. Data/privacy/security (Kotlin, Compose, Room, DataStore, WorkManager; offline-capable timer/planner/prefs/insights; backend only for shared online; no service-role secrets in APK; privacy disclosures, data deletion, error handling, permission status; no usage/accessibility upload without consent; logs free of tokens/creds/sensitive activity).

## Onboarding (Screen A-F)
- A Welcome: "Rescue Your Time"; purpose + permissions; "Set up my focus" / "Not now"; don't request all permissions at once.
- B Focus goal: single-select chips School/Boards, JEE, NEET, UPSC, Coding, Reading, Writing, Deep Work, General Study; personalize subjects/labels; preferences not performance claims.
- C Distraction selection: multi-select grid with recognizable icons (YouTube, Instagram, TikTok, Snapchat, Reddit, Facebook, gaming, streaming); licensed icons only; green border+checkmark selected; neutral unselected; enumerate installed apps via supported APIs; distinguish unsupported; save+editable.
- D Daily commitment dial: large readable number + slider/wheel; default 3h; sensible increments; no daily obligation implication; smooth animate + optional haptic; persist.
- E Permission bridge: explain each separately; lock icon + action to system settings/permission request; Usage Access only for insights/fg detection; Accessibility only if blocking design requires it with exact observation/data-use explanation; notifications for reminders/alerts; overlay only if used; no iOS-style universal Screen Time claim; status + retry after return; allow continue with unsupported capabilities marked.
- F Review & begin: summarize distractions, goal, daily target, exact blocking capabilities; editable; "Enter my focus dashboard"; don't show on every launch; revisit from Settings.
- Smooth horizontal transitions + subtle progress indicator. Save progress locally for resume.

## Execution order
1. Onboarding + design system
2. Home dashboard + reliable focus timer
3. Pomodoro, stopwatch, local session history
4. App selection + validated blocking engine
5. Planner + recurring schedules
6. Usage insights + widget
7. Website filtering + supported YouTube Study Mode
8. Focus Guard + Strict Mode hardening
9. Focus music + free themes
10. Real online focus groups, presence, leaderboards
11. Integration testing, accessibility review, battery tests, final hardening

## Build & delivery
Real native Android project + compatible Gradle Wrapper. Remote GitHub Actions build when local Android builds unavailable. Unit tests each milestone; instrumentation where practical. Upload debug APK artifact only after successful build. Compatible with repo branch/permissions. Never report build/test success without results. Maintain FEATURE_REGISTER.md statuses: not started, in progress, implemented, tested, known limitations. Update TEST_RESULTS.md and CHANGELOG.md after meaningful milestones.
