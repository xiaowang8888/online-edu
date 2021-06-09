import request from '@/utils/request'

export default {

  // 获取章节列表
  getChapterVideo(courseId){
    return request({
      url: `/eduservice/edu-chapter/getChapterVideo/${courseId}`,
      method: 'get'
    })
  },
  // 添加章节
  addChapter(eduChapter){
    return request({
      url: `/eduservice/edu-chapter/addChapter`,
      method: 'post',
      data:eduChapter
    })
  },
  // 根据章节id查询章节
  getChapter(chapterId){
    return request({
      url: `/eduservice/edu-chapter/getChapter/${chapterId}`,
      method: 'get'
    })
  },
  // 修改章节
  updateChapter(eduChapter){
    return request({
      url: `/eduservice/edu-chapter/updateChapter`,
      method: 'post',
      data:eduChapter
    })
  },
  // 删除章节
  deleteChapter(chapterId){
    return request({
      url: `/eduservice/edu-chapter/deleteChapter/${chapterId}`,
      method: 'delete'
    })
  }
}
