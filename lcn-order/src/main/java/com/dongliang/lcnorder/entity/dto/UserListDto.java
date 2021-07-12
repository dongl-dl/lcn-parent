package com.dongliang.lcnorder.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName UserListDto.java
 * @Description TODO
 * @createTime 2021-06-16 09:31:00
 */
@Data
public class UserListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "页码不能为空")
    @Range(min = 1)
    private Integer pageNum;

    @NotNull(message = "每页条数不能为空")
    @Range(min = 1)
    private Integer pageSize;
}
