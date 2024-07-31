<template>
  <div class="order-review">
    <h1>订单审核</h1>
    <ul v-if="pendingOrders.length > 0" class="order-list">
      <li
        v-for="order in paginatedPendingOrders"
        :key="order.orders_id"
        class="order-item"
      >
        <div class="order-info">
          <div class="order-name">{{ order.orders_name }}</div>
          <div class="order-price">￥{{ order.order_price }}</div>
        </div>
        <div class="buttons">
          <button @click="viewDetails(order)" class="view-detail-button">
            查看详情
          </button>
          <button
            @click="confirmApprove(order.orders_id)"
            class="approve-button"
          >
            过审
          </button>
          <button @click="confirmReject(order.orders_id)" class="reject-button">
            不过审
          </button>
        </div>
      </li>
    </ul>
    <div v-else>
      <p>没有待审核的订单...</p>
    </div>
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">
        下一页
      </button>
    </div>

    <div v-if="showDetails" class="detail-box">
      <div class="detail-box-content">
        <h3>订单详情</h3>
        <p><strong>订单名:</strong> {{ selectedOrder.orders_name }}</p>
        <p><strong>简介:</strong> {{ selectedOrder.orders_intrudction }}</p>
        <p><strong>发布时间:</strong> {{ formatDatetime(selectedOrder.created_time) }}</p>
        <p><strong>价格:</strong> ￥{{ selectedOrder.order_price }}</p>
        <p>
          <strong>接单成功者:</strong>
          {{ selectedOrder.successful_bidder || "暂无" }}
        </p>
        <button @click="closeDetails" class="close-button">返回</button>
      </div>
    </div>

    <div v-if="showApproveConfirm" class="confirm-box">
      <div class="confirm-box-content">
        <p>您确认该订单过审吗？</p>
        <div class="confirm-buttons">
          <button @click="approveOrder" class="confirm-button">是</button>
          <button @click="cancelApproveConfirm" class="cancel-button">
            否
          </button>
        </div>
      </div>
    </div>

    <div v-if="showRejectConfirm" class="confirm-box">
      <div class="confirm-box-content">
        <p>您确认该订单不过审吗？</p>
        <div class="confirm-buttons">
          <button @click="rejectOrder" class="confirm-button">是</button>
          <button @click="cancelRejectConfirm" class="cancel-button">否</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AdminOrderReview",
  data() {
    return {
      orders: [],
      currentPage: 1,
      ordersPerPage: 5,
      showDetails: false,
      selectedOrder: null,
      showApproveConfirm: false,
      showRejectConfirm: false,
      orderToApprove: null,
      orderToReject: null,
      loading: true,
    };
  },
  computed: {
    pendingOrders() {
      return this.orders.filter((order) => order.orders_status === "pending");
    },
    paginatedPendingOrders() {
      const start = (this.currentPage - 1) * this.ordersPerPage;
      const end = start + this.ordersPerPage;
      return this.pendingOrders.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.pendingOrders.length / this.ordersPerPage);
    },
  },
  methods: {
    formatDatetime(datetime) {
      // 使用replace方法将'T'替换为空格
      return datetime.replace('T', ' ');
    },
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
    viewDetails(order) {
      this.selectedOrder = order;
      this.showDetails = true;
    },
    closeDetails() {
      this.showDetails = false;
    },
    confirmApprove(orderId) {
      this.orderToApprove = orderId;
      this.showApproveConfirm = true;
    },
    cancelApproveConfirm() {
      this.showApproveConfirm = false;
      this.orderToApprove = null;
    },
    async approveOrder() {
      if (!this.orderToApprove) return;
      try {
        await axios.put(`/orders/${this.orderToApprove}/approve`);
        this.fetchOrders();
        this.showApproveConfirm = false;
        this.orderToApprove = null;
      } catch (error) {
        console.error("订单过审失败", error);
      }
    },
    confirmReject(orderId) {
      this.orderToReject = orderId;
      this.showRejectConfirm = true;
    },
    cancelRejectConfirm() {
      this.showRejectConfirm = false;
      this.orderToReject = null;
    },
    async rejectOrder() {
      if (!this.orderToReject) return;
      try {
        await axios.delete(`/orders/${this.orderToReject}`);
        this.fetchOrders();
        this.showRejectConfirm = false;
        this.orderToReject = null;
      } catch (error) {
        console.error("订单不过审失败", error);
      }
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
};
</script>

<style scoped>
.order-review {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
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

.buttons {
  display: flex;
  gap: 10px;
}

.view-detail-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
}

.view-detail-button:hover {
  background-color: #0056b3;
}

.approve-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
}

.approve-button:hover {
  background-color: #358a66;
}

.reject-button {
  padding: 10px 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
}

.reject-button:hover {
  background-color: #c0392b;
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

.detail-box {
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

.detail-box-content {
  background: #2c3e50;
  padding: 40px;
  border-radius: 30px;
  text-align: left;
  width: 400px;
  max-width: 90%;
}

.close-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  margin-top: 20px;
}

.close-button:hover {
  background-color: #358a66;
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
  max-width: 90%;
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
