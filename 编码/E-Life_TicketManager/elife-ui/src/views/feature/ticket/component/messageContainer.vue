<template>
  <div class="ticket-message-container">
    <div v-if="this.messageList">
      <div v-for="(msg, index) in messageList" :key="index">
        <message :message="msg" :updateRequest="refresh"></message>
      </div>
    </div>
    <div v-else>
      <el-skeleton :rows="6" />
    </div>
  </div>
</template>

<script>
import store from "@/store";
import Message from "./message.vue";
import { getMessageByTicketId, getDirectMessage } from "@/api/monitor/message";
import { getMessage } from "../../../../api/monitor/message";

export default {
  name: "MessageContainer",
  components: {
    message: Message,
  },
  props: {
    ticketId: undefined,
    messageType: undefined,
    timestamp: 0,
  },
  data() {
    return {
      messageList: undefined,
      msgts: 0,
      fetchInterval: undefined,
      fetchMethod: undefined,
      toLastOnce() {
        if (this.toLast) {
          console.log("tring scroll to bottom");
          this.scrollToBottom();
          this.toLast = undefined;
        }
      },
      toLast: 1,
      refresh: false,
    };
  },
  methods: {
    scrollToTop() {
      this.$refs.ticketPanel.scrollTop = 0;
    },
    scrollToBottom() {
      this.$nextTick(() => {
        // let panel = this.$el.querySelector(".message-container");
        let panel = document.querySelector(".ticket-message-container");
        panel.scrollTop = panel.scrollHeight;
      });
    },
    sortMessageList() {
      if (!this.messageList) {
        return;
      }
      this.messageList.sort((a, b) => {
        return a.messageId - b.messageId;
      });
    },
    // 合并消息
    mergeMessageList(message) {
      // 筛选不在已有列表中的数据，加入
      if (
        !this.messageList.find((msg) => {
          return msg.messageId == message.messageId;
        })
      ) {
        this.messageList.push(message);
      }
    },
    // 拉取服务单信息
    fetchTicketMessage() {
      console.log("Fetching Message in ticket ...");
      getMessageByTicketId(this.ticketId).then((response) => {
        response.rows.forEach((item) => {
          this.mergeMessageList(item);
        });
        this.sortMessageList();
        this.toLastOnce();
      });
    },
    // 拉取私信通知
    fetchDirectMessage() {
      console.log("fetching directmessage...");
      getDirectMessage().then((response) => {
        response.rows.forEach((item) => {
          this.mergeMessageList(item);
        });
        this.sortMessageList();
        this.toLastOnce();
      });
    },
    // 从服务器拉取信息列表
    fetchMessage() {
      // 判断是否第一次初始化，没设置就不拉取
      if (undefined == this.ticketId) return;
      // 初始化消息列表
      if (!this.messageList) this.messageList = [];
      // 拉取消息列表
      if (this.messageType == 0) {
        this.fetchTicketMessage();
      }
      if (this.messageType == 1) {
        this.fetchDirectMessage();
      }
    },
  },
  computed: {},
  watch: {
    ticketId(newId, oldId) {
      this.messageList = [];
      this.toLast = 1;
      this.fetchTicketMessage(newId);
    },
    timestamp(oldStamp, newStamp) {
      console.log("Sychorizing Message List...");
      this.fetchMessage();
      this.msgts = newStamp;
    },
    refresh(o, n){
      this.fetchMessage();
      this.refresh=false;
    }
  },
  created() {
    // 根据待拉取的消息类型设置拉取方法
    this.fetchInterval = setInterval(() => {
      setTimeout(this.fetchMessage, 0);
    }, 10000);
    this.fetchMessage();
  },
  mounted() {
    this.$nextTick(() => {
      this.scrollToBottom();
    });
  },
  updated() {},
  beforeDestroy() {
    if (this.fetchInterval) {
      clearInterval(this.fetchInterval);
    }
  },
};
</script>

<style lang="scss">
.ticket-message-container {
  $comp-height: 74vh;
  overflow-y: scroll;
  overflow-x: hidden;
  max-height: $comp-height;
  min-height: $comp-height;
}
</style>