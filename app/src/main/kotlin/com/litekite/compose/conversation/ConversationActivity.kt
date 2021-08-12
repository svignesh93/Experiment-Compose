/*
 * Copyright 2021 LiteKite Startup. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.litekite.compose.conversation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.litekite.compose.R
import com.litekite.compose.theme.ComposeTheme

/**
 * This is the conversation sample from the jetpack compose pathway
 *
 * @see [Jetpack Compose basics]
 * @see [https://developer.android.com/courses/pathways/compose]
 */
class ConversationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }

    @Composable
    fun Conversation(msgList: List<Message>) {
        // LazyColumn and LazyRow only deals with elements that are visible on the screen
        // LazyColumn places elements vertically and LazyRow places elements horizontally
        LazyColumn {
            items(msgList) { msg ->
                MessageCard(msg)
            }
        }
    }

    @Composable
    fun MessageCard(msg: Message) {
        // Column arranges elements vertically
        // Row arranges elements horizontally
        // Box stacks elements, one above the previous elements
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentHeight(align = Alignment.Top)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_user_profile),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            var isExpanded by remember { mutableStateOf(false) }

            val surfaceColor by animateColorAsState(
                targetValue = if (isExpanded) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.surface
                }
            )

            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text = msg.author,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = surfaceColor,
                    elevation = 4.dp,
                    modifier = Modifier.animateContentSize()
                ) {
                    Text(
                        text = msg.body,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }

    @Preview(
        name = "Light Mode",
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        showBackground = true,
        showSystemUi = true
    )
    @Preview(
        name = "Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        showSystemUi = true
    )
    @Composable
    fun PreviewConversation() {
        ComposeTheme {
            Conversation(SampleData.conversationSample)
        }
    }

    @Preview(
        name = "Light Mode",
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        showBackground = true
    )
    @Preview(
        name = "Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true
    )
    @Composable
    fun PreviewMessageCard() {
        ComposeTheme {
            MessageCard(SampleData.conversationSample[2])
        }
    }
}
