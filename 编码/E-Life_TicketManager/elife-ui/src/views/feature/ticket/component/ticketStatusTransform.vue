<template>
  <div>
    <el-button
      v-if="ticket && ticket.status == 1"
      @click="handleCloseTicket"
      type="primary"
      >关闭服务单</el-button
    >
    <el-button v-if="ticket && ticket.status == 2" @click="handleReopenTicket" type="success"
      >重开服务单</el-button
    >
    <el-button v-if="ticket && ticket.status == 2" @click="handleDeleteTicket" type="error"
      >删除服务单</el-button
    >
  </div>
</template>
<script>
import {
  listTicket,
  getTicket,
  delTicket,
  addTicket,
  updateTicket,
} from "@/api/feature/ticket";
export default {
  name: "TicketStatusTransform",
  props: {
    ticketId: undefined,
  },
  data() {
    return {
      ticket: undefined,
    };
  },
  methods: {
    fetchTicket() {
      if (undefined == this.ticketId) {
        return;
      }
      getTicket(this.ticketId).then((response) => {
        this.ticket = response.data;
      });
    },
    ticketStatusTransform(status) {
      getTicket(this.ticketId)
        .then((response) => {
          this.ticket = response.data;
        })
        .then(() => {
          this.ticket.status = status;
          updateTicket(this.ticket);
        });
    },
    handleCloseTicket() {
      if (1 != this.ticket.status) {
        return;
      }
      this.$modal.confirm("即将关闭服务单").then(
        getTicket(this.ticketId)
          .then((response) => {
            this.ticket = response.data;
          })
          .then(() => {
            this.ticket.status = 2;
            updateTicket(this.ticket).then((response) => {
              this.$modal.msgSuccess("修改成功");
            });
          })
      );
    },
    handleReopenTicket() {},
    handleDeleteTicket() {},
  },
  watch:{
    ticketId(oldId, newId){
      this.fetchTicket();
    }
  },
};
</script>