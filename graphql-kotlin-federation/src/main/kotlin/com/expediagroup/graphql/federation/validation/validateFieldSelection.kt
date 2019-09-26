/*
 * Copyright 2019 Expedia, Inc
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

package com.expediagroup.graphql.federation.validation

import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLInterfaceType
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLTypeUtil

internal fun validateFieldSelection(
    validatedDirective: String,
    iterator: Iterator<String>,
    fields: Map<String, GraphQLFieldDefinition>,
    extendedType: Boolean,
    errors: MutableList<String>
) {
    var previousField: String? = null
    while (iterator.hasNext()) {
        val currentField = iterator.next()
        when (currentField) {
            "{" -> {
                val targetField = fields[previousField]?.type
                when (val unwrappedType = GraphQLTypeUtil.unwrapAll(targetField)) {
                    is GraphQLInterfaceType -> validateFieldSelection(validatedDirective, iterator, unwrappedType.fieldDefinitions.associateBy { it.name }, extendedType, errors)
                    is GraphQLObjectType -> validateFieldSelection(validatedDirective, iterator, unwrappedType.fieldDefinitions.associateBy { it.name }, extendedType, errors)
                    else -> errors.add("$validatedDirective specifies invalid field set - field set defines nested selection set on unsupported type")
                }
            }
            "}" -> return
            else -> validateKeySetField(fields[currentField], extendedType, errors, validatedDirective)
        }
        previousField = currentField
    }
}
