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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['feature:company:add']"
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
          v-hasPermi="['feature:company:edit']"
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
          v-hasPermi="['feature:company:remove']"
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
          v-hasPermi="['feature:company:export']"
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
      :data="companyList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="公司ID" align="center" prop="companyId" />
      <el-table-column label="部门ID" align="center" prop="deptId" />
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
      <el-table-column label="服务单限制" align="center" prop="ticketLimit"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.company_status"
            :value="scope.row.status"
          />
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
            v-hasPermi="['feature:company:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['feature:company:remove']"
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

    <!-- 添加或修改公司管理对话框 -->
    <el-drawer
      :visible.sync="open"
      size="66%"
      append-to-body
      direction="rtl"
      class="form-drawer"
    >
      <div slot="title">
        {{ title }}
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="12%">
        <el-form-item label="公司名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入公司名称" />
        </el-form-item>
        <el-form-item label="公司地址" prop="companyAddress">
          <el-input
            v-model="form.companyAddress"
            placeholder="请输入公司地址"
          />
        </el-form-item>
        <el-form-item label="联系方式" prop="email">
          <el-input v-model="form.email" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="成立时间" prop="foundTime">
          <el-date-picker
            clearable
            v-model="form.foundTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择成立时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="营业执照">
          <image-upload v-model="form.certificate" />
        </el-form-item>
        <el-form-item label="其他材料">
          <file-upload v-model="form.additionalInfo" />
        </el-form-item>
        <el-form-item label="申请提交时间" prop="applyDate">
          <el-date-picker
            clearable
            v-model="form.applyDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择申请提交时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="负责人" prop="companyMaster">
          <el-input v-model="form.companyMaster" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="服务单数量限制" prop="ticketLimit">
          <el-input
            v-model="form.ticketLimit"
            placeholder="请输入公司服务单数量限制"
          />
        </el-form-item>
        <el-form-item label="公司当前状态" prop="status">
          <el-input v-model="form.status" placeholder="请输入公司状态" />
        </el-form-item>
      </el-form>
      <el-row>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-row>
    </el-drawer>
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
  dicts: ["company_status"],
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
        state: null,
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
        status: null,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加公司管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const companyId = row.companyId || this.ids;
      getCompany(companyId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改公司管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.companyId != null) {
            updateCompany(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCompany(this.form).then((response) => {
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
      const companyIds = row.companyId || this.ids;
      this.$modal
        .confirm('是否确认删除公司管理编号为"' + companyIds + '"的数据项？')
        .then(function () {
          return delCompany(companyIds);
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
  },
};
</script>

<style lang="scss">
.form-drawer {
  overflow: auto;
}
</style>
