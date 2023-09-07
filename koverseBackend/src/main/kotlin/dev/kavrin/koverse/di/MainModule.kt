package dev.kavrin.koverse.di

import dev.kavrin.koverse.data.db.MessageDataSource
import dev.kavrin.koverse.data.db.MongoMessageDataSource
import dev.kavrin.koverse.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*

val mainModule = module {
    single { provideDatabase() }
    single { provideMessageDataSource(db = get()) }
    single { provideRoomController(messageDataSource = get()) }
}

fun provideRoomController(messageDataSource: MessageDataSource): RoomController =
    RoomController(messageDataSource)

fun provideMessageDataSource(db: CoroutineDatabase): MessageDataSource = MongoMessageDataSource(db)

fun provideDatabase(): CoroutineDatabase =
    KMongo.createClient()
        .coroutine
        .getDatabase("koverse_db")

