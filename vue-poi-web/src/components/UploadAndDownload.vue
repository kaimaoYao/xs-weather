<template>
  <div>
    <el-header>
      <h1>上传excel  下载天气数据</h1>
    </el-header>
    <el-main>
      <el-row>
       <div class="el-col el-col-12">
         <Upload></Upload>
       </div>
        <div class="el-col el-col-12">
          <Download></Download>
        </div>
      </el-row>
    </el-main>
  </div>

</template>
<script>
import Upload from './Upload'
import Download from './Download'

export default {
  name: 'UploadAndDownload',
  components: {
    Upload,
    Download
  },
  data () {
    return {}
  },
  methods: {}
}
</script>

<style>
.el-container {
  margin-top: 10%;
  width: 380px;
  height: 300px;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  margin: auto;
}
.el-main{
  margin-top: 200px;
}
</style>
