package edu.dubbo.core;

import edu.dubbo.common.page.PageBean;
import edu.dubbo.common.page.PageParam;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 16:30
 */
public interface BaseDao<T>{

    /**
     * 根据实体对象新增记录
     * @param entity 实体对象
     * @return 插入条数
     */
    int insert(T entity);

    /**
     * 批量保存实体对象
     * @param list 实体对象集合
     * @return 插入条数
     */
    int insert(List<T> list);

    /**
     * 更新实体对象
     * @param entity 实体对象
     * @return 更新条数
     */
    int update(T entity);

    /**
     * 批量更新实体对象
     * @param list 实体对象集合
     * @return 更新条数
     */
    int update(List<T> list);

    /**
     * 更具ID查找记录
     * @param id id
     * @return entity
     */
    T getById(long id);

    /**
     * 根据条件查询
     * @param paramMap 查询条件
     * @return entity
     */
    T getBy(Map<String, Object> paramMap);

    /**
     * 根据条件查询
     * @param paramMap 查询条件
     * @return entities
     */
    List<T> listBy(Map<String, Object> paramMap);

    /**
     * 根据ID删除记录
     * @param id id
     * @return 删除条数
     */
    int deleteById(long id);

    /**
     * 分页查询
     * @param pageParam 分页查询条件
     * @param paramMap 数据查询条件
     * @return 分页对象
     */
    PageBean queryPage(PageParam pageParam, Map<String, Object> paramMap);

}
