package com.dongl.handlingexception.controller;

import com.dongl.handlingexception.entity.ErrorEnum;
import com.dongl.handlingexception.entity.User;
import com.dongl.handlingexception.exception.BusinessException;
import com.dongl.handlingexception.exception.CustomizeException;
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
            throw new CustomizeException(ErrorEnum.NOT_FOUND.getErrorCode() ,ErrorEnum.NOT_FOUND.getErrorMsg(),"测试接口异常");
        }
        return "ok";
    }


    @GetMapping("/getExceptionMessage1")
    public String getExceptionMessage1(@RequestParam("exStatus") String exStatus){
        if ("YES".equalsIgnoreCase(exStatus)) {
            throw new BusinessException(ErrorEnum.NOT_FOUND.getErrorCode() ,ErrorEnum.NOT_FOUND.getErrorMsg());
        }
        return "ok";
    }

    @PostMapping(value = "user")
    public String test(@Validated @RequestBody User user  /*, BindingResult bindingResult*/) {
        System.out.println(user);
        return "do something you like ------";
    }

}
