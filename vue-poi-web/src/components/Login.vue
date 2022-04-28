<template>
  <div class="login">
    <el-header>
      <h1>请先登录</h1>
    </el-header>
    <el-main>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="userName">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="password">
          <el-input placeholder="请输入密码" v-model="form.password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">Login</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </div>
</template>
<script>
import axios from 'axios'
import {Message, Loading} from 'element-ui'

export default {
  name: 'Login',
  data () {
    return {
      form: {
        userName: '',
        password: ''
      }
    }
  },
  methods: {
    onSubmit () {
      console.log('submit!')
      this.login()
    },
    login () {
      let loadingInstance = Loading.service({fullscreen: true})
      axios.post('/api/user/login', this.form)
        .then(res => {
          console.info(res)
          if (res.data.code === 200) {
            if (res.data.success) {
              Message({message: 'Login success', center: true, type: 'success', offset: 100})
              this.$router.push('/uploadAndDownload')
            } else {
              Message({message: 'Login fail', center: true, type: 'error', offset: 100})
            }
            loadingInstance.close()
          }
        })
    }
  }
}

</script>

<style scoped>
.login {
  width: 380px;
  height: 300px;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  margin: auto;
}

.el-form {
  margin-top: 300px;
}

</style>
