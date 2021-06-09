<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类" @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.value"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>

      <!-- 课程封面-->
      <el-form-item label="课程封面">

        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/eduoss/fileoss'"
          class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>

      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>
<script>
  //引用course.js文件
  import course from "@/api/edu/course"
  import subject from "@/api/edu/subject"
  //引入组件
  import Tinymce from '@/components/Tinymce'

  export default {
    //声明组件
    components: { Tinymce },
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        courseInfo:{
          title: '',
          subjectId:'',       //二级分类ID
          subjectParentId:'', //一级分类ID
          teacherId: '',
          lessonNum: 0,
          description: '',
          //默认封面
          cover: '/static/cover.jpg',
          price: 0
        },
        courseId:'',
        teacherList:[],
        subjectOneList:[],  //一级分类
        subjectTwoList:[],   //二级分类
        BASE_API: process.env.BASE_API // 接口API地址
      }
    },

    created() {
      this.init()
    },

    watch:{       //监听，当发生路由切换时执行此方法
      $route(to,from){
        this.init()
      }
    },

    methods: {

      init(){
        if(this.$route.params&&this.$route.params.id){
          this.courseId=this.$route.params.id
          this.getCourseInfo()
        }else {
          this.courseInfo={}
          this.courseInfo.cover='/static/cover.jpg'
          this.getTeacherList()
          this.getOneSubjectList()
        }
      },

      saveOrUpdate() {
        //根据id判断是添加还是修改
        if(this.courseInfo.id){
          this.updateCourseInfo()
        }else {
          this.addCourseInfo()
        }
      },
      //获取讲师列表
      getTeacherList(){
        course.getTeacherList()
        .then(response=>{
          this.teacherList=response.data.teachers
        })
      },
      //获取一级分类列表
      getOneSubjectList(){
        subject.getSubjectList()
        .then(response=>{
          this.subjectOneList=response.data.list
        })
      },
      //当点击一级分类时，触发change事件
      subjectLevelOneChanged(value){  //value就是一级分类的id
        for (let i = 0; i < this.subjectOneList.length; i++) {
          if (this.subjectOneList[i].id === value) {
            this.subjectTwoList = this.subjectOneList[i].children
            this.courseInfo.subjectId = ''
          }
        }
      },
      //封面上传前调用的方法
      beforeAvatarUpload(file){
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },
      //封面上传成功调用的方法
      handleAvatarSuccess(response,file){
        console.log(response)// 上传响应
        console.log(URL.createObjectURL(file.raw))// base64编码
        this.courseInfo.cover = response.data.url
      },
      //获取课程基本信息
      getCourseInfo(){
        course.getCourseInfo(this.courseId)
        .then(response=>{
          this.courseInfo=response.data.courseInfo
          subject.getSubjectList()
          .then(response=>{
            //获取所有一级分类
            this.subjectOneList=response.data.list
            //根据回显一级分类id找到二级分类
            for(var i=0;i<this.subjectOneList.length;i++){
              if(this.courseInfo.subjectParentId==this.subjectOneList[i].id){
                this.subjectTwoList=this.subjectOneList[i].children
              }
            }
          })
          this.getTeacherList()
        })
      },
      //添加课程信息
      addCourseInfo(){
        course.addCourseInfo(this.courseInfo)
          .then(response=>{
            //添加提示信息
            this.$message({
              type:"success",
              message:"添加课程基本信息成功！"
            })
            //跳转到第二步
            this.$router.push({ path: '/course/chapter/'+response.data.courseId })
          })
      },
      //修改课程信息
      updateCourseInfo(){
        course.updateCourseInfo(this.courseInfo)
        .then(response=>{
          //添加提示信息
          this.$message({
            type:"success",
            message:"修改课程基本信息成功！"
          })
          //跳转到第二步
          this.$router.push({ path: '/course/chapter/'+this.courseId })
        })
      }
    }
  }
</script>

<style scoped>
  .tinymce-container {
    line-height: 29px;
  }
</style>
