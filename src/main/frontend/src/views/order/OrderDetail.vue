<template>
  <div class="order-detail" v-if="order">
    <h1>{{ order.orders_name }}</h1>
    <p>简介: {{ order.orders_intrudction }}</p>
    <p>发布时间: {{ order.created_time }}</p>
    <p>价格: ￥{{ order.order_price }}</p>
    <router-link :to="{ name: 'OrderHall' }" class="action-button"
      >返回大厅</router-link
    >
  </div>
  <div v-else>
    <p>加载中...</p>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "OrderDetail",
  data() {
    return {
      order: null,
    };
  },
  methods: {
    async fetchOrder() {
      const orderId = this.$route.params.id;
      try {
        const response = await axios.get(`/orders/${orderId}`);
        this.order = response.data;
      } catch (error) {
        console.error("获取订单详情失败", error);
      }
    },
    setMenu() {
      this.$store.dispatch("setActiveSubMenu", "order");
    },
  },
  created() {
    this.fetchOrder();
    this.setMenu();
  },
};
</script>

<style scoped>
.order-detail {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.order-detail h1 {
  margin-top: 0;
}

.order-detail p {
  margin: 10px 0;
}

.action-button {
  padding: 10px 20px;
  margin-top: 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  display: inline-block;
  text-align: center;
  text-decoration: none;
}

.action-button:hover {
  background-color: #0056b3;
}
</style>
