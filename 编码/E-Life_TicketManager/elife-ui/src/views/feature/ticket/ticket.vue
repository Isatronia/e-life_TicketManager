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
            <el-row :gutter="0">
              <el-col :span="20">
                <span class="node-ticket-title">{{ node.label }}</span>
              </el-col>
              <el-col :span="1">
                <span class="node-ticket-config">
                  <i class="el-icon-setting" @click="handleEditTicket(data)">
                  </i>
                </span>
              </el-col>
            </el-row>
          </div>
        </el-tree>
      </el-aside>
      <el-container>
        <el-header class="ticket-header-container">
          <div class="ticket-header">
            {{ formatTitle }}
          </div></el-header
        >
        <div class="sns-panel">
          <el-main ref="ticketPanel">
            <message-container
              :ticketId="curTicketId"
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
      <ticket-status-transform :ticketId="curTicketId"></ticket-status-transform>
      <ticket-edit-form :ticket="curTicketId" @refresh="fetchTicketList" />
    </el-drawer>
  </div>
</template>

<script>
import MessageContainer from "./component/messageContainer.vue";
import { sendMessage } from "@/api/monitor/message";
import { listTicket } from "@/api/feature/ticket";
import ticketEditForm from "./component/ticketEditForm.vue";
import ticketStatusTransform from "./component/ticketStatusTransform.vue";
export default {
  components: {
    "message-container": MessageContainer,
    ticketEditForm,
    ticketStatusTransform,
  },
  data() {
    return {
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
        messageTicket: this.currentTicket.ticketId,
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
    updateCurrentTicketInf() {
      this.currentTicket = this.ticketList.find((item) => {
        return item.ticketId == this.curTicketId;
      });
    },
    refresh() {
      this.fetchTicketList();
    },
    fetchTicketList() {
      listTicket({}).then((response) => {
        this.ticketList = response.rows;
        this.updateCurrentTicketInf();
      });
    },
    handleNodeClick(data) {
      this.currentTicket = data;
    },
    handleEditTicket(data) {
      this.currentTicket = data;
      this.editTicket = true;
    },
  },
  computed: {
    curTicketId() {
      if (this.currentTicket) {
        return this.currentTicket.ticketId;
      } else {
        return undefined;
      }
    },
    formatTitle() {
      if (this.currentTicket) {
        return (
          "Ticket " +
          this.currentTicket.ticketId +
          "\t" +
          this.currentTicket.ticketTitle
        );
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
  width: 100%;
  .node-ticket-title{
    justify-content: left;

  }
  .node-ticket-config{
    justify-content: right;
  }
}
.ticket-header {
  align-self: center;
  font-size: 1.5rem;
  font-weight: bold;
  line-height: 1.3;
}
</style>