<template>
  <div class="message-panel">
    <el-row :gutter="3" class="message-info">
      <el-col :span="1">
        <el-image :src="message.author.avatar" class="img-circle img-sm">
        </el-image>
      </el-col>
      <el-col :span="18">
        <el-row :gutter="4">
          <el-col :span="18" class="ticket-message-header">
            {{ message.author.nickName }}
            {{ message.createTime }}
          </el-col>
          <el-col v-if="deleteAble" :span="2">
            <el-button
              type="danger"
              @click="delThisMessage"
              icon="el-icon-delete"
              size="mini"
            >
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <div v-html="compiledMessage" class="markdown"></div>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { marked } from "marked";
import { getUserBasicInfo } from "@/api/system/user";
import { safeDelMessage } from "@/api/monitor/message";

// JUST FOR TEST
import store from "@/store";

export default {
  name: "Message",
  data() {
    return {
      currentUser: this.$store.state.user.userId,
      deleteAble: false,
    };
  },
  props: {
    message: {
      type: Object,
      default: () => {
        return {
          content: "未知内容",
          time: "2019-04-05",
        };
      },
    },
    updateRequest: undefined,
  },
  computed: {
    compiledMessage() {
      if (!this.message || !this.message.content) {
        return "";
      }
      return this.message.content ? marked(this.message.content) : "";
    },
  },
  methods: {
    fetchAuthor() {
      // 用户数据是用id表示的， 需要从服务器拉取
      if (typeof this.message.author == "number") {
        getUserBasicInfo(this.message.author).then((response) => {
          this.message.author = response.data;
          if (
            !(
              this.message.author.avatar.startsWith("http://") ||
              this.message.author.avatar.startsWith("https://")
            )
          ) {
            this.message.author.avatar =
              process.env.VUE_APP_BASE_API + this.message.author.avatar;
          }
        });
      }
      if (typeof this.message.author != "object") {
        this.message.author = {};
      }
      // 填入默认用户名
      if (!this.message.author.nickName) {
        this.message.author.nickname = "404 Not Found";
      }
      // 校验头像地址
      // 如果是空的， 填入默认值
      if (
        this.message.author.avatar == null ||
        this.message.author.avatar == ""
      ) {
        // 使用默认头像地址
        this.message.author.avatar =
          "http://img.naragnova.icu/avatar/default.png";
      }
    },
    delThisMessage() {
      this.$modal
        .confirm("是否确认删除该条消息?")
        .then(
          () => {
            return safeDelMessage(this.message.messageId);
          },
          () => {
            console.log("rejected");
          }
        )
        .then(() => {
          this.$emit("refresh");
          this.$modal.msgSuccess("删除成功");
        });
    },
    checkDeleteAble() {
      if (null == this.message || null == this.message.author) {
        return;
      }
      if (null == this.currentUser) {
        this.currentUser = this.$store.state.user.userId;
      }
      if (0 == this.message.messageType) {
        this.deleteAble = this.currentUser == this.message.author.userId;
        return;
      }
      if (1 == this.message.messageType) {
        this.deleteAble = this.currentUser == this.message.messageTicket;
        return;
      }
      this.deleteAble = false;
      return;
    },
  },
  watch: {
    message(oldMessage, newMessage) {
      this.checkDeleteAble();
    },
  },
  created() {
    if (!this.message) {
      this.message = {};
    }
    if (!this.message.content) {
      this.message.content = "Error";
    }
    if (!this.message.createTime) {
      this.message.createTime = "1999-12-31";
    }
    if (!this.message.author) {
      this.message.author = {};
    } else {
      this.fetchAuthor();
    }
    this.checkDeleteAble();
  },
  updated() {
    this.fetchAuthor();
    this.checkDeleteAble();
  },
};
</script>

<style lang="scss">
.message-panel {
  overflow: hidden;
  width: 100%;
  margin-top: 1rem;
}
.message-info {
  width: 100%;
  height: auto;
}
.ticket-message-header {
  font-size: 1.2rem;
  font-weight: bold;
  line-height: 1.3;
}
</style>