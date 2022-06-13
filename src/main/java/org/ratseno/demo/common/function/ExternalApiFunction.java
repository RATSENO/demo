package org.ratseno.demo.common.function;

import org.springframework.web.reactive.function.client.ClientResponse;

@FunctionalInterface
public interface ExternalApiFunction {
    
    void Test(ClientResponse clientResponse);
}
