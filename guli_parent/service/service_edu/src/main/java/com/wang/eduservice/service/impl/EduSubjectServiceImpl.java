package com.wang.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.eduservice.listener.SubjectExcelListener;
import com.wang.eduservice.pojo.EduSubject;
import com.wang.eduservice.mapper.EduSubjectMapper;
import com.wang.eduservice.pojo.excel.SubjectData;
import com.wang.eduservice.pojo.subject.OneSubject;
import com.wang.eduservice.pojo.subject.TwoSubject;
import com.wang.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-22
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {



    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            //获取文件的输入流
            InputStream inputStream = file.getInputStream();
            //读取文件
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取所有课程分类
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneEduSubjectList = baseMapper.selectList(wrapperOne);

        //查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> TwoEduSubjectList = baseMapper.selectList(wrapperTwo);

        List<OneSubject> list = new ArrayList<>();
        //填充一级分类
        for(int i=0;i<oneEduSubjectList.size();i++){
            String id = oneEduSubjectList.get(i).getId();
            List<TwoSubject> twoSubjectList=new ArrayList<>();
            //填充二级分类
            for(int j=0;j<TwoEduSubjectList.size();j++){
                if(TwoEduSubjectList.get(j).getParentId().equals(id)){
                    TwoSubject twoSubject = new TwoSubject();
                    twoSubject.setId(TwoEduSubjectList.get(j).getId());
                    twoSubject.setTitle(TwoEduSubjectList.get(j).getTitle());
                    twoSubjectList.add(twoSubject);
                }
            }
            // System.out.println(twoSubjectList);
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(id);
            oneSubject.setTitle(oneEduSubjectList.get(i).getTitle());
            oneSubject.setChildren(twoSubjectList);
            list.add(oneSubject);

        }
        return list;
    }
}
