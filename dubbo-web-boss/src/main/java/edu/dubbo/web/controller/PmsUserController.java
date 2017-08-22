package edu.dubbo.web.controller;

import edu.dubbo.common.page.PageBean;
import edu.dubbo.facade.entity.PmsUser;
import edu.dubbo.facade.enums.UserStatusEnum;
import edu.dubbo.facade.enums.UserTypeEnum;
import edu.dubbo.facade.server.UserFacadeServer;
import edu.dubbo.web.DwzParam;
import edu.dubbo.web.PageUtil;
import edu.dubbo.web.bean.UserQueryBean;
import edu.dubbo.web.bean.UserSaveBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 19:39
 */
@Controller
@RequestMapping("/pms")
public class PmsUserController {

    @Autowired
    private UserFacadeServer userFacadeServer;

    @RequestMapping("/userViewOwnInfo")
    public void userViewOwnInfo() {

    }

    @RequestMapping("/userChangeOwnPwdUI")
    public void userChangeOwnPwdUI() {

    }

    @RequestMapping(value = "/addPmsUser", method = RequestMethod.POST)
    public String addPmsUser(UserSaveBean bean, DwzParam dwzParam, Model model) {

        try {

            PmsUser pmsUser = new PmsUser();
            pmsUser.setUserName(bean.getUserName()); // 姓名
            pmsUser.setUserNo(bean.getUserNo()); // 登录名
            pmsUser.setUserPwd(bean.getUserPwd());
            pmsUser.setRemark(bean.getDesc()); // 描述
            pmsUser.setChangedPwd(false);
            pmsUser.setLastLoginTime(null);
            pmsUser.setMobileNo(bean.getMobileNo()); // 手机号码
            pmsUser.setStatus(bean.getStatus()); // 状态（100:'激活',101:'冻结'1）
            pmsUser.setUserType(String.valueOf(UserTypeEnum.ADMIN.getValue())); // 用户类型（1:超级管理员，2:普通管理员，3:用户主帐号，4:用户子帐号）

            // 表单数据校验
            String validateMsg = validatePmsUser(pmsUser);

            if (StringUtils.isNotBlank(validateMsg)) {
                dwzParam.setStatusCode("300");
                dwzParam.setMessage(validateMsg);
                model.addAttribute("dwz", dwzParam);
                return "/common/operateError"; // 返回错误信息
            }

            // 校验用户登录名是否已存在
            PmsUser userNoCheck = userFacadeServer.findUserByUserNo(bean.getUserNo());
            if (userNoCheck != null) {
                dwzParam.setStatusCode("300");
                dwzParam.setMessage("登录名【" + bean.getUserNo() + "】已存在");
                model.addAttribute("dwz", dwzParam);
                return "/common/operateError"; // 返回错误信息
            }

            pmsUser.setUserPwd(DigestUtils.sha1Hex(bean.getUserPwd())); // 存存前对密码进行加密

            userFacadeServer.create(pmsUser);
            dwzParam.setStatusCode("200");
            dwzParam.setMessage("保存用户信息成功");
            model.addAttribute("dwz", dwzParam);
            return "/common/operateSuccess";
        } catch (Exception e) {
            dwzParam.setStatusCode("300");
            dwzParam.setMessage("保存用户信息失败");
            model.addAttribute("dwz", dwzParam);
            return "/common/operateError"; // 返回错误信息
        }
    }

    @RequestMapping("/addPmsUserUI")
    public String addPmsUserUI(Model model) {
        try {
            model.addAttribute("UserStatusEnumList", UserStatusEnum.values());
            return "pms/PmsUserAdd";
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping(value = "/editPmsUserUI",method = RequestMethod.GET)
    public String editPmsUserUI(@RequestParam("id")Long id,Model model) {
        try {
            PmsUser pmsUser = userFacadeServer.getById(id);
            if (pmsUser == null) {
                DwzParam dwzParam = new DwzParam("300", "无法获取要修改的数据");
                model.addAttribute("dwz", dwzParam);
                return "common/operateError";
            }

//            // 普通用户没有修改超级管理员的权限
//            if (UserTypeEnum.ADMIN.getValue().equals(this.getLoginedUser().getUserType()) && UserTypeEnum.ADMIN.getValue().equals(pmsUser.getUserType())) {
//                return operateError("权限不足");
//            }

            model.addAttribute("user",pmsUser);

            model.addAttribute("UserStatusEnum", UserStatusEnum.toMap());
            model.addAttribute("UserTypeEnum", UserTypeEnum.toMap());

            return "pms/PmsUserEdit";
        } catch (Exception e) {
            DwzParam dwzParam = new DwzParam("300", "获取修改数据失败");
            model.addAttribute("dwz", dwzParam);
            return "common/operateError";
        }
    }

    @RequestMapping(value = "/editPmsUser",method = RequestMethod.POST)
    public String editPmsUser(UserSaveBean bean, DwzParam dwzParam, Model model) {
        try {

            PmsUser pmsUser = userFacadeServer.getById(bean.getUserId());
            if (pmsUser == null) {
                dwzParam.setStatusCode("300");
                dwzParam.setMessage("无法获取要修改的用户信息");
                model.addAttribute("dwz", dwzParam);
                return "common/operateError";
            }

            // 普通用户没有修改超级管理员的权限
//            if ("0".equals(this.getLoginedUser().getUserType()) && "1".equals(pmsUser.getUserType())) {
//                return operateError("权限不足");
//            }

            pmsUser.setRemark(bean.getDesc());
            pmsUser.setMobileNo(bean.getMobileNo());
            pmsUser.setUserName(bean.getUserName());
            // 修改时不能修状态
            // pmsUser.setStatus(getInteger("status"));



            // 表单数据校验
            String validateMsg = validatePmsUser(pmsUser);
            if (StringUtils.isNotBlank(validateMsg)) {
                dwzParam.setStatusCode("300");
                dwzParam.setMessage(validateMsg);
                model.addAttribute("dwz", dwzParam);
            }

            userFacadeServer.update(pmsUser);
            dwzParam.setStatusCode("200");
            dwzParam.setMessage("更新数据成功");
            model.addAttribute("dwz", dwzParam);
            return "/common/operateSuccess";
        } catch (Exception e) {
            dwzParam.setStatusCode("300");
            dwzParam.setMessage("更新用户信息失败");
            model.addAttribute("dwz", dwzParam);
            return "common/operateError";
        }

    }

    @RequestMapping(value = "/deleteUserStatus",method = RequestMethod.POST)
    public String deleteUserStatus(@RequestParam("id") Long id,Model model) {
        userFacadeServer.deleteUserById(id);

        DwzParam dwzParam= new DwzParam("200", "删除成功");
        model.addAttribute("dwz",dwzParam);
        return "common/operateSuccess";
    }

    @RequestMapping(value = "/viewPmsUserUI", method = RequestMethod.GET)
    public String viewPmsUserUI(@RequestParam("id") Long id, Model model) {
        try {

            PmsUser pmsUser = userFacadeServer.getById(id);
            if (pmsUser == null) {
                DwzParam dwzParam = new DwzParam("300", "无法获取要查看的数据");
                model.addAttribute("dwz", dwzParam);
                return "common/operateError";
            }

            model.addAttribute("UserStatusEnumList", UserStatusEnum.values());
            model.addAttribute("UserStatusEnum", UserStatusEnum.toMap());
            model.addAttribute("UserTypeEnumList", UserTypeEnum.values());
            model.addAttribute("UserTypeEnum", UserTypeEnum.toMap());

            model.addAttribute("user",pmsUser);
            return "pms/PmsUserView";
        } catch (Exception e) {
            DwzParam dwzParam = new DwzParam("300", "获取数据失败");
            model.addAttribute("dwz", dwzParam);
            return "common/operateError";
        }
    }

    @RequestMapping("/listPmsUser")
    public String listPmsUser(HttpServletRequest request, UserQueryBean queryBean, Model model) {

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
            paramMap.put("userNo", queryBean.getUserNo()); // 用户登录名（精确查询）
            paramMap.put("userName", queryBean.getUserName()); // 用户姓名（模糊查询）
            paramMap.put("status", queryBean.getStatus()); // 状态

            PageBean pageBean = userFacadeServer.listPage(PageUtil.getParams(request), paramMap);

            model.addAttribute("queryBean", queryBean);
            model.addAttribute("pageData", pageBean);
            model.addAttribute("UserStatusEnumList", UserStatusEnum.values());
            model.addAttribute("UserStatusEnum", UserStatusEnum.toMap());
            model.addAttribute("UserTypeEnumList", UserTypeEnum.values());
            model.addAttribute("UserTypeEnum", UserTypeEnum.toMap());

            return "pms/PmsUserList";
        } catch (Exception e) {

        }
        return null;
    }


    private String validatePmsUser(PmsUser user) {
        String msg = ""; // 用于存放校验提示信息的变量
        msg += lengthValidate("真实姓名", user.getUserName(), true, 2, 15);
        msg += lengthValidate("登录名", user.getUserName(), true, 3, 50);

        // 登录密码
        String userPwd = user.getUserPwd();
        String userPwdMsg = lengthValidate("登录密码", userPwd, true, 6, 50);
        /*
		 * if (StringUtils.isBlank(loginPwdMsg) &&
		 * !ValidateUtils.isAlphanumeric(loginPwd)) { loginPwdMsg +=
		 * "登录密码应为字母或数字组成，"; }
		 */
        msg += userPwdMsg;

        // 手机号码
        String mobileNo = user.getMobileNo();
        String mobileNoMsg = lengthValidate("手机号", mobileNo, true, 0, 12);
        msg += mobileNoMsg;

        // 状态
        Integer status = user.getStatus();
        if (status == null) {
            msg += "请选择状态，";
        } else if (status.intValue() < 100 || status.intValue() > 101) {
            msg += "状态值不正确，";
        }

        msg += lengthValidate("描述", user.getRemark(), true, 3, 100);
        return msg;
    }

    protected String lengthValidate(String propertyName, String property, boolean isRequire, int minLength, int maxLength) {

        int propertyLenght = strLengthCn(property);
        if (isRequire && propertyLenght == 0) {
            return propertyName + "不能为空，"; // 校验不能为空
        } else if (isRequire && minLength != 0 && propertyLenght < minLength) {
            return propertyName + "不能少于" + minLength + "个字符，"; // 必填情况下校验最少长度
        } else if (maxLength != 0 && propertyLenght > maxLength) {
            return propertyName + "不能多于" + maxLength + "个字符，"; // 校验最大长度
        } else {
            return ""; // 校验通过则返回空字符串 .
        }
    }

    private int strLengthCn(String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        int valueLength = 0;
        final String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int num = 0; num < str.length(); num++) {
			/* 获取一个字符 */
            final String temp = str.substring(num, num + 1);
			/* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
				/* 中文字符长度为3 */
                valueLength += 3;
            } else {
				/* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

}
