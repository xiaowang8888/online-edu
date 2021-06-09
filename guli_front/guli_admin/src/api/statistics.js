import request from '@/utils/request'

export default {

  //手动生成数据
  createStatistics(day) {
    return request({
      url: `/statistics/daily/${day}`,
      method: 'post'
    })
  },
  //获取图表数据
  showChart(searchObj) {
    return request({
      url: `/statistics/daily/showchart/${searchObj.begin}/${searchObj.end}/${searchObj.type}`,
      method: 'get'
    })
  }
}
