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
import graphql.schema.GraphQLList
import graphql.schema.GraphQLTypeUtil
import graphql.schema.GraphQLUnionType

internal fun validateKeySetField(targetField: GraphQLFieldDefinition?, extendedType: Boolean, errors: MutableList<String>, validatedDirective: String) {
    if (null != targetField) {
        val externalField = targetField.getDirective("external") != null
        if (extendedType && !externalField) {
            errors.add("$validatedDirective specifies invalid field set - extended type incorrectly references local field=${targetField.name}")
        } else if (!extendedType && externalField) {
            errors.add("$validatedDirective specifies invalid field set - type incorrectly references external field=${targetField.name}")
        }

        when (GraphQLTypeUtil.unwrapNonNull(targetField.type)) {
            is GraphQLList -> errors.add("$validatedDirective specifies invalid field set - field set references GraphQLList, field=${targetField.name}")
            is GraphQLInterfaceType -> errors.add("$validatedDirective specifies invalid field set - field set references GraphQLInterfaceType, field=${targetField.name}")
            is GraphQLUnionType -> errors.add("$validatedDirective specifies invalid field set - field set references GraphQLUnionType, field=${targetField.name}")
        }
    } else {
        errors.add("$validatedDirective specifies invalid field set - field set specifies fields that do not exist")
    }
}
