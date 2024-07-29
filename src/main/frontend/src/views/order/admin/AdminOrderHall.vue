<template>
  <div class="order-hall">
    <h1>管理员订单一览</h1>
    <div class="filters">
      <input type="text" v-model="searchQuery" placeholder="搜索订单" />
      <button @click="applySearch" class="search-button">搜索</button>
    </div>
    <div class="advanced-filters">
      <label for="priceFilter">按价格筛选:</label>
      <select id="priceFilter" v-model="priceFilter">
        <option value="all">无限制</option>
        <option value="1000以下">￥1000以下</option>
        <option value="1000-3000">￥1000-3000</option>
        <option value="3000-10000">￥3000-10000</option>
        <option value="10000以上">￥10000以上</option>
      </select>

      <label for="dateFilter">按日期筛选:</label>
      <select id="dateFilter" v-model="dateFilter">
        <option value="all">无限制</option>
        <option value="7days">近7天</option>
        <option value="30days">近30天</option>
        <option value="180days">近180天</option>
      </select>

      <button @click="applyFilters" class="filter-button">应用筛选</button>
    </div>
    <ul v-if="filteredOrders.length > 0" class="order-list">
      <li
        v-for="order in paginatedOrders"
        :key="order.orders_id"
        class="order-item"
      >
        <div class="order-info">
          <div class="order-header">
            <div class="order-name">{{ order.orders_name }}</div>
            <div
              v-if="order.orders_status === 'completed'"
              class="status-badge completed"
            >
              已完成
            </div>
            <div
              v-else-if="order.successful_bidder !== null"
              class="status-badge in-progress"
            >
              进行中
            </div>
            <div v-else class="status-badge bidding">竞标中</div>
          </div>
          <div class="order-price">￥{{ order.order_price }}</div>
        </div>
        <router-link
          :to="{
            name: 'AdminOrderDetail',
            params: { id: order.orders_id },
            query: { from: 'admin-order-hall' },
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
  name: "AdminOrderHall",
  data() {
    return {
      searchQuery: "",
      priceFilter: "all",
      dateFilter: "all",
      orders: [],
      currentPage: 1,
      ordersPerPage: 5,
      loading: true,
    };
  },
  computed: {
    filteredOrders() {
      let filtered = this.orders.filter(
        (order) => order.orders_status !== "pending"
      );

      if (this.searchQuery) {
        filtered = filtered.filter((order) =>
          order.orders_name.includes(this.searchQuery)
        );
      }

      if (this.priceFilter !== "all") {
        filtered = filtered.filter((order) => {
          const price = order.order_price;
          if (this.priceFilter === "1000以下") {
            return price < 1000;
          } else if (this.priceFilter === "1000-3000") {
            return price >= 1000 && price <= 3000;
          } else if (this.priceFilter === "3000-10000") {
            return price >= 3000 && price <= 10000;
          } else if (this.priceFilter === "10000以上") {
            return price > 10000;
          }
          return true;
        });
      }

      if (this.dateFilter !== "all") {
        const now = new Date();
        filtered = filtered.filter((order) => {
          const orderDate = new Date(order.created_time);
          if (this.dateFilter === "7days") {
            return now - orderDate <= 7 * 24 * 60 * 60 * 1000;
          } else if (this.dateFilter === "30days") {
            return now - orderDate <= 30 * 24 * 60 * 60 * 1000;
          } else if (this.dateFilter === "180days") {
            return now - orderDate <= 180 * 24 * 60 * 60 * 1000;
          }
          return true;
        });
      }

      return filtered;
    },
    paginatedOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.filteredOrders.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredOrders.length / this.ordersPerPage);
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
    applySearch() {
      this.currentPage = 1;
    },
    applyFilters() {
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
  },
  watch: {
    searchQuery() {
      this.applySearch();
    },
    priceFilter() {
      this.applyFilters();
    },
    dateFilter() {
      this.applyFilters();
    },
  },
};
</script>

<style scoped>
.order-hall {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.filters,
.advanced-filters {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.filters input,
.advanced-filters select {
  flex: 1;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  margin-right: 10px;
}

.filters button {
  padding: 10px 20px;
  border: none;
  background-color: #f0ad4e;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.filters button:hover {
  background-color: #ec971f;
}

.advanced-filters button {
  padding: 10px 20px;
  border: none;
  background-color: #6f42c1;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.advanced-filters button:hover {
  background-color: #5a2d91;
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

.order-header {
  display: flex;
  align-items: center;
}

.order-name {
  font-size: 18px;
  font-weight: bold;
  margin-right: 10px;
}

.status-badge {
  padding: 5px 10px;
  border-radius: 10px;
  font-size: 14px;
}

.status-badge.completed {
  background-color: #810c9e;
  color: white;
}

.status-badge.in-progress {
  background-color: #f36f9b;
  color: white;
}

.status-badge.bidding {
  background-color: #ff0000;
  color: white;
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

<style scoped>
.order-hall {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.filters,
.advanced-filters {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.filters input,
.advanced-filters select {
  flex: 1;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  margin-right: 10px;
}

.filters button {
  padding: 10px 20px;
  border: none;
  background-color: #f0ad4e;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.filters button:hover {
  background-color: #ec971f;
}

.advanced-filters button {
  padding: 10px 20px;
  border: none;
  background-color: #6f42c1;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.advanced-filters button:hover {
  background-color: #5a2d91;
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

.order-header {
  display: flex;
  align-items: center;
}

.order-name {
  font-size: 18px;
  font-weight: bold;
}

.status-badge {
  margin-left: 10px;
  padding: 5px 10px;
  border-radius: 10px;
  font-size: 14px;
}

.status-badge.completed {
  background-color: #810c9e;
  color: white;
}

.status-badge.in-progress {
  background-color: #f36f9b;
  color: white;
}

.status-badge.bidding {
  background-color: #8bd8fc;
  color: white;
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
