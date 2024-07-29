<template>
  <div class="order-detail" v-if="order">
    <div class="order-header">
      <h1>{{ order.orders_name }}</h1>
      <div v-if="isCompleted" class="status-badge completed">已完成</div>
      <div v-else-if="isBidding" class="status-badge bidding">竞标中</div>
      <div v-else-if="isInProgress" class="status-badge in-progress">
        进行中
      </div>
      <div v-else-if="isPending" class="status-badge pending">审核中</div>
    </div>
    <p>简介: {{ order.orders_intrudction }}</p>
    <p>发布时间: {{ order.created_time }}</p>
    <p>价格: ￥{{ order.order_price }}</p>
    <div class="buttons">
      <router-link :to="getBackRoute()" class="back-button">返回</router-link>
      <button v-if="isBidding" @click="viewBidders" class="view-bidders-button">
        查看竞标者
      </button>
      <button
        v-if="isInProgress"
        @click="confirmCompleteOrder"
        class="complete-button"
      >
        完成订单
      </button>
      <button
        v-if="isInProgress || isCompleted"
        @click="viewSuccessfulBidderResume"
        class="view-bidders-button"
      >
        接单者简历
      </button>
      <button
        v-if="isBidding || isPending"
        @click="confirmDeleteOrder"
        class="delete-button"
      >
        删除订单
      </button>
    </div>
    <div v-if="showBidders" class="bidders-box">
      <div class="bidders-box-content">
        <h3>竞标者列表</h3>
        <ul class="bidders-list">
          <li v-for="bidder in paginatedBidders" :key="bidder">
            <span>{{ bidder }}</span>
            <button @click="viewResume(bidder)" class="view-resume-button">
              查看简历
            </button>
          </li>
        </ul>
        <div class="pagination">
          <button @click="prevPage" :disabled="currentPage === 1">
            上一页
          </button>
          <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
          <button @click="nextPage" :disabled="currentPage === totalPages">
            下一页
          </button>
        </div>
        <button @click="closeBidders" class="close-button">关闭</button>
      </div>
    </div>
    <div v-if="showResume" class="resume-box">
      <div class="resume-box-content">
        <h3>{{ currentResume.user }}的简历</h3>
        <p>自我介绍: {{ currentResume.content }}</p>
        <p>工作经历: {{ currentResume.work_experience }}</p>
        <p>学历: {{ currentResume.education }}</p>
        <p>主要技能: {{ currentResume.skills.join(", ") }}</p>
        <button @click="closeResume" class="close-button">关闭</button>
      </div>
    </div>
    <div v-if="showDeleteConfirm" class="confirm-box">
      <div class="confirm-box-content">
        <p>是否确认删除该订单？</p>
        <div class="confirm-buttons">
          <button @click="deleteOrder" class="confirm-button">是</button>
          <button @click="cancelDeleteConfirm" class="cancel-button">否</button>
        </div>
      </div>
    </div>
    <div v-if="showCompleteConfirm" class="confirm-box">
      <div class="confirm-box-content">
        <p>是否确认完成该订单？</p>
        <div class="confirm-buttons">
          <button @click="completeOrder" class="confirm-button">是</button>
          <button @click="cancelCompleteConfirm" class="cancel-button">
            否
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
  name: "MyOrderDetail",
  data() {
    return {
      order: null,
      showBidders: false,
      showResume: false,
      currentResume: null,
      currentPage: 1,
      biddersPerPage: 5,
      showDeleteConfirm: false,
      showCompleteConfirm: false,
    };
  },
  computed: {
    isCompleted() {
      return this.order.orders_status === "completed";
    },
    isInProgress() {
      return (
        this.order.orders_status === "processing" &&
        this.order.successful_bidder
      );
    },
    isBidding() {
      return (
        this.order.orders_status === "processing" &&
        !this.order.successful_bidder
      );
    },
    isPending() {
      return this.order.orders_status === "pending";
    },
    paginatedBidders() {
      const start = (this.currentPage - 1) * this.biddersPerPage;
      const end = start + this.biddersPerPage;
      return this.order.bidders.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.order.bidders.length / this.biddersPerPage);
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
    viewBidders() {
      this.showBidders = true;
    },
    closeBidders() {
      this.showBidders = false;
    },
    async viewResume(bidder) {
      try {
        const response = await axios.get(`/resumes`);
        this.currentResume = response.data.resumes.find(
          (r) => r.user === bidder
        );
        this.showResume = true;
      } catch (error) {
        console.error("获取简历失败", error);
      }
    },
    closeResume() {
      this.showResume = false;
    },
    viewSuccessfulBidderResume() {
      this.viewResume(this.order.successful_bidder);
    },
    async deleteOrder() {
      try {
        await axios.delete(`/orders/${this.order.orders_id}`);
        this.$router.push({
          name: "MyTasks",
          query: { tab: this.$route.query.status },
        });
      } catch (error) {
        console.error("删除订单失败", error);
      }
    },
    confirmDeleteOrder() {
      this.showDeleteConfirm = true;
    },
    cancelDeleteConfirm() {
      this.showDeleteConfirm = false;
    },
    async completeOrder() {
      try {
        this.order.orders_status = "completed";
        await axios.put(`/orders/${this.order.orders_id}`, this.order);
        this.showCompleteConfirm = false;
        this.$router.push({ name: "MyTasks", query: { tab: "completed" } });
      } catch (error) {
        console.error("完成订单失败", error);
      }
    },
    confirmCompleteOrder() {
      this.showCompleteConfirm = true;
    },
    cancelCompleteConfirm() {
      this.showCompleteConfirm = false;
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
    getBackRoute() {
      if (this.$route.query.from === "my-tasks") {
        const status = this.$route.query.status;
        if (status === "bidding") {
          return { name: "MyTasks", query: { tab: "bidding" } };
        } else if (status === "inProgress") {
          return { name: "MyTasks", query: { tab: "inProgress" } };
        } else if (status === "completed") {
          return { name: "MyTasks", query: { tab: "completed" } };
        } else if (status === "pending") {
          return { name: "MyTasks", query: { tab: "pending" } };
        } else {
          return { name: "MyTasks" };
        }
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
  cursor: pointer;
  text-decoration: none;
  text-align: center;
  width: 120px;
  font-size: 16px;
  border-radius: 20px;
}

.back-button:hover {
  background-color: #358a66;
}

.view-bidders-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
  text-align: center;
  width: 120px;
  font-size: 16px;
  border-radius: 20px;
}

.complete-button {
  padding: 10px 20px;
  background-color: #a459eb;
  color: white;
  border: none;
  cursor: pointer;
  text-align: center;
  width: 120px;
  font-size: 16px;
  border-radius: 20px;
}

.view-bidders-button:hover,
.complete-button:hover {
  background-color: #0056b3;
}

.delete-button {
  padding: 10px 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  cursor: pointer;
  text-align: center;
  width: 120px;
  font-size: 16px;
  border-radius: 20px;
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
  background-color: #9597f7;
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

.status-badge.pending {
  background-color: #f0ad4e;
  color: white;
}

.bidders-box,
.resume-box {
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

.bidders-box-content {
  background: #2c3e50;
  padding: 40px;
  border-radius: 30px;
  text-align: center;
  width: 400px;
  max-width: 90%;
}

.resume-box-content {
  background: #2c3e50;
  padding: 40px;
  border-radius: 30px;
  text-align: left;
  width: 500px;
  max-width: 90%;
}

.resume-box-content h3,
.bidders-box-content h3 {
  font-size: 30px;
  text-align: center;
}

.bidders-list {
  list-style: none;
  padding: 0;
  margin: 20px 0;
}

.bidders-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.view-resume-button {
  padding: 5px 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}

.view-resume-button:hover {
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

.close-button {
  padding: 10px 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  margin-top: 20px;
}

.close-button:hover {
  background-color: #c0392b;
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
