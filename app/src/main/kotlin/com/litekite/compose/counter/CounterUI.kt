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
package com.litekite.compose.counter

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.litekite.compose.base.ComposeApp

@Composable
fun Counter() {
    val counter = remember { mutableStateOf(0) }
    CounterButton(counter.value) { count ->
        // This is side effect / state hoist
        // that should be handled by the caller of this composable.
        counter.value = count
    }
}

@Composable
fun CounterButton(count: Int, updateCounter: (Int) -> Unit) {
    Button(
        onClick = { updateCounter(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) {
                MaterialTheme.colors.primaryVariant
            } else {
                MaterialTheme.colors.primary
            }
        ),
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(10.dp)
    ) {
        Text(text = "Hello $count")
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
fun PreviewCounter() {
    ComposeApp {
        Counter()
    }
}
