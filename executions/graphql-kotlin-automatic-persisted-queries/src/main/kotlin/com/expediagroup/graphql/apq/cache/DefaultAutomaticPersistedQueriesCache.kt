/*
 * Copyright 2024 Expedia, Inc
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

package com.expediagroup.graphql.apq.cache

import graphql.ExecutionInput
import graphql.execution.preparsed.PreparsedDocumentEntry
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

class DefaultAutomaticPersistedQueriesCache : AutomaticPersistedQueriesCache {

    private val cache: ConcurrentHashMap<String, PreparsedDocumentEntry> = ConcurrentHashMap()

    override fun getOrElse(
        key: String,
        executionInput: ExecutionInput,
        supplier: () -> PreparsedDocumentEntry
    ): CompletableFuture<PreparsedDocumentEntry> =
        cache[key]?.let { entry ->
            CompletableFuture.completedFuture(entry)
        } ?: run {
            val entry = supplier.invoke()
            cache[key] = entry
            CompletableFuture.completedFuture(entry)
        }
}
