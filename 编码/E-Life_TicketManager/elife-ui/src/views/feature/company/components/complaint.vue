<template>
  <div>
    <el-card class="box-card employee-container">
      <div slot="header" class="clearfix">
        <span>公司员工</span>
        <!-- <el-button style="float: right; padding: 3px 0" type="text"
          >操作按钮</el-button
        > -->
      </div>
      <div class="complaint-container">
        <div v-for="i in employeeList" :key="i.userId" class="complaint-item">
          <el-image :src="i.avatar" class="img-circle img-sm">
            <div slot="error">
              <el-image
                class="img-circle img-sm"
                src="http://img.naragnova.icu/avatar/default.png"
              ></el-image>
            </div>
            <div slot="placeholder">
              <el-image
                class="img-circle img-sm"
                src="http://img.naragnova.icu/avatar/default.png"
              ></el-image>
            </div>
          </el-image>
          <p>{{ i.nickName }}</p>
          <div>
            <el-button type="primary" @click="handleComplaint(i)" size="mini"
              >投诉</el-button
            >
          </div>
        </div>
      </div>
    </el-card>

    <el-drawer
      title="投诉员工"
      direction="rtl"
      append-to-body
      size="60vw"
      :visible.sync="showComplaintDrawer"
    >
      <el-card v-if="target != undefined">
        <div slot="header">
          <el-tag type="info"> 员工昵称 </el-tag>
          {{ target.nickName }}
        </div>
        <div>
          <el-form ref="form" :model="complaintDetail" :rules="rules">
            <el-form-item label="投诉内容">
              <el-input
                type="textarea"
                :rows="10"
                v-model="complaintDetail.content"
                placeholder="请填写投诉内容"
              />
            </el-form-item>
          </el-form>
          <el-button type="primary" @click="makeComplaint">提交</el-button>
          <el-button @click="cancel">取消</el-button>
        </div>
      </el-card>
    </el-drawer>
  </div>
</template>

<script>
import { listUserByCompany } from "@/api/system/user";

import { addComplaint } from "@/api/feature/complaint";

export default {
  name: "Complaint",
  props: {
    companyId: undefined,
  },
  data() {
    return {
      employeeList: [],
      target: undefined,
      complaintDetail: {
        complaintTarget: undefined,
        complaintUser: this.$store.state.user.userId,
        content: "",
      },
      rules: {
        complaintTarget: [{ required: true, message: "请选择投诉员工" }],
        content: [{ required: true, message: "请填写投诉内容" }],
      },
      showComplaintDrawer: false,
    };
  },
  methods: {
    fetchCompanyEmployees() {
      if (this.companyId) {
        listUserByCompany(this.companyId).then((response) => {
          this.employeeList = response.rows;
          this.employeeList.forEach((e) => {
            e.avatar = process.env.VUE_APP_BASE_API + e.avatar;
          });
        });
      }
    },
    handleComplaint(target) {
      this.target = target;
      console.log("Complaint!");
      this.complaintDetail.complaintTarget = target.userId;
      this.showComplaintDrawer = true;
    },
    makeComplaint() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          addComplaint(this.complaintDetail).then(() => {
            this.$modal.msgSuccess("投诉成功");
            this.showComplaintDrawer=false;
          });
        }
      });
    },
    cancel(){
      this.complaintDetail.content="";
      this.showComplaintDrawer=false;
    }
  },
  watch: {
    companyId(newId, oldId) {
      this.companyId = newId;
      this.fetchCompanyEmployees();
    },
  },
  created() {},
};
</script>

<style lang="scss">
.employee-container {
  max-height: 30vh;
}
.complaint-container {
  display: flex;
  .complaint-item {
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: space-between;
  }
}
</style>