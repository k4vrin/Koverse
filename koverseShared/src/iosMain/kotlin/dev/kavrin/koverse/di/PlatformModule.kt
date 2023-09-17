package dev.kavrin.koverse.di


import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import dev.kavrin.koverse.database.Koverse_db
import dev.kavrin.koverse.di.util.CoroutineDispatchers
import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val platformModule: Module = module {

    single { provideDispatchers() }
    single { provideHttpClientEngine() }
    single { provideSqlDriver() }
    single { provideMpSettings(dispatchers = get()) }
}

private fun provideDispatchers(): CoroutineDispatchers = CoroutineDispatchers(
    database = Dispatchers.IO,
    network = Dispatchers.IO,
    disk = Dispatchers.Default,
    ui = Dispatchers.Main
)

private fun provideSqlDriver(): SqlDriver = NativeSqliteDriver(
    schema = Koverse_db.Schema,
    name = "koverse.db",
    onConfiguration = { conf ->
        conf.copy(
            extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
        )
    }
)

/**
 * Darwin does not support connection time out.
 * **See** [Timeout](https://ktor.io/docs/timeout.html#limitations)
 */
private fun provideHttpClientEngine(): HttpClientEngine = Darwin.create()

private fun provideMpSettings(dispatchers: CoroutineDispatchers): SuspendSettings {
    val userDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults
    return NSUserDefaultsSettings(userDefaults).toSuspendSettings(dispatcher = dispatchers.database)
}
