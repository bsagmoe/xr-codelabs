/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.xrfundamentals.environment

import androidx.xr.scenecore.ExrImage
import androidx.xr.scenecore.GltfModel
import androidx.xr.scenecore.Session
import androidx.xr.scenecore.SpatialEnvironment
import androidx.xr.scenecore.SpatialEnvironment.SetSpatialEnvironmentPreferenceChangeApplied
import androidx.xr.scenecore.SpatialEnvironment.SetSpatialEnvironmentPreferenceChangePending
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch

class EnvironmentController(
    private val session: Session,
    private val scope: CoroutineScope
) {
    fun setSpatialEnvironmentPreference(
        environmentOption: EnvironmentOption,
        onPending: () -> Unit = {},
        onApplied: () -> Unit = {},
        onFailure: (Exception) -> Unit = {},
    ) {
        scope.launch {
            try {
                val skybox = environmentOption.skyboxPath?.let {
                    ExrImage.create(session, it)
                }

                val geometryFuture = environmentOption.geometryPath?.let {
                    GltfModel.create(session, it)
                }

                val geometry = geometryFuture?.await()

                val result = session.spatialEnvironment.setSpatialEnvironmentPreference(
                    SpatialEnvironment.SpatialEnvironmentPreference(
                        skybox,
                        geometry
                    )
                )

                when (result) {
                    is SetSpatialEnvironmentPreferenceChangePending -> onPending();
                    is SetSpatialEnvironmentPreferenceChangeApplied -> onApplied();
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}