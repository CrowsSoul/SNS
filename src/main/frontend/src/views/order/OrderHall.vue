<template>
  <div class="order-hall">
    <h1>订单大厅</h1>
    <div class="filters">
      <input type="text" v-model="searchQuery" placeholder="搜索订单名" />
      <input type="text" v-model="initiatorQuery" placeholder="发起人名" />
      <button @click="applyFilters" class="search-button">搜索</button>
    </div>
    <div class="advanced-filters">
      <label for="priceRange">按价格筛选:</label>
      <select id="priceRange" v-model="priceRange">
        <option value="all">无限制</option>
        <option value="below1000">￥1000以下</option>
        <option value="1000to3000">￥1000-3000</option>
        <option value="3000to10000">￥3000-10000</option>
        <option value="above10000">￥10000以上</option>
      </select>
      <label for="dateRange">按发布时间筛选:</label>
      <select id="dateRange" v-model="dateRange">
        <option value="all">无限制</option>
        <option value="7days">7天内</option>
        <option value="30days">30天内</option>
        <option value="180days">180天内</option>
      </select>
      <button @click="applyFilters" class="filter-button">应用筛选</button>
    </div>
    <ul v-if="paginatedOrders.length > 0">
      <li v-for="order in paginatedOrders" :key="order.orders_id">
        <h2>{{ order.orders_name }}</h2>
        <p>价格: ￥{{ order.order_price }}</p>
        <router-link
          :to="{ name: 'OrderDetail', params: { id: order.orders_id } }"
          class="action-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <div v-else>
      <p>没有相关订单...</p>
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
  name: "OrderHall",
  data() {
    return {
      searchQuery: "",
      initiatorQuery: "",
      priceRange: "all",
      dateRange: "all",
      orders: [],
      currentPage: 1,
      ordersPerPage: 4,
    };
  },
  computed: {
    filteredOrders() {
      let filtered = this.orders;

      if (this.searchQuery) {
        filtered = filtered.filter((order) =>
          order.orders_name.includes(this.searchQuery)
        );
      }

      if (this.initiatorQuery) {
        filtered = filtered.filter((order) =>
          order.user_id.includes(this.initiatorQuery)
        );
      }

      if (this.priceRange !== "all") {
        filtered = filtered.filter((order) => {
          if (this.priceRange === "below1000") return order.order_price < 1000;
          if (this.priceRange === "1000to3000")
            return order.order_price >= 1000 && order.order_price <= 3000;
          if (this.priceRange === "3000to10000")
            return order.order_price >= 3000 && order.order_price <= 10000;
          if (this.priceRange === "above10000")
            return order.order_price > 10000;
        });
      }

      if (this.dateRange !== "all") {
        const now = new Date();
        filtered = filtered.filter((order) => {
          const orderDate = new Date(order.created_time.replace(/-/g, "/"));
          if (this.dateRange === "7days")
            return now - orderDate <= 7 * 24 * 60 * 60 * 1000;
          if (this.dateRange === "30days")
            return now - orderDate <= 30 * 24 * 60 * 60 * 1000;
          if (this.dateRange === "180days")
            return now - orderDate <= 180 * 24 * 60 * 60 * 1000;
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
        const ordersResponse = await axios.get("/orders");
        this.orders = ordersResponse.data.orders;
      } catch (error) {
        console.error("获取订单列表失败", error);
      }
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
      this.applyFilters();
    },
    initiatorQuery() {
      this.applyFilters();
    },
    priceRange() {
      this.applyFilters();
    },
    dateRange() {
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
.advanced-filters input,
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

ul {
  list-style: none;
  padding: 0;
}

li {
  background: white;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

li h2 {
  margin: 0;
}

li p {
  margin: 10px 0;
}

.action-button {
  padding: 10px 20px;
  margin-top: 10px;
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
