/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.cluster.client;

import com.hazelcast.client.ClientCommandHandler;
import com.hazelcast.collection.CollectionProxyId;
import com.hazelcast.collection.CollectionProxyType;
import com.hazelcast.collection.CollectionService;
import com.hazelcast.instance.Node;
import com.hazelcast.map.MapService;
import com.hazelcast.nio.Protocol;
import com.hazelcast.spi.ProxyService;

public class DestroyHandler extends ClientCommandHandler {

    @Override
    public Protocol processCall(Node node, Protocol protocol) {
        String[] args = protocol.args;
        String serviceName = args[0];
        String name = args[1];
        final ProxyService proxyService = node.nodeEngine.getProxyService();
        if("multimap".equalsIgnoreCase(serviceName)){
            CollectionProxyId id = new CollectionProxyId(name, null, CollectionProxyType.MULTI_MAP);
            proxyService.destroyDistributedObject(CollectionService.SERVICE_NAME, id);
        }
        else {
            proxyService.destroyDistributedObject(serviceName, name);
        }
        return protocol.success();
    }
}
