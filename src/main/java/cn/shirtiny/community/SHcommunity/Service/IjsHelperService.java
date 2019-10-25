package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.PageDTO;

public interface IjsHelperService {

    //根据当前页和总页数，创建一个页码数组，帮助前端js
    long[] createPageNumArray(Long curPage,Long pages);

    //面包屑导航 辅助service
    PageDTO[] cutBreadcrumbArray(PageDTO[] breadcrumb,PageDTO curPage);

}
