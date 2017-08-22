package edu.dubbo.server.facade;

import edu.dubbo.common.page.PageBean;
import edu.dubbo.common.page.PageParam;
import edu.dubbo.facade.entity.PmsUser;
import edu.dubbo.facade.server.UserFacadeServer;
import edu.dubbo.server.user.PmsUserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/9 20:30
 */
@Service("userFacadeServer")
public class UserFacadeServerImpl implements UserFacadeServer{

    @Autowired
    private PmsUserServer pmsUserServer;

    public void create(PmsUser pmsUser) {
        pmsUserServer.create(pmsUser);
    }

    public PmsUser getById(Long userId) {
        return pmsUserServer.getById(userId);
    }

    public PmsUser findUserByUserNo(String userNo) {
        return pmsUserServer.findUserByUserNo(userNo);
    }

    public void deleteUserById(long userId) {
        pmsUserServer.deleteUserById(userId);
    }

    public void update(PmsUser user) {
        pmsUserServer.update(user);
    }

    public void updateUserPwd(Long userId, String newPwd, boolean isTrue) {
        pmsUserServer.updateUserPwd(userId,newPwd,isTrue);
    }

    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        return pmsUserServer.listPage(pageParam,paramMap);
    }
}
