package com.atguigu.boot3.ssm.bean;

import lombok.Data;

/**
 * @author lfy
 * @Description
 * @create 2023-04-20 16:58
 */
@Data
public class TUser {
    private Long id;
    private String loginName;
    private String nickName;
    private String passwd;

}
