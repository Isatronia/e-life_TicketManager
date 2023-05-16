<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="父单ID" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父单ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="祖级列表" prop="ancestors">
        <el-input
          v-model="queryParams.ancestors"
          placeholder="请输入祖级列表"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务单标题" prop="ticketTitle">
        <el-input
          v-model="queryParams.ticketTitle"
          placeholder="请输入服务单标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权限信息" prop="authorizationInfo">
        <el-input
          v-model="queryParams.authorizationInfo"
          placeholder="请输入权限信息"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评价信息" prop="commitInfo">
        <el-input
          v-model="queryParams.commitInfo"
          placeholder="请输入评价信息"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['feature:ticket:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['feature:ticket:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['feature:ticket:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['feature:ticket:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ticketList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="ticketId" />
      <el-table-column label="父单ID" align="center" prop="parentId" />
      <el-table-column label="祖级列表" align="center" prop="ancestors" />
      <el-table-column label="服务单标题" align="center" prop="ticketTitle" />
      <el-table-column label="服务单类型" align="center" prop="ticketType" />
      <el-table-column label="服务单状态" align="center" prop="status" />
      <el-table-column label="权限信息" align="center" prop="authorizationInfo" />
      <el-table-column label="评价信息" align="center" prop="commitInfo" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['feature:ticket:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['feature:ticket:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改服务单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父单ID" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父单ID" />
        </el-form-item>
        <el-form-item label="服务单标题" prop="ticketTitle">
          <el-input v-model="form.ticketTitle" placeholder="请输入服务单标题" />
        </el-form-item>
        <el-form-item label="权限信息" prop="authorizationInfo">
          <el-input v-model="form.authorizationInfo" placeholder="请输入权限信息" />
        </el-form-item>
        <el-form-item label="评价信息" prop="commitInfo">
          <el-input v-model="form.commitInfo" placeholder="请输入评价信息" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTicket, getTicket, delTicket, addTicket, updateTicket } from "@/api/feature/ticket";

export default {
  name: "Ticket",
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
      // 服务单表格数据
      ticketList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentId: null,
        ancestors: null,
        ticketTitle: null,
        ticketType: null,
        status: null,
        authorizationInfo: null,
        commitInfo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ticketTitle: [
          { required: true, message: "服务单标题不能为空", trigger: "blur" }
        ],
        ticketType: [
          { required: true, message: "服务单类型不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "服务单状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询服务单列表 */
    getList() {
      this.loading = true;
      listTicket(this.queryParams).then(response => {
        this.ticketList = response.rows;
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
        ticketId: null,
        parentId: null,
        ancestors: null,
        ticketTitle: null,
        ticketType: null,
        status: null,
        authorizationInfo: null,
        commitInfo: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.ticketId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加服务单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const ticketId = row.ticketId || this.ids
      getTicket(ticketId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改服务单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.ticketId != null) {
            updateTicket(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTicket(this.form).then(response => {
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
      const ticketIds = row.ticketId || this.ids;
      this.$modal.confirm('是否确认删除服务单编号为"' + ticketIds + '"的数据项？').then(function() {
        return delTicket(ticketIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('feature/ticket/export', {
        ...this.queryParams
      }, `ticket_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
