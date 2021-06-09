import request from '@/utils/request'

export default {

  // 1.讲师列表（条件查询分页）
  // current 当前页 ，limit 每页记录数，teacherQuery条件对象
  getTeacherListPage(current,limit,teacherQuery){
    return request({
      // url: '/eduservice/edu-teacher/pageTeacherCondition/'+current+"/"+limit,
      url: `/eduservice/edu-teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      //teacherQuery条件对象，后端使用RequestBody接收数据
      //data表示把对象转换成json传送到接口
      data:teacherQuery
    })
  },

  //删除讲师
  removeTeacherById(id){
    return request({
      url: `/eduservice/edu-teacher/${id}`,
      method: 'delete'
    })
  },
  //添加讲师
  addTeacher(teacher){
    return request({
      url:`/eduservice/edu-teacher/addTeacher`,
      method:'post',
      //teacher对象，后端使用RequestBody接收数据
      //data表示把对象转换成json传送到接口
      data:teacher
    })
  },
  //根据ID回显讲师信息
  getInfo(id){
    return request({
      url:`/eduservice/edu-teacher/getTeacher/${id}`,
      method:'get',
    })
  },
  //根据ID修改讲师
  updateTeacher(teacher){
    return request({
      url:`/eduservice/edu-teacher/updateTeacher`,
      method:'post',
      data:teacher
    })
  }
}
