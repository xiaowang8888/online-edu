import request from '@/utils/request'

export default {
  //根据手机号码发送短信
  getMobile(mobile) {
    return request({
      url: `/edumsm/msm/sendMessage/${mobile}`,
      method: 'get'
    })
  },
  //用户注册
  submitRegister(registerVo) {
    return request({
      url: `/ucenter/member/register`,
      method: 'post',
      data: registerVo
    })
  }
}
