import request from '@/utils/request'

export default {

  // 添加课时
  addVideo(eduVideo){
    return request({
      url: `/eduservice/edu-video/addVideo`,
      method: 'post',
      data:eduVideo
    })
  },
  // 根据id查询课时
  getVideo(id){
    return request({
      url: `/eduservice/edu-video/getVideo/${id}`,
      method: 'get'
    })
  },
  // 修改视频小节
  updateVideo(eduVideo){
    return request({
      url: `/eduservice/edu-video/updateVideo`,
      method: 'post',
      data:eduVideo
    })
  },
  // 删除章节
  deleteVideo(id){
    return request({
      url: `/eduservice/edu-video/deleteVideo/${id}`,
      method: 'delete'
    })
  },
  //根据视频id删除阿里云视频
  deleteAlyVideo(id){
    return request({
      url: `/eduvod/video/deleteVideo/${id}`,
      method: 'delete'
    })
  }
}
