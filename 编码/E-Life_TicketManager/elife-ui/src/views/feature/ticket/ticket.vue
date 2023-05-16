<template>
  <div class="ticket-container">
    <el-container class="sns-container">
      <el-aside width="30vh">
        <el-tree
          :data="ticketList"
          :props="treeConfig"
          @node-click="handleNodeClick"
        >
          <div class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ node.label }}</span>
            <span class="push-right">
              <i
                class="el-icon-setting"
                @click="handleEditTicket(data.ticketId)"
              >
              </i>
            </span>
          </div>
        </el-tree>
      </el-aside>
      <el-container>
        <el-header class="ticket-header-container">
          <div class="ticket-header">
            {{ formatTitle(currentTicket) }}
          </div></el-header
        >
        <div class="sns-panel">
          <el-main ref="ticketPanel">
            <message-container
              :ticketId="ticketId"
              :messageType="0"
              :timestamp="ticketMessageTimeStamp"
            />
          </el-main>
        </div>
        <el-footer>
          <el-row :gutter="5">
            <el-col :span="20">
              <el-input
                v-model="messageContent"
                clearable
                autofocus
                placeholder="输入消息..."
                :disabled="currentTicket == undefined"
                maxlength="200"
                @keydown.enter.native="handleSendMessage"
              ></el-input>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="handleSendMessage()"
                >发送</el-button
              >
            </el-col>
          </el-row>
        </el-footer>
      </el-container>
    </el-container>

    <el-drawer label="rtl" :visible.sync="editTicket">
      <ticket-edit-form :ticket="currentTicket" />
    </el-drawer>
  </div>
</template>

<script>
import MessageContainer from "./component/messageContainer.vue";
import { sendMessage } from "@/api/monitor/message";
import { listTicket } from "@/api/feature/ticket";
import ticketEditForm from "./component/ticketEditForm.vue";
export default {
  components: {
    "message-container": MessageContainer,
    ticketEditForm,
  },
  data() {
    return {
      ticketId: undefined,
      currentTicket: undefined,
      editTicket: false,
      ticketList: [],
      messageContent: "",
      ticketMessageTimeStamp: 0,
      treeConfig: {
        childern: "",
        label: "ticketTitle",
      },
    };
  },
  methods: {
    handleSendMessage() {
      let user = this.$store.state.userId;
      let message = {
        messageTicket: this.ticketId,
        author: user,
        quote: undefined,
        mention: undefined,
        status: undefined,
        content: this.messageContent,
      };
      sendMessage(message).then((response) => {
        this.messageContent = "";
        this.ticketMessageTimeStamp++;
      });
    },
    fetchTicketList() {
      listTicket({}).then((response) => {
        console.log(response);
        this.ticketList = response.rows;
      });
    },
    handleNodeClick(data) {
      this.currentTicket = data;
      this.ticketId = data.ticketId;
    },
    handleEditTicket(id) {
      console.log(id);
      this.editTicket = true;
    },
    formatTitle(ticket) {
      console.log("logging current ticket...");
      console.log(ticket);
      if (ticket) {
        return "Ticket " + ticket.ticketId + "\t" + ticket.ticketTitle;
      } else {
        return "从右侧选择一个服务单以开始...";
      }
    },
  },
  created() {
    this.ticketId = this.$route.params.ticketId;
    this.fetchTicketList();
  },
};
</script>

<style lang="scss">
.ticket-header-container {
  display: flex;
}
.custom-tree-node {
  display: flex;
  justify-content: space-between;
}
.ticket-header {
  align-self: center;
  font-size: 1.5rem;
  font-weight: bold;
  line-height: 1.3;
}
</style>