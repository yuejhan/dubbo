package edu.dubbo.common.page;

import java.io.Serializable;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 16:45
 */
public class PageParam implements Serializable {

    private int pageNum; // 当前页数
    private int numPerPage; // 每页记录数

    public PageParam(int pageNum, int numPerPage) {
        super();
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
    }

    /** 当前页数 */
    public int getPageNum() {
        return pageNum;
    }

    /** 当前页数 */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /** 每页记录数 */
    public int getNumPerPage() {
        return numPerPage;
    }

    /** 每页记录数 */
    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
}
