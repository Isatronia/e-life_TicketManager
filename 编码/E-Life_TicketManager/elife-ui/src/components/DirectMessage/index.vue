<template>
    <div>
      <i class="el-icon-s-comment" @click="goto" ></i>
    </div>
  </template>
  
  <script>
  export default {
    name: 'DirectMessage',
    data() {
      return {
        url: ''
      }
    },
    methods: {
      goto() {
        this.$router.push('/user/direct-message')
      }
    }
  }
  </script>