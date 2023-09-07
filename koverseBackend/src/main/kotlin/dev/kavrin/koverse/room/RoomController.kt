package dev.kavrin.koverse.room

import dev.kavrin.koverse.data.db.MessageDataSource
import dev.kavrin.koverse.data.model.Message
import io.ktor.websocket.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.containsKey(username)) {
            throw MemberAlreadyExistException()
        }
        members[username] = Member(username, sessionId, socket)
    }

    suspend fun sendMessage(senderUsername: String, message: String) = coroutineScope {
        val messageEntity = Message(
            text = message,
            userName = senderUsername,
            timestamp = System.currentTimeMillis()
        )
        val insertJob = launch { messageDataSource.insertMessage(messageEntity) }
        val parsedMessage = Json.encodeToString(messageEntity)

        members.values.forEach { member ->
            member.socket.send(Frame.Text(parsedMessage))
        }
        insertJob.join()
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun tryDisconnect(username: String) {
        members[username]?.socket?.close()
        if (members.containsKey(username)) members.remove(username)
    }
}