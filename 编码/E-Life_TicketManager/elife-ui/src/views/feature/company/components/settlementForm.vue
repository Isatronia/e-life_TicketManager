<template>
  <div class="settlement-form">
    <el-container>
      <el-aside>
        <el-steps
          :active="form.status ? form.status + 2 : 0"
          finish-status="success"
          align-center
          direction="vertical"
        >
          <el-step title="等待提交申请"></el-step>
          <el-step title="已提交申请"></el-step>
          <el-step title="已通过审批"></el-step>
        </el-steps>
      </el-aside>
      <el-main>
        <el-form ref="form" :model="form" :rules="rules" label-width="12%">
          <el-form-item label="公司名称" prop="companyName">
            <el-input
              v-model="form.companyName"
              placeholder="请输入公司名称"
              maxlength="20"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="公司地址" prop="companyAddress">
            <el-input
              v-model="form.companyAddress"
              placeholder="请输入公司地址"
              maxlength="40"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="电子邮箱" prop="email">
            <el-input
              v-model="form.email"
              placeholder="请输入电子邮箱"
              maxlength="20"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="成立时间" prop="foundTime">
            <el-date-picker
              clearable
              v-model="form.foundTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择公司成立时间"
              :default-value="new Date()"
              :picker-options="foundTimePickerOptions"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="营业执照">
            <image-upload v-model="form.certificate" />
          </el-form-item>
          <el-form-item label="其他材料">
            <file-upload v-model="form.additionalInfo" />
          </el-form-item>
          <el-form-item label="服务单数量限制" prop="ticketLimit">
            <el-input
              v-model="form.ticketLimit"
              placeholder="请输入公司服务单数量限制"
              type="number"
            />
          </el-form-item>
          <el-divider></el-divider>
          <el-form-item>
            <div class="dialog-footer">
              <el-button type="primary" @click="handleSubmit">确 定</el-button>
              <el-button type="info" @click="queryCompany">重 置</el-button>
              <el-button type="warning" @click="reset">清 空</el-button>
              <el-button type="info" @click="handleTest">测 试</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import store from "@/store";
import {
  getCompanyByMaster,
  addCompany,
  updateCompany,
} from "@/api/feature/company";
export default {
  name: "SettlementForm",
  data() {
    return {
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        email: [
          {
            required: true,
            message: "电子邮箱不能为空",
            trigger: "blur",
          },
          {
            type: "email",
            message: "电子邮箱格式不正确",
            trigger: "blur",
          },
        ],
        companyName: [
          { required: true, message: "公司名称不能为空", trigger: "blur" },
        ],
        certificate: [
          { required: true, message: "请提交营业执照", trigger: "blur" },
        ],
        foundTime: [
          { required: true, message: "成立时间不能为空", trigger: "blur" },
        ],
        ticketLimit: [
          {
            type:"number",
            min:0,
            max:2147483647,
            message: "请填写0-2147483647之间的数字",
            transform: num=>{
              return Number(num);
            }
          },
        ],
      },
      foundTimePickerOptions: {
        disabledDate: this.dateFilter,
      },
    };
  },
  props: {},
  methods: {
    queryCompany() {
      getCompanyByMaster(this.$store.state.user.userId).then((response) => {
        console.log(response);
        if (response.data) {
          this.form = response.data;
          this.resetForm("form");
        } else {
          this.reset();
        }
      });
    },
    handleSubmit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.companyId != null) {
            updateCompany(this.form).then((response) => {
              this.$modal.msgSuccess("公司信息修改成功");
              this.queryCompany();
            });
          } else {
            addCompany(this.form).then((response) => {
              this.$modal.msgSuccess("申请提交成功");
              this.queryCompany();
            });
          }
        }
      });
    },
    reset() {
      this.form = {
        companyId: null,
        companyName: null,
        companyAddress: null,
        email: null,
        foundTime: null,
        certificate: null,
        additionalInfo: null,
        applyDate: null,
        companyMaster: null,
        ticketLimit: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      };
      this.resetForm("form");
    },
    handleTest() {
      this.$modal.msgSuccess("已提交申请");
    },
    dateFilter(date) {
      return date >= new Date();
    },
  },
  created() {
    this.queryCompany();
  },
};
</script>

<style lang="scss">
.settlement-form {
  overflow: auto;
}

.dialog-footer {
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-around;
}
</style>