<!-- apply-ticket.vue -->
<!-- 申请服务单组件，用于申请服务单时提交分类信息 -->
<template>
  <div v-if="companyId == undefined">
    <el-row>
      <i class="el-icon-error"></i>
      <p>请指定要申请的公司</p>
    </el-row>
    <el-row class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
    </el-row>
  </div>
  <div v-else>
    <el-card class="box-card">
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="标题" prop="ticketTitle">
          <el-input
            v-model="form.ticketTitle"
            placeholder="请用一句话描述您的问题"
          ></el-input>
        </el-form-item>
        <el-form-item label="问题类型选择" prop="ticketType">
          <el-select
            no-data-text="无类型"
            v-model="form.ticketType"
            placeholder="请选择问题类型"
          >
            <el-option
              v-for="t in typeList"
              :key="t.typeId"
              :label="t.typeName"
              :value="t.typeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <el-row>
        <div class="dialog-footer">
          <el-button type="primary" @click="handleSubmit">确 定</el-button>
          <el-button @click="reset">取 消</el-button>
          <el-button @click="submitSuccessModal">测 试</el-button>
        </div>
      </el-row>
    </el-card>
  </div>
</template>
      
  <script>
import { getCompany } from "@/api/feature/company";
import {
  listTicketType,
  getTicketType,
  delTicketType,
  addTicketType,
  updateTicketType,
} from "@/api/feature/ticketType";

import {
  listSubscribe,
  subscribeCompany,
  unsubscribeCompany,
} from "@/api/feature/subscribe";
import { applyTicket } from "@/api/feature/ticket";

export default {
  name: "ApplyTicketForm",
  props: {
    companyId: undefined,
  },
  data() {
    return {
      form: {
        ticketTitle: undefined,
        ticketType: undefined,
        status: 0,
      },
      rules: {},
      typeList: [],
    };
  },
  methods: {
    fetchTicketType() {
      let param = {
        companyId: this.companyId,
      };
      listTicketType(param).then((response) => {
        console.log("fetching type...");
        this.typeList = response.rows;
      });
    },
    reset() {
      this.form = {
        ticketTitle: undefined,
        ticketType: undefined,
      };
    },
    ticketLimitOverflowError(){
      this.$modal.msgError("申请数量达到上限，请关闭后再尝试申请。");
    },
    ticketApplyError(){
      this.$modal.msgError("出现错误，请联系管理员");
    },
    submitSuccessModal(){
      this.$modal.msgSuccess("服务单申请成功");
    },
    handleSubmit() {
      this.form.userId = this.$store.state.user.userId;
      this.form.companyId = this.companyId;
      applyTicket(this.form).then(
        (response) => {
          this.$modal.msgSucess("已成功申请服务单");
        },
        (reject) => {
          this.$modal.msgError("出现错误，请检查");
        }
      );
    },
  },
  created() {
    if (!this.companyId) {
      return;
    }
    getCompany(this.companyId).then((response) => {
      this.detail = response.data;
      this.loading = false;
    });
    this.fetchTicketType();
  },
};
</script>
  
  <style lang="scss">
.subscribe-button {
  float: right;
}
</style>