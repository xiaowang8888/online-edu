package com.wang.generator.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {

        //写入的文件路径
        String filePath="F:\\write.xlsx";

        EasyExcel.write(filePath,DemoData.class).sheet("学生列表").doWrite(getList());
    }

    //创建一个方法，返回List
    public static List<DemoData> getList(){
        List<DemoData> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("学生"+i);
            list.add(demoData);
        }
        return list;
    }
}
