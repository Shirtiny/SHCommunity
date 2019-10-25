package cn.shirtiny.community.SHcommunity.Utils.ShComparator;

import cn.shirtiny.community.SHcommunity.DTO.PageDTO;

import java.util.Comparator;

//路径比较器
public class ShPathComparator implements Comparator<PageDTO> {

    @Override
    public int compare(PageDTO page1, PageDTO page2) {
        String[] split1 = page1.getPath().split("/");
        String[] split2 = page2.getPath().split("/");

        return split1.length-split2.length;
    }
}
