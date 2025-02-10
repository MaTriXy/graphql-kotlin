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

package com.expediagroup.graphql.server.ktor.subscriptions

import com.expediagroup.graphql.server.execution.subscription.GraphQLSubscriptionContextFactory
import io.ktor.server.websocket.WebSocketServerSession

/**
 * Ktor specific version of WebSocket subscription context factory.
 */
interface KtorGraphQLSubscriptionContextFactory : GraphQLSubscriptionContextFactory<WebSocketServerSession>

class DefaultKtorGraphQLSubscriptionContextFactory : KtorGraphQLSubscriptionContextFactory
