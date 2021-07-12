package com.dongliang.lcnorder.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "lcn-pay")
public interface UserClient {

    @PostMapping("add-pay1")
    String addPay1(@RequestBody JSONObject bean);

}
