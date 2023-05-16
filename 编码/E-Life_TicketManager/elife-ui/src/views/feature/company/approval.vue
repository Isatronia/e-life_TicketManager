<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="公司名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入公司名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司地址" prop="companyAddress">
        <el-input
          v-model="queryParams.companyAddress"
          placeholder="请输入公司地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系方式" prop="email">
        <el-input
          v-model="queryParams.email"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="成立时间" prop="foundTime">
        <el-date-picker
          clearable
          v-model="queryParams.foundTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择成立时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="申请提交时间" prop="applyDate">
        <el-date-picker
          clearable
          v-model="queryParams.applyDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择申请提交时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="负责人" prop="companyMaster">
        <el-input
          v-model="queryParams.companyMaster"
          placeholder="请输入负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="companyList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="公司名称" align="center" prop="companyName" />
      <el-table-column label="公司地址" align="center" prop="companyAddress" />
      <el-table-column label="联系方式" align="center" prop="email" />
      <el-table-column
        label="成立时间"
        align="center"
        prop="foundTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.foundTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="营业执照"
        align="center"
        prop="certificate"
        width="100"
      >
        <template slot-scope="scope">
          <image-preview
            :src="scope.row.certificate"
            :width="50"
            :height="50"
          />
        </template>
      </el-table-column>
      <el-table-column label="其他材料" align="center" prop="additionalInfo">
        <template slot-scope="scope">
          <el-button-group>
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-view"
              plain
              @click="handleOpenAdditionalInfo(scope.row.additionalInfo)"
            ></el-button>
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-download"
              plain
              @click="handleDownloadAdditionalInfo(scope.row.additionalInfo)"
            ></el-button>
          </el-button-group>
        </template>
      </el-table-column>
      <el-table-column
        label="申请提交时间"
        align="center"
        prop="applyDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="companyMaster" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            icon="el-icon-edit"
            @click="handleApprove(scope.row)"
            v-hasPermi="['feature:company:edit']"
            >通过</el-button
          >
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['feature:company:remove']"
            >否决</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  listCompany,
  getCompany,
  delCompany,
  addCompany,
  updateCompany,
} from "@/api/feature/company";

export default {
  name: "Company",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 公司管理表格数据
      companyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyName: null,
        companyAddress: null,
        email: null,
        foundTime: null,
        certificate: null,
        additionalInfo: null,
        applyDate: null,
        companyMaster: null,
        status: 0,
        ticketLimit: null,
      },
      // 表单参数
      form: {},
      // 表单校验
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
  created() {
    this.getList();
  },
  methods: {
    /** 查询公司管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.status=0;
      listCompany(this.queryParams).then((response) => {
        this.companyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
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
        status: 0,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.companyId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "feature/company/export",
        {
          ...this.queryParams,
        },
        `company_${new Date().getTime()}.xlsx`
      );
    },
    handleOpenAdditionalInfo(link) {
      window.open(process.env.VUE_APP_BASE_API + link);
    },
    handleDownloadAdditionalInfo(link) {
      this.$download.resource(link);
    },
    approve(id, sendMessage=true){
      getCompany(id).then((response) => {
        this.form = response.data;
        this.form.status = 1;
        updateCompany(this.form).then((response) => {
          if(sendMessage){
            this.$modal.msgSuccess("已同意入驻");
          }
          this.getList();
        });
      });
    },
    reject(id){
      return delCompany(id);
    },
    handleApprove(data) {
      this.reset();
      const companyId = data.companyId;
      this.approve(companyId);
    },
    handleReject(row) {
      this.reset();
      const companyId = row.companyId;
      this.$modal
        .confirm('是否确认拒绝"' + companyName + '"的入驻申请？')
        .then(function () {
          return reject(companyId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("已拒绝入驻申请");
        })
        .catch(() => {});
    },
    handleApproveList() {
      this.ids.forEach((id) => {
        this.approve(id=id, sendMessage=false);
      });
      this.$modal.msgSuccess("已同意入驻");
    },
    handleRejectList() {
      this.ids.forEach((id) => {
        this.reject(id);
      });
    },
  },
};
</script>
