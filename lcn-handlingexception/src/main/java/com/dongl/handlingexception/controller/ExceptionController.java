package com.dongl.handlingexception.controller;

import com.dongl.handlingexception.entity.ErrorEnum;
import com.dongl.handlingexception.entity.User;
import com.dongl.handlingexception.exception.BusinessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName ExceptionController.java
 * @Description TODO
 * @createTime 2021-07-20 16:11:00
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/getExceptionMessage")
    public String getExceptionMessage(@RequestParam("exStatus") String exStatus){
        if ("YES".equalsIgnoreCase(exStatus)) {
            throw new BusinessException(ErrorEnum.NO_AUTH.getErrorCode() ,ErrorEnum.NO_AUTH.getErrorMsg() ,"获取异常接口");
        }
        System.out.println("----------------------");
        return "ok";
    }

    @PostMapping(value = "user")
    public String test(@Validated @RequestBody User user) {
        System.out.println(user);
        return "do something you like ------";
    }

    @GetMapping("/getMessage")
    public String getMessage(){
       int e = 1/0;
        System.out.println("----------------------");
        return "ok";
    }
}
