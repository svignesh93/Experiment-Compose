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
package com.litekite.compose.customlayout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.litekite.compose.theme.ComposeTheme

/**
 * Custom Layout Modifier that includes padding from the text baseline
 */
fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) = this.then(
    layout { measurable, constraints ->

        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        // Throws an [IllegalStateException] if the value is false.
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val placeableHeight = placeable.height + placeableY

        layout(placeable.width, placeableHeight) {
            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun CustomColumn(modifier: Modifier, content: @Composable () -> Unit) = Layout(
    modifier = modifier,
    content = content
) { measurables, constraints ->

    // Don't constrain child views further, measure them with given constraints
    // list of measured children
    val placeables = measurables.map { measurable ->
        // Measure each child
        measurable.measure(constraints)
    }

    // Track the y co-ord we have placed children up to
    var yPosition = 0

    // Set the size of the layout as big as it can
    layout(constraints.maxWidth, constraints.maxHeight) {
        // Place children in the parent layout
        placeables.forEach { placeable ->
            placeable.placeRelative(0, yPosition)

            // Record the y co-ord placed up to
            yPosition += placeable.height
        }
    }
}

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeTheme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    ComposeTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}

@Preview
@Composable
fun PreviewCustomColumn(modifier: Modifier = Modifier) {
    CustomColumn(modifier.padding(8.dp)) {
        Text("CustomColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}
