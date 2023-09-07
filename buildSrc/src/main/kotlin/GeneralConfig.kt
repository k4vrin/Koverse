object GeneralConfig {
    const val PROJECT_ID = "dev.kavrin.koverse"
    const val VERSION = "0.0.1"
    fun getVersion(type: AppType) = when (type) {
        AppType.Server -> "0.1.0"
        AppType.Android -> "0.1.0"
        AppType.iOS -> TODO()
    }
}

enum class AppType {
    Server, Android, iOS
}