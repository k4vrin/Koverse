package dev.kavrin.koverse.android.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kavrin.koverse.android.presentation.theme.KoverseTheme
import dev.kavrin.koverse.android.presentation.theme.padding
import dev.kavrin.koverse.domain.model.entity.Message
import java.text.DateFormat
import java.util.Date

@Composable
fun ChatContent(
    state: ChatContract.State,
    event: (ChatContract.Event) -> Unit,
    messageState: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {

            item {
                Spacer(modifier = Modifier.height(MaterialTheme.padding.smallLarge))
            }
            items(
                items = state.messages
            ) { message ->

                val isOwnMessage = message.username == state.username
                val primColor = MaterialTheme.colorScheme.primary
                val secColor = MaterialTheme.colorScheme.secondary
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = if (isOwnMessage) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Column(
                        modifier = Modifier
                            .width(200.dp)
                            .background(
                                color = if (isOwnMessage) primColor else secColor,
                                shape = MaterialTheme.shapes.medium
                            )
                            .drawBehind {
                                val cornerRadius = 10.dp.toPx()
                                val triangleHeight = 20.dp.toPx()
                                val triangleWidth = 25.dp.toPx()
                                val trianglePath = Path().apply {
                                    if (isOwnMessage) {
                                        moveTo(size.width, size.height - cornerRadius)
                                        lineTo(size.width, size.height + triangleHeight)
                                        lineTo(
                                            size.width - triangleWidth,
                                            size.height - cornerRadius
                                        )
                                        close()
                                    } else {
                                        moveTo(0f, size.height - cornerRadius)
                                        lineTo(0f, size.height + triangleHeight)
                                        lineTo(triangleWidth, size.height - cornerRadius)
                                        close()
                                    }
                                }
                                drawPath(
                                    path = trianglePath,
                                    color = if (isOwnMessage) primColor else secColor
                                )
                            }
                            .padding(MaterialTheme.padding.small)
                    ) {
                        Text(
                            text = message.username,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = message.text,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = message.formattedTime,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .align(Alignment.End)
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.padding.smallLarge))
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = messageState,
                onValueChange = { event(ChatContract.Event.MessageChange(it)) },
                placeholder = {
                    Text(
                        text = "Enter a message"
                    )
                },
                modifier = Modifier
                    .weight(1f)
            )
            IconButton(onClick = {event(ChatContract.Event.SendMessage)}) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "send message"
                )
            }
        }

    }

}

@Preview()
@Composable
private fun ChatContentPreview() {
    KoverseTheme {
        ChatContent(
            state = ChatContract.State(
                username = "Kavrin",
                messages = listOf(
                    Message(
                        text = "Hello",
                        formattedTime = DateFormat
                            .getDateInstance(DateFormat.DEFAULT)
                            .format(Date()),
                        username = "Kavrin"
                    ),
                    Message(
                        text = "Hi",
                        formattedTime = DateFormat
                            .getDateInstance(DateFormat.DEFAULT)
                            .format(Date()),
                        username = "Ali"
                    )
                )
            ),
            event = {},
            messageState = "Hell"
        )
    }
}