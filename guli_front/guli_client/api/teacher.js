import request from '@/utils/request'

export default {
  //讲师分页
  getPageList(page, limit) {
    return request({
      url: `/eduservice/teacherFront/getTeacherFrontList/${page}/${limit}`,
      method: 'get'
    })
  },
  //讲师详情页获取讲师信息和课程信息
  getCourseAndTeacher(id){
    return request({
      url: `/eduservice/teacherFront/getCourse/${id}`,
      method: 'get'
    })
  }
}
