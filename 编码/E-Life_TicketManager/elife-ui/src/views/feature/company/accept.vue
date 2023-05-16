<template>
  <div>
    <div>这是一个中转页面，如果没有正常跳转，请</div>
    <el-button @click="jump"> 点击这里！ </el-button>
  </div>
</template>

<script>
import { acceptSubscribe } from "@/api/feature/subscribe";

export default {
  name: "AcceptOffer",
  data() {
    return {
      companyId: undefined,
    };
  },
  methods: {
    accept() {
      console.log("Accepted!!!!");
      return;
      acceptSubscribe(this.companyId).then(
        (response) => {
        //   this.jump();
        },
        (reject) => {
        //   this.jump();
        }
      );
    },
    jump() {
      this.$route.push("/ticket/company-detail/" + this.companyId);
    },
  },
  created() {
    this.companyId = this.$route.params.companyId;
    this.accept();
  },
};
</script>