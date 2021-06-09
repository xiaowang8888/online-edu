import request from '@/utils/request'

export default {
  //登录
  submitLogin(member) {
    return request({
      url: `/ucenter/member/login`,
      method: 'post',
      data: member
    })
  },
  //根据token获取用户信息
  getLoginInfo() {
    return request({
      url: `/ucenter/member/getUserInfo`,
      method: 'get'
    })
  }
}
