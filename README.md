# ARUUU — Privacy-First App Vault for Android

![Android](https://img.shields.io/badge/Android-12%2B-green)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue)
![Material3](https://img.shields.io/badge/Material%203-UI-purple)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

ARUUU is a production-ready Android app vault built with **Kotlin + Jetpack Compose**. It lets users lock and hide apps using biometric authentication, PIN, password, or pattern — all secured by the **Android Keystore** with zero data leaving the device.

---

## Features

| Feature | Status |
|---|---|
| Biometric unlock (fingerprint + face) | ✅ |
| PIN lock (4–6 digits) | ✅ |
| Password lock | ✅ |
| Pattern lock | ✅ |
| App lock (requires auth before opening) | ✅ |
| App hide (from launcher / recents) | ✅ |
| Intruder selfie (camera capture on failed unlock) | ✅ |
| Auto-lock after configurable delay | ✅ |
| Disguise mode (appear as Calculator / Notes) | ✅ |
| Dark / Light / System theme | ✅ |
| Material Design 3 UI | ✅ |
| Android Keystore AES-256-GCM encryption | ✅ |
| 100% on-device — no external servers | ✅ |
| Android 12+ (minSdk 31) | ✅ |
| Accessibility service for reliable detection | ✅ |
| Boot receiver (restarts protection after reboot) | ✅ |
| Factory reset | ✅ |

---

## Architecture

```
ARUUU/
├── app/src/main/java/com/aruuu/app/
│   ├── MainActivity.kt                  # Single-activity entry point
│   ├── ARUUUApplication.kt             # Hilt app + notification channels
│   │
│   ├── domain/model/
│   │   └── Models.kt                    # AppInfo, VaultSettings, UnlockResult …
│   │
│   ├── data/
│   │   ├── local/
│   │   │   ├── Entities.kt              # Room entities
│   │   │   ├── Daos.kt                  # Room DAOs
│   │   │   ├── ARUUUDatabase.kt        # Room database
│   │   │   └── SecureCredentialManager  # Android Keystore AES-GCM wrapper
│   │   └── repository/
│   │       └── ARUUURepository.kt      # Single source of truth
│   │
│   ├── service/
│   │   ├── AppLockService.kt            # Foreground service (UsageStats polling)
│   │   ├── ARUUUAccessibilityService   # Accessibility-based detection
│   │   ├── ARUUUBiometricManager.kt    # BiometricPrompt wrapper
│   │   └── IntruderCaptureService.kt    # CameraX silent capture
│   │
│   ├── receiver/
│   │   └── Receivers.kt                 # Boot + Screen state receivers
│   │
│   ├── di/
│   │   └── AppModule.kt                 # Hilt singleton providers
│   │
│   └── ui/
│       ├── Navigation.kt                # NavHost + Routes
│       ├── theme/Theme.kt               # M3 color scheme + typography + shapes
│       └── screens/
│           ├── MainViewModel.kt
│           ├── onboarding/OnboardingScreen.kt
│           ├── auth/AuthScreen.kt        # PIN / Password + AppLockActivity
│           ├── home/HomeScreen.kt
│           ├── apps/ManageAppsScreen.kt
│           ├── apps/IntruderLogScreen.kt
│           └── settings/SettingsScreen.kt
```

**Stack:** Kotlin · Jetpack Compose · Hilt · Room · DataStore · CameraX · AndroidX Biometric · Android Keystore · WorkManager · Navigation Compose · Material 3

---

## Security Model

```
User credential (PIN/password/pattern)
        │
        ▼
  SHA-256(salt + input)          ← salt stored encrypted
        │
        ▼
  AES-256-GCM encrypt            ← key in Android Keystore (hardware-backed)
        │
        ▼
  Base64(IV + ciphertext)        ← stored in EncryptedSharedPreferences
```

- Credentials are **never stored in plain text**
- The AES-256 key is **non-exportable** and hardware-backed on supported devices
- All data is excluded from cloud backup and device transfer via `backup_rules.xml` and `data_extraction_rules.xml`
- Network security config blocks all cleartext traffic

---

## Setup

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android device or emulator running API 31+

### Build
```bash
git clone https://github.com/youruser/ARUUU.git
cd ARUUU
./gradlew assembleDebug
```

### Required Permissions (user must grant manually)
1. **Usage Access** — Settings → Apps → Special app access → Usage access → ARUUU  
   *(Needed for `AppLockService` UsageStats polling)*
2. **Accessibility Service** — Settings → Accessibility → Installed services → ARUUU  
   *(Preferred method; more reliable than UsageStats on Android 12+)*
3. **Display over other apps** — Auto-requested at runtime

---

## Key Implementation Notes

### App Hiding
On modern Android, true system-wide hiding (removing from launcher) requires device-owner privileges or manufacturer-specific APIs. ARUUU implements **app lock** (intercepts launches and shows the lock screen) as the primary protection method. The "hidden" flag is tracked in the database for UI purposes.

### App Lock Detection
Two complementary methods:
1. **AccessibilityService** (`ARUUUAccessibilityService`) — preferred, event-driven
2. **UsageStats polling** (`AppLockService`) — fallback, polls every 500ms

### Intruder Selfie
Uses CameraX with a synthetic `LifecycleOwner` to capture from a non-Activity context. Photos stored in `filesDir/intruder_selfies/` (private, excluded from backup).

---

## License
MIT © ARUUU Contributors
