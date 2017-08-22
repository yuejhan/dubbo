package edu.dubbo.core;

import edu.dubbo.common.entity.BaseEntity;
import edu.dubbo.common.page.PageBean;
import edu.dubbo.common.page.PageParam;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 17:18
 */
public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T>{

    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    public static final String SQL_INSERT = "insert";
    public static final String SQL_BATCH_INSERT = "batchInsert";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_BATCH_UPDATE = "batchUpdate";
    public static final String SQL_GET_BY_ID = "getById";
    public static final String SQL_DELETE_BY_ID = "deleteById";
    public static final String SQL_LIST_PAGE = "listPage";
    public static final String SQL_LIST_PAGE_COUNT = "listPageCount";
    public static final String SQL_LIST_BY = "listBy";
    public static final String SQL_COUNT_BY_PAGE_PARAM = "countByPageParam"; // 根据当前分页参数进行统计


    @Autowired
    private SqlSessionTemplate sessionTemplate;

    public int insert(T entity) {
        return sessionTemplate.insert(getStatement(SQL_INSERT), entity);
    }

    public int insert(List<T> list) {
        if (list == null || list.size() <= 0) return 0;
        return sessionTemplate.insert(getStatement(SQL_BATCH_INSERT), list);
    }

    public int update(T entity) {
        return sessionTemplate.update(getStatement(SQL_UPDATE), entity);
    }

    public int update(List<T> list) {
        if (list == null || list.size() <= 0) {
            return 0;
        }

        return sessionTemplate.update(getStatement(SQL_BATCH_UPDATE), list);
    }

    public T getById(long id) {
        return sessionTemplate.selectOne(getStatement(SQL_GET_BY_ID), id);
    }

    public T getBy(Map<String, Object> paramMap) {
        return sessionTemplate.selectOne(getStatement(SQL_LIST_BY), paramMap);
    }

    public List<T> listBy(Map<String, Object> paramMap) {
        return sessionTemplate.selectList(getStatement(SQL_LIST_BY), paramMap);
    }

    public int deleteById(long id) {
        return sessionTemplate.delete(getStatement(SQL_DELETE_BY_ID), id);
    }

    public PageBean queryPage(PageParam pageParam, Map<String, Object> paramMap) {

        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst", (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("pageSize", pageParam.getNumPerPage());
        paramMap.put("startRowNum", (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("endRowNum", pageParam.getPageNum() * pageParam.getNumPerPage());

        // 统计总记录数
        Long count = sessionTemplate.selectOne(getStatement(SQL_LIST_PAGE_COUNT), paramMap);

        // 获取分页数据集
        List<Object> list = sessionTemplate.selectList(getStatement(SQL_LIST_PAGE), paramMap);

        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            Map<String, Object> countResultMap = sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), count.intValue(), list, countResultMap);
        } else {
            // 构造分页对象
            return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), count.intValue(), list);
        }
    }

    /**
     * 获取mapper的命名空间
     *
     * @param sqlId 操作
     */
    public String getStatement(String sqlId) {
        String name = this.getClass().getName();
        StringBuffer sb = new StringBuffer();
        sb.append(name).append(".").append(sqlId);
        String statement = sb.toString();

        return statement;
    }
}
