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

package com.expediagroup.graphql.generator.federation.data.integration.provides.failure._1

import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import com.expediagroup.graphql.generator.federation.directives.ProvidesDirective
import io.mockk.mockk

/*
# example of invalid usage of @provides directive - provides references to non-existent field
type ProvidesNonExistentField @key(fields : "id") {
  description: String!
  id: String!
  provided: ProvidedType! @provides(fields : "description")
}

type ProvidedType @key(fields : "id") {
  id: String!
  text: String!
}
 */
@KeyDirective(fields = FieldSet("id"))
class ProvidesNonExistentField(val id: String, val description: String) {

    @ProvidesDirective(fields = FieldSet("description"))
    fun provided(): ProvidedType = mockk()
}

@KeyDirective(fields = FieldSet("id"))
data class ProvidedType(
    val id: String,
    val text: String
)

class ProvidesNonExistentFieldQuery {
    fun providesQuery(): ProvidesNonExistentField = mockk()
}
