package dev.kavrin.koverse.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import dev.kavrin.koverse.database.Koverse_db
import dev.kavrin.koverse.di.util.CoroutineDispatchers
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.java.Java
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.Properties
import java.util.prefs.Preferences

actual val platformModule: Module = module {
    single { provideDispatchers() }
    single { provideHttpClientEngine() }
    single { provideSqlDriver() }
    single { provideMpSettings() }
}

private fun provideSqlDriver(): SqlDriver = JdbcSqliteDriver(
    url = JdbcSqliteDriver.IN_MEMORY,
    schema = Koverse_db.Schema,
    properties = Properties().apply { put("foreign_keys", "true") }
)

private fun provideHttpClientEngine(): HttpClientEngine = Java.create()

private fun provideMpSettings(): SuspendSettings = PreferencesSettings(Preferences.userRoot()).toSuspendSettings()


private fun provideDispatchers(): CoroutineDispatchers = CoroutineDispatchers(
    database = Dispatchers.IO,
    network = Dispatchers.IO,
    disk = Dispatchers.Default,
    ui = Dispatchers.Main
)