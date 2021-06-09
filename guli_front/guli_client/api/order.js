import request from '@/utils/request'

export default {

  //1、创建订单
  createOrder(cid) {
    return request({
      url: '/eduorder/order/createOrder/'+cid,
      method: 'post'
    })
  },
  //2、根据id获取订单
  getById(oid) {
    return request({
      url: '/eduorder/order/getOrder/'+oid,
      method: 'get'
    })
  },
  //3、生成微信支付二维码
  createNative(oid) {
    return request({
      url: '/eduorder/pay-log/createNative/'+oid,
      method: 'get'
    })
  },
  //4、根据id获取订单支付状态
  queryPayStatus(oid) {
    return request({
      url: '/eduorder/pay-log/queryPayStatus/'+oid,
      method: 'get'
    })
  }
}
