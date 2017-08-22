package edu.dubbo.dao;

import edu.dubbo.core.BaseDao;
import edu.dubbo.facade.entity.PmsUser;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 18:36
 */
public interface PmsUserDao extends BaseDao<PmsUser> {

    /**
     *根据用户登录名获取用户信息
     * @param userNo 登录名
     * @return 用户信息
     */
    PmsUser findByUserNo(String userNo);
}
