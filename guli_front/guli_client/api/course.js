import request from '@/utils/request'

export default {
  //课程条件查询加分页
  getPageList(page, limit,couresQueryFront) {
    return request({
      url: `/eduservice/courseFront/pageCourseCondition/${page}/${limit}`,
      method: 'post',
      data:couresQueryFront
    })
  },
  // 获取课程二级分类
  getNestedTreeList2() {
    return request({
      url: `/eduservice/edu-subject/getAllSubject`,
      method: 'get'
    })
  },
  //获取课程详情信息
  getById(courseId) {
    return request({
      url: `/eduservice/courseFront/geFrontCourseInfo/${courseId}`,
      method: 'get'
    })
  }
}
