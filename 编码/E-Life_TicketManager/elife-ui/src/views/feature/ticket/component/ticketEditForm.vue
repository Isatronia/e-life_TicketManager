<template>
  <div>
    <el-card>
      <div slot="header">{{ ticket ? ticket.ticketTitle : "UNDEFINED" }}</div>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="服务单标题" prop="ticketTitle">
          <el-input v-model="form.ticketTitle" placeholder="请输入服务单标题" />
        </el-form-item>
        <el-form-item>
          <el-input
            type="text"
            v-model="form.ticketType"
            placeholder="请选择服务单类型"
          ></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </el-card>
  </div>
</template>
<script>
import { getTicket, updateTicket } from "@/api/feature/ticket";

export default {
  name: "TicketEditForm",
  props: {
    ticket: null,
  },
  data() {
    return {
      form: {},
      rules: {
        ticketTitle: [{ required: true, message: "服务单标题不能为空" }],
        ticketType: [{}],
      },
    };
  },
  methods: {
    reset() {
      if (null != this.ticket) {
        getTicket(this.ticket).then((response) => {
          this.form = response.data;
        });
      }
    },
    cancel() {
      this.reset();
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          updateTicket(this.form).then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.$emit("refresh");
          });
        }
      });
    },
  },
  watch:{
    ticket(oldTicket, newTicket){
      this.reset();
    }
  },
  mounted() {
    this.reset();
  },
};
</script>