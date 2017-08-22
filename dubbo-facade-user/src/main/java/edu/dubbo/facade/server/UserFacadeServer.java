package edu.dubbo.facade.server;

import edu.dubbo.common.page.PageBean;
import edu.dubbo.common.page.PageParam;
import edu.dubbo.facade.entity.PmsUser;

import java.util.Map;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/9 20:36
 */
public interface UserFacadeServer {



    /**
     * 保存用户信息.
     * @param pmsUser 用户实体
     */
    void create(PmsUser pmsUser);

    /**
     * 根据ID获取用户信息.
     * @param userId 用户ID
     */
    PmsUser getById(Long userId);

    /**
     * 根据登录名取得用户对象
     */
    PmsUser findUserByUserNo(String userNo);

    /**
     * 根据ID删除一个用户，同时删除与该用户关联的角色关联信息. type="1"的超级管理员不能删除.
     *
     * @param userId 用户ID.
     */
    void deleteUserById(long userId);


    /**
     * 更新用户信息.
     * @param user 用户实体
     */
   void update(PmsUser user);

    /**
     * 根据用户ID更新用户密码.
     *
     * @param userId 用户ID
     * @param newPwd 新密码
     *            (已进行SHA1加密)
     */
    void updateUserPwd(Long userId, String newPwd, boolean isTrue);



    /**
     * 查询并分页列出用户信息.
     * @param pageParam 分页查询条件
     * @param paramMap 查询条件
     */
    PageBean listPage(PageParam pageParam, Map<String, Object> paramMap);
}
