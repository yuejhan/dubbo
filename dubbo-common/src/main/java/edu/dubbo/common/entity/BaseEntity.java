package edu.dubbo.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 18:23
 */
public class BaseEntity implements Serializable{
    /** 主键ID **/
    private Long id;

    /** 版本号 **/
    private Integer version = 0;

    /** 创建时间 **/
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
