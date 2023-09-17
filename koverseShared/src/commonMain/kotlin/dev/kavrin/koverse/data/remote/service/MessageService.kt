package dev.kavrin.koverse.data.remote.service

import dev.kavrin.koverse.data.remote.dto.MessageDto
import dev.kavrin.koverse.domain.util.ApiResponse

interface MessageService {

    suspend fun getAllMessages(): ApiResponse<List<MessageDto>, String>

    companion object {
        // Emulator
        const val BASE_URL = "http://10.0.2.2:8080"
        // Real phone
//        Ipconfig getifaddr en0                                                    ─╯
//        192.168.1.7
//        const val BASE_URL = "http://192.168.1.7:8080"
    }

    sealed class EndPoints(val url: String) {
        data object GetAllMessages : EndPoints("$BASE_URL/messages")
    }
}