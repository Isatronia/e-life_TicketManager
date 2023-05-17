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
      <el-form-item label="投诉用户" prop="complaintUser">
        <el-input
          v-model="queryParams.complaintUser"
          placeholder="请输入投诉用户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投诉目标" prop="complaintTarget">
        <el-input
          v-model="queryParams.complaintTarget"
          placeholder="请输入投诉目标"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['feature:complaint:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['feature:complaint:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['feature:complaint:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['feature:complaint:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="complaintList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="投诉ID" align="center" prop="complaintId" />
      <el-table-column label="投诉用户" align="center" prop="complaintUser" />
      <el-table-column label="投诉目标" align="center" prop="complaintTarget" />
      <el-table-column label="部门编号" align="center" prop="deptId" v-if="checkRole(['admin'])"/>
      <el-table-column label="投诉内容" align="center" prop="content" >
        <template slot-scope="scope">
          <el-button
          size="mini"
              type="primary"
              icon="el-icon-s-marketing"
              plain
              @click="handleShowDetail(scope.row)"
          ></el-button>

        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['feature:complaint:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['feature:complaint:remove']"
            >删除</el-button
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

    <!-- 添加或修改投诉对话框 -->
    <el-drawer
      direction="rtl"
      :title="title"
      :visible.sync="open"
      size="66%"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="投诉用户" prop="complaintUser">
          <el-input v-model="form.complaintUser" placeholder="请输入投诉用户" />
        </el-form-item>
        <el-form-item label="投诉目标" prop="complaintTarget">
          <el-input
            v-model="form.complaintTarget"
            placeholder="请输入投诉目标"
          />
        </el-form-item>
        <el-form-item label="部门编号" prop="deptId" v-if="checkRole(['admin'])">
          <el-input v-model="form.deptId" placeholder="请输入部门编号" />
        </el-form-item>
        <el-form-item label="投诉内容">
          <editor v-model="form.content" :min-height="192" />
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-drawer>

    <el-drawer direction="rtl" :visible.sync="detail" size="66%" append-to-body>
      <div v-html="form.content ? form.content : ''"></div>
    </el-drawer>
  </div>
</template>

<script>
import { checkPermi, checkRole } from "@/utils/permission";
import {
  listComplaint,
  getComplaint,
  delComplaint,
  addComplaint,
  updateComplaint,
} from "@/api/feature/complaint";

export default {
  
  name: "Complaint",
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
      // 投诉表格数据
      complaintList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        complaintUser: null,
        complaintTarget: null,
        content: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 显示详情
      detail: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    checkRole,
    /** 查询投诉列表 */
    getList() {
      this.loading = true;
      listComplaint(this.queryParams).then((response) => {
        this.complaintList = response.rows;
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
        complaintId: null,
        complaintUser: null,
        complaintTarget: null,
        deptId: null,
        content: null,
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
      this.ids = selection.map((item) => item.complaintId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加投诉";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const complaintId = row.complaintId || this.ids;
      getComplaint(complaintId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改投诉";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.complaintId != null) {
            updateComplaint(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addComplaint(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const complaintIds = row.complaintId || this.ids;
      this.$modal
        .confirm('是否确认删除投诉编号为"' + complaintIds + '"的数据项？')
        .then(function () {
          return delComplaint(complaintIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "feature/complaint/export",
        {
          ...this.queryParams,
        },
        `complaint_${new Date().getTime()}.xlsx`
      );
    },
    handleShowDetail(row){
      console.log(row);
      this.reset();
      const complaintId = row.complaintId;
      getComplaint(complaintId).then((response) => {
        this.form = response.data;
        this.detail = true;
      });
    },
  },
};
</script>
