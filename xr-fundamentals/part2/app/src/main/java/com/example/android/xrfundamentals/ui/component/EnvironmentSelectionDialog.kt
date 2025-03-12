/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.xrfundamentals.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.xr.compose.spatial.SpatialDialog
import com.example.android.xrfundamentals.environment.DEFAULT_ENVIRONMENT
import com.example.android.xrfundamentals.environment.ENVIRONMENT_OPTIONS
import com.example.android.xrfundamentals.environment.EnvironmentOption

@Composable
fun EnvironmentSelectionDialog(
    environmentOptions: List<EnvironmentOption>,
    initialEnvironmentOption: EnvironmentOption,
    modifier: Modifier = Modifier,
    onEnvironmentOptionSelected: (environmentOption: EnvironmentOption) -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    var selectedEnvironmentOption by remember { mutableStateOf(initialEnvironmentOption) }

    SpatialDialog(onDismissRequest = onDismissRequest) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .width(400.dp)
            ) {
                Text("Environment", style = MaterialTheme.typography.titleMedium)
                Column(modifier = Modifier.selectableGroup()) {
                    environmentOptions.forEachIndexed { i, environmentOption ->
                        val selected = environmentOption == selectedEnvironmentOption
                        Row(
                            Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .selectable(
                                    selected = selected,
                                    onClick = { selectedEnvironmentOption = environmentOption },
                                    role = Role.RadioButton
                                )
                                .padding(16.dp)
                        ) {
                            RadioButton(
                                selected = selected,
                                onClick = null
                            )
                            Spacer(Modifier.width(16.dp))
                            Text(
                                text = environmentOption.name
                            )
                        }
                        if (i < environmentOptions.size) {
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
                Button(
                    enabled = selectedEnvironmentOption != initialEnvironmentOption,
                    onClick = {
                        onEnvironmentOptionSelected(selectedEnvironmentOption)
                    }
                ) {
                    Text("Update environment")
                }
            }
        }
    }
}

@Preview
@Composable
fun EnvironmentSelectionDialogPreview() {
    MaterialTheme {
        EnvironmentSelectionDialog(
            ENVIRONMENT_OPTIONS,
            initialEnvironmentOption = DEFAULT_ENVIRONMENT,
        )
    }
}