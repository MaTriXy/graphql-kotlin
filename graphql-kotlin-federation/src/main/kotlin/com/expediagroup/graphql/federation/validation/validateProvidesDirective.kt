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

import com.expediagroup.graphql.federation.extensions.isExtendedType
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLTypeUtil

// [OK]    @provides on base type references valid @external fields on @extend object
// [ERROR] @provides on base type references local object fields
// [ERROR] @provides on base type references local fields on @extends object
// [ERROR] @provides references interface type
// [OK]    @provides references list of valid @extend objects
// [ERROR] @provides references @external list field
// [ERROR] @provides references @external interface field
internal fun validateProvidesDirective(federatedType: String, field: GraphQLFieldDefinition): List<String> {
    val errors = mutableListOf<String>()
    val returnType = GraphQLTypeUtil.unwrapAll(field.type)
    if (returnType is GraphQLObjectType) {
        if (!returnType.isExtendedType()) {
            errors.add("@provides directive is specified on a $federatedType.${field.name} field references local object")
        } else {
            val returnTypeFields = returnType.fieldDefinitions.associateBy { it.name }
            // @provides is applicable on both base and federated types and always references @external fields
            errors.addAll(
                validateDirective(
                    "$federatedType.${field.name}",
                    "provides",
                    field.directivesByName,
                    returnTypeFields,
                    true))
        }
    } else {
        errors.add("@provides directive is specified on a $federatedType.${field.name} field but it does not return an object type")
    }
    return errors
}
