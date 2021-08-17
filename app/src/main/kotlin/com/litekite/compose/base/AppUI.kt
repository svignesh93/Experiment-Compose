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
package com.litekite.compose.base

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.litekite.compose.R
import com.litekite.compose.theme.ComposeTheme

@Composable
fun ComposeApp(content: @Composable () -> Unit) {
    ComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun AppBar(title: String, bodyContent: @Composable (Modifier) -> Unit, actionClick: () -> Unit) {
    Scaffold(topBar = {
        // This is a Compose Slot and it can be customized with any composable than TopAppBar
        TopAppBar(
            title = {
                Text(text = title, maxLines = 1)
            },
            actions = {
                IconButton(
                    onClick = { actionClick() }
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_user_profile),
                    contentDescription = "AppBar Icon",
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(CircleShape)
                )
            }
        )
    }) { innerPadding -> bodyContent(Modifier.padding(innerPadding)) }
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
fun PreviewAppBar() {
    ComposeTheme {
        AppBar(title = "Page title", { }) { }
    }
}
