package edu.dubbo.web;

import edu.dubbo.common.page.PageParam;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/9 00:09
 */
public class PageUtil {

    public static PageParam getParams(HttpServletRequest request){
        // 当前页数
        String pageNumStr = request.getParameter("pageNum");
        int pageNum = 1;
        if (!StringUtils.isEmpty(pageNumStr)) {
            pageNum = Integer.valueOf(pageNumStr);
        }
        String numPerPageStr = request.getParameter("numPerPage");
        int numPerPage = 15;
        if (!StringUtils.isEmpty(numPerPageStr)) {
            numPerPage = Integer.parseInt(numPerPageStr);
        }
        return new PageParam(pageNum, numPerPage);
    }

}
