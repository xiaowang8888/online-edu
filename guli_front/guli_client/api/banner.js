import request from '@/utils/request'

export default {
  //前台获取所有banner
  getList() {
    return request({
      url: `/educms/bannerFront/getAllBanner`,
      method: 'get'
    })
  }
}
