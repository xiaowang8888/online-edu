<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <!--添加章节按钮    -->
    <el-button type="text" @click="openChapterDialog">添加章节</el-button>

    <!-- 章节 -->
      <ul class="chanpterList">
      <li
        v-for="chapter in chapterVideoList"
        :key="chapter.id">
        <p>
          {{ chapter.title }}

          <span class="acts">
                <el-button type="text" @click="openVideoDialog(chapter.id)">添加课时</el-button>
                <el-button style="" type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
                <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>{{ video.title }}
              <span class="acts">
                        <el-button type="text" @click="openEditVideo(video.id)">编辑</el-button>
                        <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                    </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/video/uploadVideo'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>

    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

  </div>
</template>

<script>

  import chapter from "@/api/edu/chapter"
  import video from "@/api/edu/video"

  export default {
    data() {
      return {
        saveBtnDisabled: false ,// 保存按钮是否禁用
        courseId:'',
        chapterVideoList:[],
        dialogChapterFormVisible:false,
        dialogVideoFormVisible:false,
        chapter:{
          title: '',
          sort: 0
        },
        video: {// 课时对象
          title: '',
          sort: 0,
          free: 0,
          videoSourceId: '',
          videoOriginalName:''
        },
        fileList: [],//上传文件列表
        BASE_API: process.env.BASE_API // 接口API地址
      }
    },

    created() {
      if(this.$route.params&&this.$route.params.id){
        this.courseId=this.$route.params.id
        this.getChapterVideo()
      }
    },


    methods: {
      //==============上传视频相关方法===============================
      //点击X时弹出确认删除框
      beforeVodRemove(file,fileList){
        return this.$confirm(`确认移除${file.name}?`)
      },
      //点击删除确认按钮后执行
      handleVodRemove(file,fileList){
        video.deleteAlyVideo(this.video.videoSourceId)
        .then(response=>{
          //提示信息
          this.$message({
            type:"success",
            message:"删除视频成功！"
          })
          //将filelist清空
          this.fileList=[]
          this.video.videoSourceId = ''
          this.video.videoOriginalName=''
        })
      },
      //视频上传成功调用的方法
      handleVodUploadSuccess(response, file, fileList){
        console.log(response)
        this.video.videoSourceId = response.data.videoId
        this.video.videoOriginalName=file.name
        console.log(this.video)
      },
      //视图上传多于一个视频
      handleUploadExceed(files, fileList){
        this.$message.warning('想要重新上传视频，请先删除已上传的视频')
      },

      //课时表单添加或修改
      saveOrUpdateVideo(){
        if(this.video.id){
          this.updateVideo()
        }else {
          this.addVideo()
        }
      },
      //添加课时
      addVideo(){
        this.video.courseId=this.courseId
        video.addVideo(this.video)
        .then(response=>{
          //关闭弹窗
          this.dialogVideoFormVisible=false
          //提示信息
          this.$message({
            type:"success",
            message:"添加课时成功！"
          })
          //刷新页面
          this.getChapterVideo()
        })
      },
      //课时数据回显
      openEditVideo(id){
        this.dialogVideoFormVisible=true
        video.getVideo(id)
        .then(response=>{
          this.video=response.data.eduVideo
        })
      },
      //修改课时
      updateVideo(){
        video.updateVideo(this.video)
          .then(response=>{
            //关闭弹框
            this.dialogVideoFormVisible=false
            //提示信息
            this.$message({
              type:"success",
              message:"修改课时成功！"
            })
            //刷新页面
            this.getChapterVideo()
          })
      },
      //删除课时
      removeVideo(id){
        this.$confirm('此操作将删除该课时, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          video.deleteVideo(id)
            .then(response=>{
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              //刷新页面
              this.getChapterVideo()
            })
        })
      },
      //弹出课时表单方法
      openVideoDialog(chapterId){
        this.dialogVideoFormVisible=true
        this.video={}
        this.video.chapterId=chapterId
      },
      //删除章节
      removeChapter(chapterId){
        this.$confirm('此操作将删除该章节, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            chapter.deleteChapter(chapterId)
            .then(response=>{
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              //刷新页面
              this.getChapterVideo()
            })
        })
      },
      //打开修改表单并回显
      openEditChapter(chaperId){
        this.dialogChapterFormVisible=true
        chapter.getChapter(chaperId)
        .then(response=>{
          this.chapter=response.data.chapter
        })
      },
      //弹出表单方法
      openChapterDialog(){
        this.dialogChapterFormVisible = true
        this.chapter={}
      },

      previous() {
        this.$router.push({ path: '/course/info/'+this.courseId })
      },

      next() {
        // console.log('next')
        this.$router.push({ path: '/course/publish/'+this.courseId })
      },

      //获取所有章节嵌套信息
      getChapterVideo(){
        chapter.getChapterVideo(this.courseId)
        .then(response=>{
          this.chapterVideoList=response.data.list
        })
      },

      //添加或修改
      saveOrUpdate(){
        if(this.chapter.id){
          this.updateChapter()
        }else {
          this.addChapter()
        }
      },
      //添加
      addChapter(){
        this.chapter.courseId=this.courseId
        chapter.addChapter(this.chapter)
        .then(response=>{
          //关闭弹框
          this.dialogChapterFormVisible=false
          //提示信息
          this.$message({
            type:"success",
            message:"添加章节成功！"
          })
          //刷新页面
          this.getChapterVideo()
        })
      },
      //修改
      updateChapter(){
        chapter.updateChapter(this.chapter)
        .then(response=>{
          //关闭弹框
          this.dialogChapterFormVisible=false
          //提示信息
          this.$message({
            type:"success",
            message:"修改章节成功！"
          })
          //刷新页面
          this.getChapterVideo()
        })
      }
    }
  }
</script>

<style scoped>
  .chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .chanpterList li{
    position: relative;
  }
  .chanpterList p{
    /*float: left;*/
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }
  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList{
    padding-left: 50px;
  }
  .videoList p{
    /*float: left;*/
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }

</style>
