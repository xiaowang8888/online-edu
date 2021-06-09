import request from '@/utils/request'

export default {
  //查询前8条热门课程
  getCourseList() {
    return request({
      url: `/eduservice/index/course`,
      method: 'get'
    })
  },
  //查询前4条名师
  getTeacherList() {
    return request({
      url: `/eduservice/index/teacher`,
      method: 'get'
    })
  }
}
