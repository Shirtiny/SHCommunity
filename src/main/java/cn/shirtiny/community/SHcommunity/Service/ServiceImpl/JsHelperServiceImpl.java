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
    public long[] createPageNumArray(Long curPage,Long pages){
        long pageNum = curPage - 3;
        ShArrayQueue queue=new ShArrayQueue(7);
        for (int i=0;i<queue.getMaxSize();i++){
            if (pageNum>0 && pageNum<=pages){
                queue.add(pageNum);
            }
            pageNum++;
        }
        return  queue.toArray();
    }


    //处理和截取面包屑数组
    @Override
    public PageDTO[] cutBreadcrumbArray(PageDTO[] breadcrumb,PageDTO curPage) {

        //新建栈
        Stack<PageDTO> pageStack = new Stack<>();

        //标识是否page已经存在
        boolean isExsit = false;

        //若session中能取到值
        if (breadcrumb != null && breadcrumb.length != 0) {
            //遍历数组
            for (PageDTO pageDTO : breadcrumb) {

                //数组内元素入栈
                pageStack.push(pageDTO);

                //根据路径，比较page与要存入的page是否相同
                if (curPage.getPath().equals(pageDTO.getPath())) {

                    //相同则表示curPage已经存在，不需要存入curPage，停止继续入栈，舍弃数组以后的元素
                    isExsit = true;

                    //退出数组遍历
                    break;
                }
            }
            //如果在session中取不到值,不做处理
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
        return pageStack.toArray(new  PageDTO[0]);
    }
}
