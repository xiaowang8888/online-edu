import request from '@/utils/request'

export default {

  // 添加课程信息
  addCourseInfo(courseInfo){
    return request({
      url: `/eduservice/edu-course/addCourseInfo`,
      method: 'post',
      data:courseInfo
    })
  },
  // 获取讲师列表
  getTeacherList(){
    return request({
      url: `/eduservice/edu-teacher/findAll`,
      method: 'get'
    })
  },
  //根据id获取课程基本信息
  getCourseInfo(id){
    return request({
      url: `/eduservice/edu-course/getCourseInfo/${id}`,
      method: 'get'
    })
  },
  //修改课程信息
  updateCourseInfo(courseInfo){
    return request({
      url: `/eduservice/edu-course/updateCourseINfo`,
      method: 'post',
      data:courseInfo
    })
  },
  //返回即将发布课程信息
  getCoursePublish(courseId) {
    return request({
      url: `/eduservice/edu-course/getCoursePublish/${courseId}`,
      method: 'get'
    })
  },
  //最终发布课程
  publishCourse(courseId){
    return request({
      url: `/eduservice/edu-course/publishCourse/${courseId}`,
      method: 'post'
    })
  },
  // 1.课程列表（条件查询分页）
  // current 当前页 ，limit 每页记录数，courseQuery条件对象
  getCourseListPage(current,limit,courseQuery){
    return request({
      url: `/eduservice/edu-course/pageCourseCondition/${current}/${limit}`,
      method: 'post',
      data:courseQuery
    })
  },
  //根据id删除课程
  removeCourseById(courseId){
    return request({
      url: `/eduservice/edu-course/deleteCourse/${courseId}`,
      method: 'delete'
    })
  }

}
