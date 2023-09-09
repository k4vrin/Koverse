package dev.kavrin.koverse.data.remote.service

import dev.kavrin.koverse.data.remote.dto.MessageDto
import dev.kavrin.koverse.data.util.safeRequest
import dev.kavrin.koverse.domain.util.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class MessageServiceImpl(
    private val client: HttpClient,
) : MessageService {
    override suspend fun getAllMessages(): ApiResponse<List<MessageDto>, String> {
        return client.safeRequest {
            method = HttpMethod.Get
            url(MessageService.EndPoints.GetAllMessages.url)
        }
    }
}