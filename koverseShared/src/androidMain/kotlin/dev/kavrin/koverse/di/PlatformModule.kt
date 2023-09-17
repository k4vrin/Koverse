package dev.kavrin.koverse.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.datastore.DataStoreSettings
import dev.kavrin.koverse.database.Koverse_db
import dev.kavrin.koverse.di.util.CoroutineDispatchers
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { provideDispatchers() }
    single { provideHttpClientEngine() }
    single { provideDataStore(context = get(), dispatchers = get()) }
    single { provideSqlDriver(context = get()) }
    single { provideMpSettings(dataStore = get()) }
}

private fun provideSqlDriver(
    context: Context,
): SqlDriver = AndroidSqliteDriver(
    schema = Koverse_db.Schema,
    context = context,
    name = "koverse.db",
    callback = object : AndroidSqliteDriver.Callback(Koverse_db.Schema) {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            db.setForeignKeyConstraintsEnabled(true)
        }
    }
)

private fun provideHttpClientEngine(): HttpClientEngine = OkHttp.create()

private fun provideMpSettings(dataStore: DataStore<Preferences>): SuspendSettings =
    DataStoreSettings(dataStore)

private fun provideDispatchers(): CoroutineDispatchers = CoroutineDispatchers(
    database = Dispatchers.IO,
    network = Dispatchers.IO,
    disk = Dispatchers.Default,
    ui = Dispatchers.Main
)

private fun provideDataStore(context: Context, dispatchers: CoroutineDispatchers): DataStore<Preferences> = PreferenceDataStoreFactory.create(
    scope = CoroutineScope(dispatchers.database + SupervisorJob()),
    produceFile = {context.applicationContext.preferencesDataStoreFile("koverse_ds")}
)