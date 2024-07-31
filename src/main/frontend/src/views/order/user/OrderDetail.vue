<template>
  <div class="order-detail" v-if="order">
    <div class="order-header">
      <h1>{{ order.orders_name }}</h1>
      <div v-if="isCompleted" class="completed-badge">已完成</div>
      <div v-else-if="isSuccessfulBidder" class="success-badge">接单成功</div>
    </div>
    <p>简介: {{ order.orders_introduction }}</p>
    <p>发布时间: {{ formatDatetime(order.created_time) }}</p>
    <p>价格: ￥{{ order.order_price }}</p>
    <div class="buttons">
      <router-link :to="getBackRoute()" class="back-button">返回</router-link>
      <template v-if="isSuccessfulBidder"> </template>
      <template v-else>
        <button
          v-if="!hasTakenOrder"
          @click="confirmOrder"
          class="take-order-button"
        >
          接单
        </button>
        <button
          v-if="hasTakenOrder"
          @click="confirmCancelOrder"
          class="cancel-order-button"
        >
          取消接单
        </button>
      </template>
    </div>
    <div v-if="showConfirm" class="confirm-box">
      <div class="confirm-box-content">
        <p>确定提交简历并申请接单？</p>
        <div class="confirm-buttons">
          <button @click="takeOrder" class="confirm-button">是</button>
          <button @click="cancelConfirm" class="cancel-button">否</button>
        </div>
      </div>
    </div>
    <div v-if="showCancelConfirm" class="confirm-box">
      <div class="confirm-box-content">
        <p>确定取消接单？</p>
        <div class="confirm-buttons">
          <button @click="cancelOrder" class="confirm-button">是</button>
          <button @click="cancelCancelConfirm" class="cancel-button">否</button>
        </div>
      </div>
    </div>
    <div v-if="showSuccessMessage" class="success-message">
      <div class="success-message-box">
        <p>申请成功</p>
        <button @click="closeSuccessMessage" class="confirm-button">
          确定
        </button>
      </div>
    </div>
    <div v-if="showCancelSuccessMessage" class="success-message">
      <div class="success-message-box">
        <p>取消成功</p>
        <button @click="closeCancelSuccessMessage" class="confirm-button">
          确定
        </button>
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
  name: "OrderDetail",
  data() {
    return {
      order: null,
      showConfirm: false,
      showCancelConfirm: false,
      showSuccessMessage: false,
      showCancelSuccessMessage: false,
      hasTakenOrder: false,
      isSuccessfulBidder: false,
      isCompleted: false,
    };
  },
  methods: {
    formatDatetime(datetime) {
      // 使用replace方法将'T'替换为空格
      return datetime.replace('T', ' ');
    },
    async fetchOrder() {
      const orderId = this.$route.params.id;
      try {
        const response = await axios.get(`/orders/${orderId}`);
        this.order = response.data;
        const user = JSON.parse(localStorage.getItem("user"));
        if (user && this.order.successful_bidder === user.nickname) {
          this.isSuccessfulBidder = true;
        }
        if (user && this.order.orders_status === "completed") {
          this.isCompleted = true;
        } else if (user && this.order.bidders.includes(user.nickname)) {
          this.hasTakenOrder = true;
        }
      } catch (error) {
        console.error("获取订单详情失败", error);
      }
    },
    confirmOrder() {
      this.showConfirm = true;
    },
    cancelConfirm() {
      this.showConfirm = false;
    },
    confirmCancelOrder() {
      this.showCancelConfirm = true;
    },
    cancelCancelConfirm() {
      this.showCancelConfirm = false;
    },
    async takeOrder() {
      const user = JSON.parse(localStorage.getItem("user"));
      if (!user) {
        console.error("用户未登录");
        return;
      }
      if (!this.order.bidders.includes(user.nickname)) {
        this.order.bidders.push(user.nickname);
      }
      try {
        await axios.put(`/orders/${this.order.orders_id}`, this.order);
        this.showConfirm = false;
        this.showSuccessMessage = true;
        this.hasTakenOrder = true;
      } catch (error) {
        console.error("申请接单失败", error);
      }
    },
    async cancelOrder() {
      const user = JSON.parse(localStorage.getItem("user"));
      if (!user) {
        console.error("用户未登录");
        return;
      }
      const index = this.order.bidders.indexOf(user.nickname);
      if (index > -1) {
        this.order.bidders.splice(index, 1);
      }
      try {
        await axios.put(`/orders/${this.order.orders_id}`, this.order);
        this.showCancelConfirm = false;
        this.showCancelSuccessMessage = true;
        this.hasTakenOrder = false;
      } catch (error) {
        console.error("取消接单失败", error);
      }
    },
    closeSuccessMessage() {
      this.showSuccessMessage = false;
    },
    closeCancelSuccessMessage() {
      this.showCancelSuccessMessage = false;
    },
    getBackRoute() {
      if (this.$route.query.from === "my-orders") {
        return this.isCompleted
          ? { name: "MyOrders", query: { tab: "completed" } }
          : this.isSuccessfulBidder
          ? { name: "MyOrders", query: { tab: "success" } }
          : { name: "MyOrders", query: { tab: "pending" } };
      }
      return { name: "OrderHall" };
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

.order-header .success-badge,
.order-header .completed-badge {
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

.take-order-button {
  padding: 10px 20px;
  background-color: #f0ad4e;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
}

.take-order-button:hover {
  background-color: #ec971f;
}

.cancel-order-button {
  padding: 10px 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
}

.cancel-order-button:hover {
  background-color: #c0392b;
}

.success-badge {
  width: 120px;
  padding: 10px 20px;
  background-color: #f36f9b;
  color: white;
  border-radius: 20px;
  font-size: 20px;
  text-align: center;
}

.completed-badge {
  width: 120px;
  padding: 10px 20px;
  background-color: #810c9e;
  color: white;
  border-radius: 20px;
  font-size: 20px;
  text-align: center;
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

.success-message {
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

.success-message-box {
  background: #2c3e50;
  padding: 40px;
  border-radius: 30px;
  text-align: center;
  width: 400px;
}

.success-message-box .confirm-button {
  margin-top: 20px;
}
</style>
