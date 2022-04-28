import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import UploadAndDownload from '@/components/UploadAndDownload'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/uploadAndDownload',
      name: 'UploadAndDownload',
      component: UploadAndDownload
    },
    {
      path: '/',
      name: 'Login',
      component: Login
    }
  ]
})
