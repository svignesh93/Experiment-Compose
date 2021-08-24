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
package com.litekite.compose.constraintlayout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atMost
import com.litekite.compose.theme.ComposeTheme

@Composable
fun ComposeConstraintUI() {
    ConstraintLayout {

        // Create references for the composables to constrain
        val (button, text1, text2, constraintBox) = createRefs()

        Button(
            onClick = { },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, 16.dp)
                centerHorizontallyTo(parent)
            }
        ) {
            Text("Button")
        }

        // Barrier pos is flexible based on the refs
        val barrier = createBottomBarrier(button, margin = 6.dp)

        Text(
            text = "Text1",
            modifier = Modifier.constrainAs(text1) {
                top.linkTo(barrier, 10.dp)
                // Centers Text horizontally in the ConstraintLayout
                centerHorizontallyTo(parent)
            }
        )

        // Guideline pos is fixed
        val guideline = createGuidelineFromEnd(0.5F)

        Text(
            text = "Text2",
            modifier = Modifier.constrainAs(text2) {
                // Coercing with at-most 100dp
                width = Dimension.preferredWrapContent.atMost(100.dp)
                top.linkTo(text1.bottom, 16.dp)
                end.linkTo(guideline)
                // Centers Text horizontally in the ConstraintLayout
                centerHorizontallyTo(parent)
            }
        )

        BoxWithConstraints(
            modifier = Modifier.constrainAs(constraintBox) {
                top.linkTo(text2.bottom)
            }
        ) {
            val constraints = if (maxWidth < maxHeight) {
                decoupledConstraints(margin = 16.dp) // Portrait constraints
            } else {
                decoupledConstraints(margin = 32.dp) // Landscape constraints
            }

            ConstraintLayout(constraints) {
                Button(
                    onClick = { },
                    modifier = Modifier.layoutId("button2")
                ) {
                    Text(text = "Button3")
                }
            }
        }
    }
}

private fun decoupledConstraints(margin: Dp) = ConstraintSet {
    val button = createRefFor("button2")
    constrain(button) {
        top.linkTo(parent.top, margin = margin)
        bottom.linkTo(parent.bottom, margin)
    }
}

@Preview
@Composable
fun PreviewConstraintUI() {
    ComposeTheme {
        ComposeConstraintUI()
    }
}
