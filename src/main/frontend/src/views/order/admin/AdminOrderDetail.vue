<template>
  <div class="order-detail" v-if="order">
    <div class="order-header">
      <h1>{{ order.orders_name }}</h1>
      <div v-if="isCompleted" class="status-badge completed">已完成</div>
      <div v-else-if="isInProgress" class="status-badge in-progress">
        进行中
      </div>
      <div v-else class="status-badge bidding">竞标中</div>
    </div>
    <p>简介: {{ order.orders_intrudction }}</p>
    <p>发布时间: {{ order.created_time }}</p>
    <p>价格: ￥{{ order.order_price }}</p>
    <div class="buttons">
      <router-link :to="getBackRoute()" class="back-button">返回</router-link>
      <button @click="confirmDeleteOrder" class="delete-button">
        删除订单
      </button>
    </div>
    <div v-if="showDeleteConfirm" class="confirm-box">
      <div class="confirm-box-content">
        <p>确认删除该订单吗？</p>
        <div class="confirm-buttons">
          <button @click="deleteOrder" class="confirm-button">确认</button>
          <button @click="cancelDeleteConfirm" class="cancel-button">
            取消
          </button>
        </div>
      </div>
    </div>
  </div>
  <div v-else>
    <p>加载中...</p>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AdminOrderDetail",
  data() {
    return {
      order: null,
      showDeleteConfirm: false,
    };
  },
  computed: {
    isCompleted() {
      return this.order.orders_status === "completed";
    },
    isInProgress() {
      return this.order.successful_bidder !== null;
    },
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
    confirmDeleteOrder() {
      this.showDeleteConfirm = true;
    },
    cancelDeleteConfirm() {
      this.showDeleteConfirm = false;
    },
    async deleteOrder() {
      try {
        await axios.delete(`/orders/${this.order.orders_id}`);
        this.$router.push({ name: "AdminOrderHall" });
      } catch (error) {
        console.error("删除订单失败", error);
      }
    },
    getBackRoute() {
      return { name: "AdminOrderHall" };
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
  position: relative;
}

.order-header {
  display: flex;
  align-items: center;
}

.order-header h1 {
  margin: 0;
  flex-grow: 1;
}

.order-header .status-badge {
  margin-left: 20px;
}

.order-detail p {
  margin: 10px 0;
}

.buttons {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.back-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-decoration: none;
  text-align: center;
}

.back-button:hover {
  background-color: #358a66;
}

.delete-button {
  padding: 10px 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
}

.delete-button:hover {
  background-color: #c0392b;
}

.status-badge {
  width: 120px;
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 20px;
  text-align: center;
}

.status-badge.bidding {
  background-color: #8bd8fc;
  color: white;
}

.status-badge.in-progress {
  background-color: #f36f9b;
  color: white;
}

.status-badge.completed {
  background-color: #810c9e;
  color: white;
}

.confirm-box {
  background: rgba(0, 0, 0, 0.8);
  color: white;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.confirm-box-content {
  background: #2c3e50;
  padding: 40px;
  border-radius: 30px;
  text-align: center;
  width: 400px;
}

.confirm-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.confirm-button,
.cancel-button {
  padding: 15px 30px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  flex: 1;
  margin: 0 10px;
}

.confirm-button {
  background-color: #42b983;
  color: white;
}

.confirm-button:hover {
  background-color: #358a66;
}

.cancel-button {
  background-color: #e74c3c;
  color: white;
}

.cancel-button:hover {
  background-color: #c0392b;
}
</style>
