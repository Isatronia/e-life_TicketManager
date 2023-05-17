<template>
  <div class="app-container">
    <el-container>
      <el-header>
        <el-row :gutter="20">
          <el-col :span="16">
            <h1>{{ detail.companyName }}</h1>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="handleApplyTicket" plain
              >申请服务</el-button
            >
          </el-col>
          <el-col :span="4">
            <div class="subscribe-button">
              <div v-if="subscribed">
                <el-button
                  type="success"
                  icon="el-icon-check"
                  @click="unsubscribe"
                  circle
                ></el-button>
              </div>
              <div v-else>
                <el-button
                  type="warning"
                  icon="el-icon-star-off"
                  @click="subscribe"
                  circle
                ></el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row>
          <complaint :companyId="this.detail.companyId"></complaint>
        </el-row>
        <el-row>
          <el-card class="box-card">
            <div slot="header">公司信息</div>
          </el-card>
        </el-row>
      </el-main>
    </el-container>

    <el-drawer
      title="申请服务"
      direction="rtl"
      :visible.sync="drawer"
      :wrapperClosable="true"
      size="66%"
      append-to-body
    >
      <apply-ticket-form :companyId="detail.companyId" />
    </el-drawer>
  </div>
</template>
    
<script>
import { getCompany } from "@/api/feature/company";

import {
  listSubscribe,
  subscribeCompany,
  unsubscribeCompany,
} from "@/api/feature/subscribe";

import { listTicketType } from "@/api/feature/ticketType";

import { listNotice } from "@/api/system/notice";

import { applyTicket } from "@/api/feature/ticket";

import complaint from "./components/complaint.vue";

import companyInfoEdit from "./components/companyInfoEdit.vue";

import { checkPermi, checkRole } from "@/utils/permission"; // 权限判断函数
import CompanyInfoEdit from "./components/companyInfoEdit.vue";
// import ApplyTicketForm from './componenets/applyTicket.vue';
import applyTicketFrom from "./components/applyTicketForm.vue";

export default {
  name: "CompanyDetail",
  components: {
    complaint,
    companyInfoEdit,
    "apply-ticket-form": applyTicketFrom,
  },
  data() {
    return {
      // 加载
      loading: true,
      // 订阅,现在用来判断是否被认定为员工
      subscribed: false,
      // 公司id
      companyId: undefined,
      // 是否显示修改信息的抽屉
      drawer: false,
      // 详情
      detail: {},
      // 查询参数，用来查询问题类型
      queryTypeParams: {
        pageNum: 1,
        pageSize: 10,
        typeCompany: null,
        typeName: null,
        description: null,
      },
      // 公告列表
      noticeList: [],
      // 问题类型列表
      ticketTypeList: [],
      // 公告数量
      noticeCount: 0,
      // 用户名
      username: null,
      // 更新公司信息时用到的数据
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
    checkPermi,
    checkRole,
    // 拉取公司信息
    fetchCompanyInfo() {
      getCompany(this.companyId).then((response) => {
        this.detail = response.data;
        this.loading = false;
      });
    },
    // 拉取订阅信息
    fetchSubscribeInfo() {
      listSubscribe({
        userId: this.$store.state.userId,
        companyId: this.companyId,
      }).then((response) => {
        if (response.total != 0) {
          this.subscribed = true;
        }
      });
    },
    // 取消订阅
    unsubscribe() {
      unsubscribeCompany(this.companyId).then((response) => {
        this.subscribed = false;
      });
    },
    // 订阅公司
    subscribe() {
      subscribeCompany(this.companyId).then((response) => {
        this.subscribed = true;
      });
    },
    // 拉取公司公告
    fetchCompanyNotice() {
      if (this.companyId != undefined) {
        listNotice({
          companyId: this.companyId,
          pageNum: 1,
          pageSize: 10,
        }).then((response) => {
          this.noticeList = response.rows;
          this.noticeCount = response.total;
        });
      }
    },
    // 提交服务申请
    handleApplyTicket() {
      this.drawer = true;
    },
  },
  created() {
    this.companyId = this.$route.params.companyId;
    if (!this.companyId) {
      return;
    }
    this.fetchCompanyInfo();
    this.fetchSubscribeInfo();
    this.fetchCompanyNotice();
    this.username = this.$store.state.user.name;
  },
};
</script>

<style lang="scss">
.subscribe-button {
  float: right;
}
</style>