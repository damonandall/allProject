package com.ectocyst.allproject.controller;

import com.ectocyst.allproject.dao.DO.UserDO;
import com.ectocyst.allproject.enums.EnumType;
import com.ectocyst.allproject.req.UserReq;
import com.ectocyst.allproject.result.Result;
import com.ectocyst.allproject.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Damon . yanli
 * @Description:
 * @Date: Created in 21:14 2020-11-10
 * @Modified By:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    @ResponseBody
    public Result insertUser(@RequestBody UserReq userReq) {
        try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = simpleDateFormat.parse("2020-11-23 00:00:00");
            if(new Date().after(parse)){
                return Result.fail(101,"活动已结束");
            }

        if(userReq == null){
            return Result.fail(101,"参数为空");
        }


        if(StringUtils.isBlank(userReq.getUserName()) || userReq.getUserName().length() > 5){
            return Result.fail(101,"请输入正确姓名");
        }

        if(userReq.getMobile() == null || !depandMobile(String.valueOf(userReq.getMobile()))){
            return Result.fail(101,"手机号码错误");
        }


        if(StringUtils.isBlank(userReq.getRoomNum()) || userReq.getRoomNum().length() > 20){
            return Result.fail(101,"房间号不能为空，长度少于20");
        }

        if(userReq.getTime() == null){
            return Result.fail(101,"请选择时间");
        }


        UserDO userDO = new UserDO();
        userDO.setUserName(userReq.getUserName());
        userDO.setMobile(userReq.getMobile());
        userDO.setRoomNum(userReq.getRoomNum());
        userDO.setType(EnumType.WANG_CHENG_FU_TWO.getType());

        userDO.setSignDay(getDay(userReq.getTime()));


            Integer integer = userService.insertUser(userDO);

            if(integer == 1){
                return Result.buildSuccess("报名成功！");
            }else {
                return Result.buildSuccess("报名失败，请重试！");
            }
        }catch (DuplicateKeyException e){
            return Result.fail(101,"您已经报名！");
        }catch (RuntimeException e){
            return Result.fail(101,e.getMessage());
        }catch (Exception e){
            return Result.fail(101,"请稍后重试！");
        }
    }

    private String getDay(Integer time) {
        if(time == 1){
            return "2020-11-21";
        }
            return "2020-11-22";
    }


    public static boolean depandMobile(String mobile) {
        if (mobile == null) {
            return false;
        }
        Pattern p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

}
