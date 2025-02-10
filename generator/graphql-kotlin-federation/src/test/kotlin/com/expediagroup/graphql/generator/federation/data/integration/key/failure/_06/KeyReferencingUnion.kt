/*
 * Copyright 2022 Expedia, Inc
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

package com.expediagroup.graphql.generator.federation.data.integration.key.failure._06

import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import io.mockk.mockk

/*
# example invalid usage of @key directive - field set references a union
type KeyReferencingUnion @key(fields : "id") {
  description: String!
  id: KeyUnion!
}

type Key {
  id: String!
}

union KeyUnion = Key
 */
@KeyDirective(fields = FieldSet("id"))
data class KeyReferencingUnion(val id: KeyUnion, val description: String)

interface KeyUnion

@Suppress("UnusedPrivateClass")
data class Key(val id: String) : KeyUnion

class KeyReferencingUnionQuery {
    fun keyQuery(): KeyReferencingUnion = mockk()
}
