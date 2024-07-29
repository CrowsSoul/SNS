<template>
  <div class="my-orders">
    <h1>我的接单</h1>
    <div class="tabs">
      <button
        :class="{ active: activeTab === 'pending' }"
        @click="setTab('pending')"
      >
        申请中
      </button>
      <button
        :class="{ active: activeTab === 'success' }"
        @click="setTab('success')"
      >
        接单成功
      </button>
      <button
        :class="{ active: activeTab === 'completed' }"
        @click="setTab('completed')"
      >
        已完成
      </button>
    </div>
    <ul v-if="activeTab === 'pending'" class="order-list">
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
            name: 'OrderDetail',
            params: { id: order.orders_id },
            query: { from: 'my-orders', tab: 'pending' },
          }"
          class="view-detail-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <ul v-else-if="activeTab === 'success'" class="order-list">
      <li
        v-for="order in paginatedSuccessOrders"
        :key="order.orders_id"
        class="order-item"
      >
        <div class="order-info">
          <div class="order-name">{{ order.orders_name }}</div>
          <div class="order-price">￥{{ order.order_price }}</div>
        </div>
        <router-link
          :to="{
            name: 'OrderDetail',
            params: { id: order.orders_id },
            query: { from: 'my-orders', tab: 'success' },
          }"
          class="view-detail-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <ul v-else class="order-list">
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
            name: 'OrderDetail',
            params: { id: order.orders_id },
            query: { from: 'my-orders', tab: 'completed' },
          }"
          class="view-detail-button"
          >查看详情</router-link
        >
      </li>
    </ul>
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
  name: "MyOrders",
  data() {
    return {
      orders: [],
      activeTab: "pending",
      currentPage: 1,
      ordersPerPage: 5,
      loading: true,
    };
  },
  computed: {
    user() {
      return JSON.parse(localStorage.getItem("user"));
    },
    pendingOrders() {
      return this.orders.filter(
        (order) =>
          order.bidders.includes(this.user.nickname) &&
          order.successful_bidder === null &&
          order.orders_status !== "completed"
      );
    },
    successOrders() {
      return this.orders.filter(
        (order) =>
          order.successful_bidder === this.user.nickname &&
          order.orders_status !== "completed"
      );
    },
    completedOrders() {
      return this.orders.filter(
        (order) =>
          order.successful_bidder === this.user.nickname &&
          order.orders_status === "completed"
      );
    },
    paginatedPendingOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.pendingOrders.slice(start, end);
    },
    paginatedSuccessOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.successOrders.slice(start, end);
    },
    paginatedCompletedOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.completedOrders.slice(start, end);
    },
    totalPages() {
      if (this.activeTab === "pending") {
        return Math.ceil(this.pendingOrders.length / this.ordersPerPage);
      } else if (this.activeTab === "success") {
        return Math.ceil(this.successOrders.length / this.ordersPerPage);
      } else {
        return Math.ceil(this.completedOrders.length / this.ordersPerPage);
      }
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
.my-orders {
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
  background-color: #810c9e;
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
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.pagination button:hover {
  background-color: #358a66;
}
</style>
