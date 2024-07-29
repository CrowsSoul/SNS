<template>
  <div class="my-tasks">
    <h1>我的发包</h1>
    <div class="tabs">
      <button :class="{ active: activeTab === 'new' }" @click="setTab('new')">
        发布新订单
      </button>
      <button
        :class="{ active: activeTab === 'pending' }"
        @click="setTab('pending')"
      >
        审核中
      </button>
      <button
        :class="{ active: activeTab === 'bidding' }"
        @click="setTab('bidding')"
      >
        竞标中
      </button>
      <button
        :class="{ active: activeTab === 'inProgress' }"
        @click="setTab('inProgress')"
      >
        进行中
      </button>
      <button
        :class="{ active: activeTab === 'completed' }"
        @click="setTab('completed')"
      >
        已完成
      </button>
    </div>
    <div v-if="activeTab === 'new'" class="new-task">
      <h2>发布新订单</h2>
      <form @submit.prevent="submitNewOrder">
        <div class="form-group">
          <label for="order-name">订单名:</label>
          <input
            type="text"
            id="order-name"
            v-model="newOrder.orders_name"
            required
          />
        </div>
        <div class="form-group">
          <label for="order-price">价格:</label>
          <input
            type="number"
            id="order-price"
            v-model="newOrder.order_price"
            required
          />
        </div>
        <div class="form-group">
          <label for="order-introduction">简介:</label>
          <textarea
            id="order-introduction"
            v-model="newOrder.orders_introduction"
            required
          ></textarea>
        </div>
        <button type="submit" class="submit-button">发布订单</button>
      </form>
    </div>
    <ul v-else-if="activeTab === 'draft'" class="order-list">
      <li
        v-for="order in paginatedDraftOrders"
        :key="order.orders_id"
        class="order-item"
      >
        <div class="order-info">
          <div class="order-name">{{ order.orders_name }}</div>
          <div class="order-price">￥{{ order.order_price }}</div>
        </div>
        <router-link
          :to="{
            name: 'MyOrderDetail',
            params: { id: order.orders_id },
            query: { from: 'my-tasks', status: 'draft' },
          }"
          class="view-detail-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <ul v-else-if="activeTab === 'pending'" class="order-list">
      <li
        v-for="order in paginatedPendingOrders"
        :key="order.orders_id"
        class="order-item"
      >
        <div class="order-info">
          <div class="order-name">{{ order.orders_name }}</div>
          <div class="order-price">￥{{ order.order_price }}</div>
        </div>
        <router-link
          :to="{
            name: 'MyOrderDetail',
            params: { id: order.orders_id },
            query: { from: 'my-tasks', status: 'pending' },
          }"
          class="view-detail-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <ul v-else-if="activeTab === 'bidding'" class="order-list">
      <li
        v-for="order in paginatedBiddingOrders"
        :key="order.orders_id"
        class="order-item"
      >
        <div class="order-info">
          <div class="order-name">{{ order.orders_name }}</div>
          <div class="order-price">￥{{ order.order_price }}</div>
        </div>
        <router-link
          :to="{
            name: 'MyOrderDetail',
            params: { id: order.orders_id },
            query: { from: 'my-tasks', status: 'bidding' },
          }"
          class="view-detail-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <ul v-else-if="activeTab === 'inProgress'" class="order-list">
      <li
        v-for="order in paginatedInProgressOrders"
        :key="order.orders_id"
        class="order-item"
      >
        <div class="order-info">
          <div class="order-name">{{ order.orders_name }}</div>
          <div class="order-price">￥{{ order.order_price }}</div>
        </div>
        <router-link
          :to="{
            name: 'MyOrderDetail',
            params: { id: order.orders_id },
            query: { from: 'my-tasks', status: 'inProgress' },
          }"
          class="view-detail-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <ul v-else-if="activeTab === 'completed'" class="order-list">
      <li
        v-for="order in paginatedCompletedOrders"
        :key="order.orders_id"
        class="order-item"
      >
        <div class="order-info">
          <div class="order-name">{{ order.orders_name }}</div>
          <div class="order-price">￥{{ order.order_price }}</div>
        </div>
        <router-link
          :to="{
            name: 'MyOrderDetail',
            params: { id: order.orders_id },
            query: { from: 'my-tasks', status: 'completed' },
          }"
          class="view-detail-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <div v-else>
      <p>没有符合条件的订单...</p>
    </div>
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">
        下一页
      </button>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "MyTasks",
  data() {
    return {
      orders: [],
      activeTab: "new",
      currentPage: 1,
      ordersPerPage: 5,
      loading: true,
      newOrder: {
        orders_name: "",
        order_price: 0,
        orders_introduction: "",
      },
    };
  },
  computed: {
    user() {
      return JSON.parse(localStorage.getItem("user"));
    },
    draftOrders() {
      return this.orders.filter(
        (order) =>
          order.user === this.user.nickname && order.orders_status === "draft"
      );
    },
    pendingOrders() {
      return this.orders.filter(
        (order) =>
          order.user === this.user.nickname && order.orders_status === "pending"
      );
    },
    biddingOrders() {
      return this.orders.filter(
        (order) =>
          order.user === this.user.nickname &&
          !order.successful_bidder &&
          order.orders_status === "processing"
      );
    },
    inProgressOrders() {
      return this.orders.filter(
        (order) =>
          order.user === this.user.nickname &&
          order.successful_bidder &&
          order.orders_status === "processing"
      );
    },
    completedOrders() {
      return this.orders.filter(
        (order) =>
          order.user === this.user.nickname &&
          order.orders_status === "completed"
      );
    },
    paginatedDraftOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.draftOrders.slice(start, end);
    },
    paginatedPendingOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.pendingOrders.slice(start, end);
    },
    paginatedBiddingOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.biddingOrders.slice(start, end);
    },
    paginatedInProgressOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.inProgressOrders.slice(start, end);
    },
    paginatedCompletedOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.completedOrders.slice(start, end);
    },
    totalPages() {
      if (this.activeTab === "draft") {
        return Math.ceil(this.draftOrders.length / this.ordersPerPage);
      } else if (this.activeTab === "pending") {
        return Math.ceil(this.pendingOrders.length / this.ordersPerPage);
      } else if (this.activeTab === "bidding") {
        return Math.ceil(this.biddingOrders.length / this.ordersPerPage);
      } else if (this.activeTab === "inProgress") {
        return Math.ceil(this.inProgressOrders.length / this.ordersPerPage);
      } else if (this.activeTab === "completed") {
        return Math.ceil(this.completedOrders.length / this.ordersPerPage);
      }
      return 1;
    },
  },
  methods: {
    async fetchOrders() {
      try {
        const response = await axios.get("/orders");
        this.orders = response.data.orders;
      } catch (error) {
        console.error("获取订单列表失败", error);
      } finally {
        this.loading = false;
      }
    },
    async submitNewOrder() {
      try {
        const user = this.user;
        const newOrder = {
          ...this.newOrder,
          orders_id: 1, // 简单的订单ID生成方式
          user: user.nickname,
          orders_status: "pending",
          bidders: [],
          successful_bidder: null,
          created_time: new Date().toISOString(), // 设置创建时间
        };
        await axios.post("/orders", newOrder);
        this.orders.push(newOrder);
        this.setTab("pending");
      } catch (error) {
        console.error("发布新订单失败", error);
      }
    },
    setTab(tab) {
      this.activeTab = tab;
      this.currentPage = 1;
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    setMenu() {
      this.$store.dispatch("setActiveSubMenu", "order");
    },
  },
  created() {
    this.fetchOrders();
    this.setMenu();
    const tab = this.$route.query.tab;
    if (tab) {
      this.activeTab = tab;
    }
  },
};
</script>

<style scoped>
.my-tasks {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.tabs {
  display: flex;
  justify-content: left;
  margin-bottom: 20px;
}

.tabs button {
  width: 120px;
  font-size: 20px;
  padding: 10px 20px;
  background-color: #ccc4b8;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  margin: 0 10px;
}

.tabs button.active {
  background-color: #574c4c;
}

.order-list {
  list-style: none;
  padding: 0;
}

.order-item {
  background: white;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.order-info {
  display: flex;
  flex-direction: column;
}

.order-name {
  font-size: 18px;
  font-weight: bold;
}

.order-price {
  font-size: 16px;
  color: #666;
}

.view-detail-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-decoration: none;
  text-align: center;
}

.view-detail-button:hover {
  background-color: #0056b3;
}

.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.pagination button {
  padding: 10px 20px;
  background-color: #21c279;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.pagination button:hover {
  background-color: #358a66;
}

.new-task {
  padding: 20px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.submit-button {
  padding: 10px 20px;
  background-color: #69bce2;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
}

.submit-button:hover {
  background-color: #4d8ba8;
}
</style>
