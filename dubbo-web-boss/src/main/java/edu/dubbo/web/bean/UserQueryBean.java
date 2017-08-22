package edu.dubbo.web.bean;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 23:43
 */
public class UserQueryBean {

    private String userNo;

    private String userName;

    private Integer status;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
