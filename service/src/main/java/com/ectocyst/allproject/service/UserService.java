package com.ectocyst.allproject.service;

import com.ectocyst.allproject.dao.DO.UserDO;

/**
 * @Author: Damon . yanli
 * @Description:
 * @Date: Created in 21:12 2020-11-10
 * @Modified By:
 */
public interface UserService {

    /**
     * 插入用户
     * @param userDO
     * @return
     */
    Integer insertUser(UserDO userDO);

}
