<template>
  <div class="app-container home">
    <el-row>
      <el-carousel :interval="4000" type="card" height="200px">
        <el-carousel-item
          v-for="(item, index) in carouselItemList"
          :key="index"
        >
          <img :src="item.src" />
        </el-carousel-item>
      </el-carousel>
    </el-row>
    <el-row type="flex" justify="center" :gutter="5">
      <el-col :span="13">
        <el-autocomplete
          style="width: 100%"
          class="inline-input"
          v-model="companyQueryParams.companyName"
          :fetch-suggestions="fetchCompany"
          placeholder="请输入公司名称"
          @select="handleSelectCompany"
        ></el-autocomplete>
      </el-col>
      <el-col :span="1"><el-button type="success">搜索</el-button></el-col>
    </el-row>
    <el-row>
    </el-row>
  </div>
</template>

<script>
import { listCompany, getCompany } from "@/api/feature/company";

export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.2",
      // 查询公司的参数
      companyQueryParams: {
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
      },
      // 查找的遮罩层
      companySearchLoading: false,
      companyList: [],
      totalCompany: 0,
      carouselItemList: [
        { src: "http://img.naragnova.icu/avatar/default.png" },
        { src: "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" },
        { src: "http://img.naragnova.icu/avatar/default.png" },
      ],
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    getList() {
      this.loading = true;
      if (this.companyQueryParams.companyName == null) {
        return;
      }
      listCompany(this.companyQueryParams).then((response) => {
        this.companyList = response.rows;
        this.totalCompany = response.total;
        this.loading = false;
      });
    },
    fetchCompany(queryString, cb) {
      this.companyQueryParams.pageNum = 1;
      this.getList();
      if (this.companyList.length > 0) {
        var res = this.companyList.map((item, index) => {
          return { value: item.companyName, id: item.companyId };
        });
        // console.log(res);
        cb(res);
      } else {
        cb([{ value: "暂无搜索结果" }]);
      }
    },
    showCompanyInfo() {},
    handleSelectCompany(item) {
      console.log(item);
      this.$router.push({
        path: "/ticket/company-detail/" + item.id,
        query: {},
      });
    },
  },
};
</script>

<style scoped lang="scss">
.el-input.el-input--medium,
.el-select.el-select--medium {
  width: 100%;
}
.el-carousel {
  .el-carousel__item {
    display: flex;
    justify-content: center;
  }
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  .el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
  }
}

.home {
  .search-panel-container {
    width: 100%;
    .inline-input {
      width: 60%;
    }
  }
  // 原生代码
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

