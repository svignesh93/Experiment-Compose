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
package com.litekite.compose.state

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.litekite.compose.theme.ComposeTheme

@Composable
fun ComposeContent(vm: StatefulVM = viewModel()) {
    val name: String by vm.name.collectAsState()
    ComposeBody(name = name, onNameChange = { changedName -> vm.onNameChange(changedName) })
}

/**
 * @param name (state) current text to display
 * @param onNameChange (event) request that text change
 */
@Composable
fun ComposeBody(name: String, onNameChange: (String) -> Unit) {
    Column {
        Text(text = name)
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(text = "name") }
        )
    }
}

@Preview
@Composable
fun PreviewComposeBody() {
    ComposeTheme {
        ComposeBody(name = "Compose", onNameChange = { })
    }
}
