package com.aruuu.app.domain.model

/**
 * Domain models for ARUUU app vault.
 */

// ════════════════════════════════════════════════════════════════════════════════════
// Authentication
// ════════════════════════════════════════════════════════════════════════════════════

enum class AuthMethod {
    PIN, PASSWORD, PATTERN, BIOMETRIC
}

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

data class UnlockResult(
    val success: Boolean,
    val message: String = "",
    val failedAttempts: Int = 0,
    val lockoutRemaining: Long = 0L,
)

// ════════════════════════════════════════════════════════════════════════════════════
// App Info
// ════════════════════════════════════════════════════════════════════════════════════

data class AppInfo(
    val packageName: String,
    val appName: String,
    val versionName: String,
    val versionCode: Long,
    val isSystemApp: Boolean,
    val isHidden: Boolean = false,
    val isLocked: Boolean = false,
    val installedAt: Long,
)

// ════════════════════════════════════════════════════════════════════════════════════
// Vault Settings
// ════════════════════════════════════════════════════════════════════════════════════

data class VaultSettings(
    val primaryAuthMethod: AuthMethod = AuthMethod.PIN,
    val biometricEnabled: Boolean = false,
    val faceUnlockEnabled: Boolean = false,
    val autoLockDelaySeconds: Int = 30,
    val intruderSelfieEnabled: Boolean = true,
    val maxFailedAttempts: Int = 3,
    val disguiseModeEnabled: Boolean = false,
    val disguiseLabel: String = "Calculator",
    val hapticFeedback: Boolean = true,
    val showNotificationBadge: Boolean = false,
    val onboardingComplete: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val lastBackupTimestamp: Long = 0L,
)
