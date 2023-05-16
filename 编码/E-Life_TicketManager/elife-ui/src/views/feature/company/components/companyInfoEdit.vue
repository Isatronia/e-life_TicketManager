<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="公司名称" prop="companyName">
        <el-input
          v-model="form.companyName"
          placeholder="请输入公司名称"
          disabled
        />
      </el-form-item>
      <el-form-item label="公司地址" prop="companyAddress">
        <el-input v-model="form.companyAddress" placeholder="请输入公司地址" />
      </el-form-item>
      <el-form-item label="联系方式" prop="email">
        <el-input v-model="form.email" placeholder="请输入联系方式" />
      </el-form-item>
    </el-form>
    <div class="dialog-footer">
      <el-button type="primary" @click="handleUpdateCompanyInfo"
        >确 定</el-button
      >
      <el-button @click="cancel">重置</el-button>
    </div>
  </div>
</template>
<script>
import { getCompany, updateCompany } from "@/api/feature/company";

export default {
  name: "CompanyInfoEdit",
  props: {
    companyId: {
      type: Number,
      default: () => {
        return undefined;
      },
    },
  },
  data() {
    return {
      // 加载
      loading: true,
      // 表单信息
      form: {},
      rules: {
        companyName: [
          { required: true, message: "公司名称不能为空", trigger: "blur" },
        ],
        createBy: [
          { required: true, message: "创建者不能为空", trigger: "blur" },
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    fetchCompanyInfo() {
      getCompany(this.companyId).then((response) => {
        this.detail = response.data;
        this.loading = false;
      });
    },
    // 重置表单
    resetCompanyForm() {
      if (!this.companyId) return;
      getCompany(this.companyId).then((response) => {
        this.form = response.data;
        this.resetForm("form");
      });
    },
    // 更新公司信息
    handleUpdateCompanyInfo() {
      if (!this.companyId) return;
      updateCompany(this.form).then((response) => {
        this.$modal.msgSuccess("修改成功");
        this.drawer = false;
        this.fetchCompanyInfo();
      });
    },
    cancel() {
      this.resetCompanyForm();
    },
  },
  watch:{
    companyId(newId, oldId){
      this.companyId = newId;
      this.resetCompanyForm();
    }
  },
  mounted(){
    this.resetCompanyForm();
  }
};
</script>