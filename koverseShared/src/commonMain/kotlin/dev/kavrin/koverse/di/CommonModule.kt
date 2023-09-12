package dev.kavrin.koverse.di

import app.cash.sqldelight.db.SqlDriver
import dev.kavrin.koverse.data.remote.service.ChatSocketService
import dev.kavrin.koverse.data.remote.service.ChatSocketServiceImpl
import dev.kavrin.koverse.data.remote.service.MessageService
import dev.kavrin.koverse.data.remote.service.MessageServiceImpl
import dev.kavrin.koverse.data.repository.ChatRepositoryImpl
import dev.kavrin.koverse.database.Koverse_db
import dev.kavrin.koverse.domain.repository.ChatRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val commonModule = module {

    single { provideSqlDatabase(sqlDriver = get()) }
    single { provideHttpClient(engine = get()) }
    single { provideMessageService(client = get()) }
    single { provideChatSocketService(client = get()) }

}

private fun provideMessageService(client: HttpClient): MessageService = MessageServiceImpl(client)
private fun provideChatSocketService(client: HttpClient): ChatSocketService =
    ChatSocketServiceImpl(client)

private fun provideChatRepository(
    messageService: MessageService,
    chatSocketService: ChatSocketService,
): ChatRepository = ChatRepositoryImpl(
    messageService = messageService,
    chatSocketService = chatSocketService
)


private fun provideSqlDatabase(
    sqlDriver: SqlDriver,
): Koverse_db = Koverse_db(
    driver = sqlDriver,
)

private fun provideHttpClient(
    engine: HttpClientEngine,
): HttpClient = HttpClient(engine = engine) {

    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                co.touchlab.kermit.Logger.withTag("ktor").v(message)
            }
        }
    }

    install(ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            },
            contentType = ContentType.Application.Any
        )
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 20000
        // Darwin does not support connection time out
        connectTimeoutMillis = 20000
        socketTimeoutMillis = 20000
    }

    install(WebSockets) {

    }

    defaultRequest {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
        // host has a bug on Darwin client
//        url {
//            protocol = URLProtocol.HTTPS
//            host = HttpRoutes.Host
//        }
    }

    /**
     * Throws error for non-2xx responses
     *
     * **See Also** [SafeRequest](com.walletline.data.util.safeRequest)
     * **See Also** [Response validation](https://ktor.io/docs/response-validation.html)
     */
    expectSuccess = true
}
