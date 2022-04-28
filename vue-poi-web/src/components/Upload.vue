<template>
    <el-upload
      class="avatar-uploader"
      action="/api/weather/uploadExcel"
      :show-file-list="false"
      :on-success="handleAvatarSuccess">
      <i class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
</template>

<script>
import {Message} from 'element-ui'

export default {
  name: 'Upload',
  data () {
    return {
      imageUrl: ''
    }
  },
  methods: {
    handleAvatarSuccess (res, file) {
      console.info(res)
      if (res.data.code === 200) {
        if (res.data.success) {
          Message({message: 'Upload success', center: true, type: 'success', offset: 100})
        } else {
          Message({message: 'Upload fail', center: true, type: 'error', offset: 100})
        }
      }
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
