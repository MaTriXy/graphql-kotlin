/*
 * Copyright 2023 Expedia, Inc
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

package com.expediagroup.graalvm.schema

import com.expediagroup.graalvm.schema.model.InputOnly
import com.expediagroup.graalvm.schema.model.OutputOnly
import com.expediagroup.graphql.server.operations.Query

/**
 * Queries verifying handling of lists.
 */
class ListQuery : Query {
    fun listQuery(): List<Int>? = null
    fun listObjectQuery(): List<OutputOnly> = listOf(OutputOnly(id = 123, description = "foo"))
    fun listPrimitiveArg(arg: List<Int>): List<Int> = arg
    fun listObjectArg(arg: List<InputOnly>): String = "foobar"
    fun optionalPrimitiveListArg(arg: List<String>? = null): List<String>? = arg
}
