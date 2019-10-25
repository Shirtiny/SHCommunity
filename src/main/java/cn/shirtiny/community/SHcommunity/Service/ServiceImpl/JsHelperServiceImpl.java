package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.PageDTO;
import cn.shirtiny.community.SHcommunity.Service.IjsHelperService;
import cn.shirtiny.community.SHcommunity.Utils.ShArrayQueue;
import cn.shirtiny.community.SHcommunity.Utils.ShComparator.ShPathComparator;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Stack;

@Service
public class JsHelperServiceImpl implements IjsHelperService {


    //创建页码数组
    @Override
    public long[] createPageNumArray(Long curPage, Long pages) {
        long pageNum = curPage - 3;
        ShArrayQueue queue = new ShArrayQueue(7);
        for (int i = 0; i < queue.getMaxSize(); i++) {
            if (pageNum > 0 && pageNum <= pages) {
                queue.add(pageNum);
            }
            pageNum++;
        }
        return queue.toArray();
    }


    //处理和截取面包屑数组
    @Override
    public PageDTO[] cutBreadcrumbArray(PageDTO[] breadcrumb, PageDTO curPage) {

        //新建栈
        Stack<PageDTO> pageStack = new Stack<>();

        //标识是否page已经存在
        boolean isExsit = false;

        //若session中能取到值
        if (breadcrumb != null && breadcrumb.length != 0) {
            //遍历数组
            for (PageDTO pageDTO : breadcrumb) {

                //只要当前元素path的长度比curPage的path长度小或相等,元素就入栈
                if (pageDTO.getPath().split("/").length <= curPage.getPath().split("/").length) {
                    pageStack.push(pageDTO);
                }

                //根据路径，比较page与要存入的page相同时
                if (curPage.getPath().equals(pageDTO.getPath())) {

                    //相同则表示curPage已经存在，不需要存入curPage，停止继续入栈，舍弃数组以后的元素
                    isExsit = true;

                    //退出数组遍历
                    break;
                }


            }
        } else {
            //如果在session中取不到值
            //只要当前的访问页不是主页
            if (!curPage.getPath().equals("/")) {
                //存入一个主页
                PageDTO homePage = new PageDTO("主页", "/");
                pageStack.push(homePage);
            }
        }

        //在数组中不存在curPage时，存入curPage
        if (!isExsit) {
            pageStack.push(curPage);
        }


        //排序 常规写法
//        Comparator<PageDTO> c=new ShPathComparator();
//        pageStack.sort(c);

        /*
        //lambda表达式写法
        pageStack.sort(
                (page1,page2)->{
                    String[] split1 = page1.getPath().split("/");
                    String[] split2 = page2.getPath().split("/");
                    return split1.length-split2.length;
                }
        );
        */
        //或者
        pageStack.sort(
                Comparator.comparingInt(page -> page.getPath().split("/").length)
        );

        //把已入栈元素转为数组，用于覆盖原数组，此处利用toArray()带参数的方法
        return pageStack.toArray(new PageDTO[0]);
    }
}
