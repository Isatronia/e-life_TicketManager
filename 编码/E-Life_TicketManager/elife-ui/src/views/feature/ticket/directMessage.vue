<template>
  <div class="ticket-container">
    <el-container class="sns-container">
      <el-container>
        <el-header class="ticket-header-container"> 私信 </el-header>
        <div class="sns-panel">
          <el-main ref="ticketPanel">
            <message-container
              :ticketId="-1"
              :messageType="1"
              :timestamp="ticketMessageTimeStamp"
            />
          </el-main>
        </div>
      </el-container>
    </el-container>
  </div>
</template>
  
  <script>
import ImportTable from "../../tool/gen/importTable.vue";
import MessageContainer from "./component/messageContainer.vue";
export default {
  components: {
    "message-container": MessageContainer,
    ImportTable,
  },
  data() {
    return {
      ticketId: this.$store.state.user.userId,
      ticketMessageTimeStamp: 0,
      treeConfig: {
        childern: "",
        label: "ticketTitle",
      },
    };
  },
  methods: {},
  created() {
    console.log(this.$store.state.user.userId);
  },
};
</script>
  
  <style lang="scss">
.ticket-header-container {
  display: flex;
}
.ticket-header {
  align-self: center;
  font-size: 1.5rem;
  font-weight: bold;
  line-height: 1.3;
}
</style>