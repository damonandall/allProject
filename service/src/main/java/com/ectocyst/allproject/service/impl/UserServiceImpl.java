package com.ectocyst.allproject.service.impl;

import com.ectocyst.allproject.dao.DO.UserDO;
import com.ectocyst.allproject.dao.UserDAO;
import com.ectocyst.allproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Author: Damon . yanli
 * @Description:
 * @Date: Created in 21:13 2020-11-10
 * @Modified By:
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final ConcurrentMap<String, Future<Integer>> valueComputeFutureMap = new ConcurrentHashMap<>();

    @Autowired
    private UserDAO userDAO;

    @Override
    public Integer insertUser(UserDO userDO) {

        if(valueComputeFutureMap.size() >= 20){
            throw new RuntimeException("前面排队的人过多，请稍后重试。。");
        }

        String key = stringKey(userDO.getUserName(), userDO.getMobile(), userDO.getRoomNum());

        Future future = valueComputeFutureMap.get(key);
        try {
            // 如果任务不存在
            if (future == null) {
                FutureTask<Integer> task = new FutureTask<Integer>(() -> {

                    UserDO user = userDAO.selectUser(userDO);

                    if(user != null && user.getSignDay().equals(userDO.getSignDay())){
                        throw new RuntimeException("您已经报名!");
                    }

                    Long tatalCount = userDAO.selectCountByDay(userDO.getSignDay(),userDO.getType());
                    if(tatalCount >= 50){
                        throw new RuntimeException("友情提醒：感谢您的参与和支持，很遗憾本场活动报名人数已满，敬请关注下一场！");
                    }

                    //报名
                    return userDAO.insert(userDO);
                }) {
                    @Override
                    protected void done() {
                        valueComputeFutureMap.remove(key, this);
                    }
                };
                future = valueComputeFutureMap.putIfAbsent(key, task);
                if (future == null) {
                    task.run();
                    return task.get();
                }
            }
            long timeout = 3000l;
            Object o = future.get(timeout, TimeUnit.MILLISECONDS);
            if(!Objects.equals(o,null)){
                return Integer.parseInt(String.valueOf(o));
            }
        } catch (Exception ex) {
            Throwable cause = ex;
            // 执行异常获取其cause
            if (cause instanceof ExecutionException) {
                cause = cause.getCause();
            }
            // 运行时异常执行抛出
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
        }
        // 其它异常包装为非受查异常抛出
        throw new RuntimeException("正在报名中");
    }

    private String stringKey(String userName,Long mobile,String roomNum) {
        return "Sign:"+ userName + "_"+ mobile + '_' + roomNum;
    }
}

