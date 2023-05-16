<template>
  <div>
    <el-card
      class="box-card"
      style="
         {
          max-height: 20vh;
        }
      "
    >
      <div slot="header" class="clearfix">
        <span>公司员工</span>
        <!-- <el-button style="float: right; padding: 3px 0" type="text"
          >操作按钮</el-button
        > -->
      </div>
      <div
        style="
           {
            display: flex;
          }
        "
      >
        <div
          v-for="i in employeeList"
          :key="i.userId"
          style="
             {
              flex-direction: row;
              flex-wrap: nowrap;
              justify-content: space-between;
            }
          "
        >
          <img :src="i.avatar" class="img-circle img-sm" />
          <p>{{ i.nickName }}</p>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { listUserByCompany } from "@/api/system/user";

export default {
  name: "Complaint",
  props: {
    companyId: undefined,
  },
  data() {
    return {
      employeeList: [],
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
</style>