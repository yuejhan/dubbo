package edu.dubbo.dao.impl;

import edu.dubbo.core.BaseDaoImpl;
import edu.dubbo.dao.PmsUserDao;
import edu.dubbo.facade.entity.PmsUser;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 18:39
 */
@Repository("pmsUserDao")
public class PmsUserDaoImpl extends BaseDaoImpl<PmsUser> implements PmsUserDao {

    public PmsUser findByUserNo(String userNo) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userNo",userNo);
        return getBy(params);
    }
}
