<template>
  <el-card>
    <el-container>
      <el-aside width="40vh">
        <div>
          <div class="text-center">
            <img :src="userDetail.avatar" class="user-avatar" />
          </div>
          <ul class="list-group list-group-striped">
            <li class="list-group-item">
              <svg-icon icon-class="user" />用户昵称
              <div class="pull-right">{{ userDetail.nickName }}</div>
            </li>
            <li class="list-group-item">
              <div class="text-center">
                <el-button type="primary" :disabled="!inviteable"  @click="handleOffer()">
                  邀请该用户
                </el-button>
              </div>
            </li>
          </ul>
          <el-divider />
        </div>
        <el-form
          :model="queryUserParams"
          ref="userSearch"
          size="small"
          :inline="true"
          @submit.native.prevent
          class="search-form"
        >
          <el-form-item label="用户ID" prop="userId">
            <el-input
              class="form-input"
              v-model="queryUserParams.userId"
              placeholder="请输入用户ID"
              clearable
              @keydown.enter.native="handleQueryUser()"
              @keyup.enter.native="() => {}"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              class="pull-right"
              type="primary"
              icon="el-icon-search"
              size="mini"
              circle
              @click="handleQueryUser"
            ></el-button>
          </el-form-item>
        </el-form>
      </el-aside>
      <el-main>
        <el-table :data="employeeList" height="250">
          <el-table-column label="头像" prop="avatar" align="center">
            <template slot-scope="scope">
              <img :src="scope.row.avatar" class="img-circle img-sm" />
            </template>
          </el-table-column>
          <el-table-column label="昵称" prop="nickName" align="center" />
          <el-table-column label="状态" prop="status">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.company_offer" :value="scope.row.status" />
            </template>
          </el-table-column>
          <el-table-column align="center">
            <template slot="header" slot-scope="scope">
              <el-row>
                <el-col :span="14"
                  ><div class="pull-right table-header">操作</div></el-col
                >
                <el-col :span="10">
                  <div class="pull-right">
                    <el-button
                      icon="el-icon-refresh"
                      circle
                      @click="fetchCompanyEmployees"
                    /></div
                ></el-col>
              </el-row>
            </template>
            <template slot-scope="scope">
              <el-button
                class="push-right"
                @click="handleRevokeUser(scope.row.userId)"
                >撤销用户</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-card>
</template>

<script>
import {
  addSubscribe,
  deleteSubscribe,
  listSubscribe,
} from "@/api/feature/subscribe";
import { getUserBasicInfo, listUserByCompany } from "@/api/system/user";

export default {
  name: "Employee",
  dicts: ["company_offer"],
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
      employeeList: [],
      queryUserParams: {
        userId: undefined,
      },
      userDetail: undefined,
      inviteable: false,
    };
  },
  methods: {
    handleQueryUser() {
      if (undefined == this.queryUserParams.userId) return;
      this.loading = true;
      this.inviteable = false;
      getUserBasicInfo(this.queryUserParams.userId).then((response) => {
        if (undefined == response.data) {
          return;
        }
        // 查询到用户信息，开始纠正流程
        this.userDetail = response.data;
        if (undefined == this.userDetail.avatar) {
          this.userDetail.avatar =
            "http://img.naragnova.icu/avatar/default.png";
        }
        if (
          !(
            this.userDetail.avatar.startsWith("http://") ||
            this.userDetail.avatar.startsWith("https://")
          )
        ) {
          this.userDetail.avatar =
            process.env.VUE_APP_BASE_API + this.userDetail.avatar;
        }
        this.inviteable = true;
        this.loading = false;
      });
    },
    handleInviteUser() {
      if (!this.userDetail) {
        return;
      }
      let param = {
        userId: this.userDetail.userId,
        companyId: this.companyId,
        status: 0,
      };
      addSubscribe(param).then((response) => {
        this.$modal.msgSuccess("修改成功");
        this.userDetail = undefined;
      });
    },
    fetchCompanyEmployees() {
      if (this.companyId) {
        listUserByCompany(this.companyId).then((response) => {
          this.employeeList = response.rows;
          this.employeeList.forEach((e) => {
            e.avatar = e.avatar
              ? process.env.VUE_APP_BASE_API + e.avatar
              : "http://img.naragnova.icu/avatar/default.png";
            listSubscribe({ userId: e.userId, companyId: this.companyId }).then(
              (res) => {
                if (!res.row) {
                  e.status = 0;
                  return;
                }
                e.status = res.row[0].status;
              }
            );
          });
        });
      }
    },
    handleOffer() {
      if (!this.queryUserParams.userId) {
        return;
      }
      let params = {
        userId: this.queryUserParams.userId,
        companyId: this.companyId,
        status: 0,
      };
      addSubscribe(params).then(
        (response) => {
          this.$modal.msgSuccess("已发送邀请");
        },
        (reject) => {
          this.$modal.msgError("发送失败");
        }
      );
    },
    handleRevokeUser(userId) {
      let param = {
        userId: userId,
        companyId: this.companyId,
      };
      deleteSubscribe(param).then((response) => {
        this.$modal.msgSuccess("操作成功");
      });
    },
  },
  created() {
    this.userDetail = {
      avatar: "http://img.naragnova.icu/avatar/default.png",
      nickName: "未选择用户",
    };
    this.fetchCompanyEmployees();
  },
};
</script>

<style lang="scss">
.user-avatar {
  border-radius: 4vh;
}
.search-form {
  padding: 0;
  margin: 0vh 2vh;
  width: 30vh;
  overflow: hidden;
  display: flex;
  justify-content: space-around;
  flex-direction: row;
  flex-wrap: nowrap;
  .el-form-item {
    margin: 0;
    padding: 0;
  }
  .form-input {
    width: 15vh;
  }
}
.employee-info-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  align-content: center;
}
.table-header {
  margin-top: auto;
  position: relative;
  vertical-align: middle;
}
</style>