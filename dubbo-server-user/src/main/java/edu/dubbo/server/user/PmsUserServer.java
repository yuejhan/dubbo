package edu.dubbo.server.user;

import edu.dubbo.dao.PmsUserDao;
import edu.dubbo.common.page.PageBean;
import edu.dubbo.common.page.PageParam;
import edu.dubbo.facade.entity.PmsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 19:23
 */
@Service("pmsUserServer")
public class PmsUserServer {

    private static final Logger logger = LoggerFactory.getLogger(PmsUserServer.class);

    @Autowired
    private PmsUserDao pmsUserDao;


    /**
     * 保存用户信息.
     * @param pmsUser 用户实体
     */
    public void create(PmsUser pmsUser) {
        pmsUserDao.insert(pmsUser);
    }

    /**
     * 根据ID获取用户信息.
     * @param userId 用户ID
     */
    public PmsUser getById(Long userId) {
        return pmsUserDao.getById(userId);
    }

    /**
     * 根据登录名取得用户对象
     */
    public PmsUser findUserByUserNo(String userNo) {
        return pmsUserDao.findByUserNo(userNo);
    }

    /**
     * 根据ID删除一个用户，同时删除与该用户关联的角色关联信息. type="1"的超级管理员不能删除.
     *
     * @param userId 用户ID.
     */
    public void deleteUserById(long userId) {
        PmsUser pmsUser = pmsUserDao.getById(userId);
        if (pmsUser != null) {
            if ("1".equals(pmsUser.getUserType())) {
                throw new RuntimeException("【" + pmsUser.getUserNo() + "】为超级管理员，不能删除！");
            }
            pmsUserDao.deleteById(pmsUser.getId());
        }
    }


    /**
     * 更新用户信息.
     * @param user 用户实体
     */
    public void update(PmsUser user) {
        pmsUserDao.update(user);
    }

    /**
     * 根据用户ID更新用户密码.
     *
     * @param userId 用户ID
     * @param newPwd 新密码
     *            (已进行SHA1加密)
     */
    public void updateUserPwd(Long userId, String newPwd, boolean isTrue) {
        PmsUser pmsUser = pmsUserDao.getById(userId);
        pmsUser.setUserPwd(newPwd);
        pmsUser.setPwdErrorCount(0); // 密码错误次数重置为0
        pmsUser.setChangedPwd(isTrue); // 设置密码为已修改过
        pmsUserDao.update(pmsUser);
    }



    /**
     * 查询并分页列出用户信息.
     * @param pageParam 分页查询条件
     * @param paramMap 查询条件
     */
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        return pmsUserDao.queryPage(pageParam, paramMap);
    }

}
