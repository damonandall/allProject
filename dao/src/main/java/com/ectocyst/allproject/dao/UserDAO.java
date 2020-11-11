package com.ectocyst.allproject.dao;

import com.ectocyst.allproject.dao.DO.UserDO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Damon . yanli
 * @Description:
 * @Date: Created in 21:07 2020-11-10
 * @Modified By:
 */
public interface UserDAO {

    int insert(UserDO userDO);

    Long selectCountByDay(@Param("signDay") String signDay, @Param("type") Integer type);

    UserDO selectUser(UserDO userDO);
}
