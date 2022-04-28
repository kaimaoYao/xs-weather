<template>
  <el-button @click="download" type="primary">
    Download
  </el-button>
</template>
<script>
// import {Message} from 'element-ui'
import axios from 'axios'

export default {
  name: 'Download',
  data () {
    return {}
  },
  methods: {
    download () {
      axios({
        url: '/api/weather/download',
        method: 'get',
        responseType: 'blob'
      }).then(res => {
        console.info(res)
        if (res && res.data != null) {
          let data = res.data
          let name = Date.parse(new Date())
          console.info(data)
          this.saveData2File(data, name)
        }
      })
    },
    // save data to file
    saveData2File (data, name) {
      let blob = new Blob([data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'})
      let url = window.URL.createObjectURL(blob)
      let aLink = document.createElement('a')
      aLink.style.display = 'none'
      aLink.href = url
      aLink.setAttribute('download', name + '.xls')
      document.body.appendChild(aLink)
      aLink.click()
      document.body.removeChild(aLink)
      window.URL.revokeObjectURL(url)
    }
  }
}
</script>
<style scoped>

</style>
