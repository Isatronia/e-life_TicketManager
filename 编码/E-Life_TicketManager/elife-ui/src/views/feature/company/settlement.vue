<template>
  <div class="applicaion-main">
    <el-card class="box-card">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="公司入驻" name="form">
          <settlement-form></settlement-form>
        </el-tab-pane>
        <el-tab-pane
          label="员工管理"
          name="employee"
          v-if="null != company && checkRole(['isacompanytester', 'admin'])"
          ><employee :companyId="company.companyId"></employee>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import store from "@/store";
import settlementForm from "./components/settlementForm.vue";
import employee from "./components/employee.vue";
import { checkPermi, checkRole } from "@/utils/permission"; // 权限判断函数
import { getCompanyByMaster } from "@/api/feature/company";
export default {
  name: "Settlement",
  components: {
    settlementForm,
    employee,
  },
  data() {
    return {
      activeName: "form",
      company: undefined,
    };
  },
  props: {},
  methods: {
    checkPermi,
    checkRole,
    handleClick(tab, event) {
      console.log(tab, event);
    },
    fetchCompanyByUser() {
      getCompanyByMaster(this.$store.state.user.userId).then((response) => {
        this.company = response.data;
      });
    },
  },
  created() {
    this.fetchCompanyByUser();
  },
};
</script>

<style lang="scss">
.applicaion-main {
  padding-left: 2vh;
  padding-right: 2vh;
  max-height: 80vh;
}
</style>