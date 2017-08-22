package edu.dubbo.web.controller;

import edu.dubbo.facade.entity.PmsUser;
import edu.dubbo.facade.enums.UserStatusEnum;
import edu.dubbo.facade.server.UserFacadeServer;
import edu.dubbo.web.SessionConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 20:44
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserFacadeServer userFacadeServer;

    @RequestMapping("/loginPage")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/userLogin")
    public String login(HttpServletRequest request,
                        @RequestParam("userNo")String userNo,
                        @RequestParam("userPwd")String pwd,
                        Model model){

        try {

            // 明文用户名
            if (StringUtils.isBlank(userNo)) {
                model.addAttribute("userNoMsg","用户名不能为空");
                return "login";
            }
            model.addAttribute("userNo", userNo);
            PmsUser user = userFacadeServer.findUserByUserNo(userNo);
            if (user == null) {
                model.addAttribute("userNoMsg", "用户名或密码不正确");
                return "login";
            }

            if (user.getStatus().intValue() == UserStatusEnum.INACTIVE.getValue()) {
                model.addAttribute("userNoMsg", "该帐号已被冻结");
                return "login";
            }
            if (StringUtils.isBlank(pwd)) {
                model.addAttribute("userPwdMsg", "密码不能为空");
                return "login";
            }
            // 加密明文密码
            // 验证密码
            if (user.getUserPwd().equals(DigestUtils.sha1Hex(pwd))) {
                // 用户信息，包括登录信息和权限
//                request.getSession().put(SessionConstant.USER_SESSION_KEY, user);
//
//                // 将主帐号ID放入Session
//                if (UserTypeEnum.MAIN_USER.getValue().equals(user.getUserType())) {
//                    this.getSessionMap().put(SessionConstant.MAIN_USER_ID_SESSION_KEY, user.getId());
//                } else if (UserTypeEnum.SUB_USER.getValue().equals(user.getUserType())) {
//                    this.getSessionMap().put(SessionConstant.MAIN_USER_ID_SESSION_KEY, user.getMainUserId());
//                } else {
//                    // 其它类型用户的主帐号ID默认为0
//                    this.getSessionMap().put(SessionConstant.MAIN_USER_ID_SESSION_KEY, 0L);
//                }

                model.addAttribute("userNo", userNo);
                model.addAttribute("lastLoginTime", user.getLastLoginTime());

                try {
                    // 更新登录数据
                    user.setLastLoginTime(new Date());
                    user.setPwdErrorCount(0); // 错误次数设为0
                    userFacadeServer.update(user);

                } catch (Exception e) {
                    model.addAttribute("errorMsg", e.getMessage());
                    return "login";
                }

                // 判断用户是否重置了密码，如果重置，弹出强制修改密码页面； TODO
                model.addAttribute("isChangePwd", user.getChangedPwd());

                return "index";

            } else {
                // 错误次数加1
                Integer pwdErrorCount = user.getPwdErrorCount();
                if (pwdErrorCount == null) {
                    pwdErrorCount = 0;
                }
                user.setPwdErrorCount(pwdErrorCount + 1);
                user.setPwdErrorTime(new Date()); // 设为当前时间
                String msg = "";
                if (user.getPwdErrorCount().intValue() >= SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT) {
                    // 超5次就冻结帐号
                    user.setStatus(UserStatusEnum.INACTIVE.getValue());
                    msg += "<br/>密码已连续输错【" + SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT + "】次，帐号已被冻结";
                } else {
                    msg += "<br/>密码错误，再输错【" + (SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT - user.getPwdErrorCount().intValue()) + "】次将冻结帐号";
                }
                userFacadeServer.update(user);
                model.addAttribute("userPwdMsg", msg);
                return "login";
            }

        } catch (RuntimeException e) {
            model.addAttribute("errorMsg", "登录出错");
            return "login";
        } catch (Exception e) {
            model.addAttribute("errorMsg", "登录出错");
            return "login";
        }
    }

    @RequestMapping("/logoutConfirm")
    public void logoutConfirm(){

    }
}
