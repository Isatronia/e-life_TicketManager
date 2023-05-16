<template>
  <div>
    <el-card :body-style="{ padding: '0px' }">
      <img :src="item.avatar" class="image" />
      <div style="padding: 14px">
        <span>{{ item.title }}</span>
        <div class="bottom clearfix">
          <div>{{ item.description }}</div>
          <time class="time">{{ item.createTime }}</time>
          <el-button type="text" class="button">操作按钮</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>
<script>
import { getShopItem } from "@/api/feature/shopItem";

export default {
  name: "ShopItem",
  data() {
    return {
      item: {
        title: "test",
        avatar: "https://img.naragnova.icu/avatar/default.png",
        
      },
    };
  },
  props: {
    itemId: {
      type: Number,
      default: () => {
        return undefined;
      },
    },
  },
  methods: {
    getShopItemDetail() {
      if (this.itemId == undefined) {
        return;
      }
      getShopItem(this.itemId).then((response) => {
        this.item = response.data;
      });
    },
  },
  created() {
    this.getShopItem();
    return;
  },
  mounted() {},
};
</script>