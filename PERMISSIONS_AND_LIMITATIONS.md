# Permissions and Limitations — Study App

## Permissions (requested individually with rationale)
- Usage Access — usage insights + supported foreground-app detection only.
- Accessibility — only if blocking design requires it; explain exactly what is observed and how data is used.
- Notifications — focus reminders and session alerts.
- Overlay — only if implementation uses an overlay and Android requires it.
- Exact alarms — only when justified + permission granted.

## Limitations
- No iOS-style universal Screen Time permission on Android.
- True app blocking limited by Android/OEM/Play policy; cannot block system apps or override user without consent.
- Accessibility service cannot secretly collect screen contents/credentials/messages.
- System-wide website filtering not claimed unless a supported, tested mechanism provides it.
- Cannot distinguish all feeds inside third-party native apps without evidence.
- Device-owner enforcement is separate opt-in provisioning mode, not silently enabled.
- Never block emergency functions or create unrecoverable lockout.
- Exact alarm/background execution not millisecond-precise.

## Data
- Usage/accessibility logs not uploaded without explicit informed consent.
- Logs free of tokens, credentials, sensitive activity.
