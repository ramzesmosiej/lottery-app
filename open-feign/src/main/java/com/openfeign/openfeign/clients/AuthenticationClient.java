package com.openfeign.openfeign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("authentication")
public interface AuthenticationClient {

    @GetMapping(path = "auth/allowed/{pesel}")
    boolean isAuthenticated(@PathVariable("pesel") String pesel);

}
