<template>
  <div class="admin-activity-review">
    <h1>活动审核</h1>
    <ul v-if="paginatedActivities.length > 0">
      <li v-for="activity in paginatedActivities" :key="activity.id">
        <h2>{{ activity.name }}</h2>
        <p>时间: {{ formatDate(activity.time) }}</p>
        <p>地点: {{ activity.location }}</p>
        <p>
          报名人数: {{ activity.currentParticipants }}/{{
            activity.maxParticipants
          }}
        </p>
        <div class="action-buttons">
          <button @click="openModal(activity)" class="detail-button">
            查看详情
          </button>
          <button @click="confirmApprove(activity)" class="approve-button">
            过审
          </button>
          <button @click="confirmReject(activity)" class="reject-button">
            不过审
          </button>
        </div>
      </li>
    </ul>
    <div v-else>
      <p>暂无审核中的活动</p>
    </div>
    <div class="pagination" v-if="totalPages > 1">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">
        下一页
      </button>
    </div>

    <!-- Modal for activity details -->
    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <h2>{{ selectedActivity.name }}</h2>
        <p>时间: {{ formatDate(selectedActivity.time) }}</p>
        <p>地点: {{ selectedActivity.location }}</p>
        <p>发起者: {{ selectedActivity.initiator }}</p>
        <p>
          报名人数: {{ selectedActivity.currentParticipants }}/{{
            selectedActivity.maxParticipants
          }}
        </p>
        <p>简介: {{ selectedActivity.description }}</p>
      </div>
    </div>

    <!-- Confirm dialog -->
    <div v-if="showConfirm" class="report-success">
      <div class="report-success-box">
        <p>{{ confirmMessage }}</p>
        <div class="confirm-buttons">
          <button
            v-if="confirmAction === 'approve'"
            @click="executeApprove"
            class="confirm-button"
          >
            确认
          </button>
          <button
            v-if="confirmAction === 'reject'"
            @click="executeReject"
            class="confirm-button"
          >
            确认
          </button>
          <button @click="cancelConfirm" class="confirm-button cancel-button">
            取消
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "AdminActivityReview",
  data() {
    return {
      activities: [],
      currentPage: 1,
      activitiesPerPage: 3,
      showModal: false,
      selectedActivity: null,
      showConfirm: false,
      confirmMessage: "",
      confirmAction: "",
    };
  },
  computed: {
    pendingActivities() {
      return this.activities.filter((activity) => activity.status === "审核中");
    },
    paginatedActivities() {
      const start = (this.currentPage - 1) * this.activitiesPerPage;
      const end = start + this.activitiesPerPage;
      return this.pendingActivities.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.pendingActivities.length / this.activitiesPerPage);
    },
  },
  methods: {
    async fetchActivities() {
      try {
        const response = await axios.get("/activities");
        this.activities = response.data;
      } catch (error) {
        console.error("获取活动列表失败", error);
      }
    },
    formatDate(timeArray) {
      return `${timeArray[0]}-${String(timeArray[1]).padStart(2, "0")}-${String(
        timeArray[2]
      ).padStart(2, "0")} ${String(timeArray[3]).padStart(2, "0")}:${String(
        timeArray[4]
      ).padStart(2, "0")}`;
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
    openModal(activity) {
      this.selectedActivity = activity;
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
      this.selectedActivity = null;
    },
    confirmApprove(activity) {
      this.confirmMessage = "确定要通过此活动的审核吗？";
      this.showConfirm = true;
      this.confirmAction = "approve";
      this.selectedActivity = activity;
    },
    confirmReject(activity) {
      this.confirmMessage = "确定要拒绝此活动的审核吗？";
      this.showConfirm = true;
      this.confirmAction = "reject";
      this.selectedActivity = activity;
    },
    async executeApprove() {
      try {
        await axios.put(`/activities/${this.selectedActivity.id}`, {
          status: "通过",
        });
        this.fetchActivities();
        this.closeModal();
      } catch (error) {
        console.error("活动审核失败", error);
      }
      this.showConfirm = false;
    },
    async executeReject() {
      try {
        await axios.put(`/activities/${this.selectedActivity.id}`, {
          status: "未过审",
        });
        this.fetchActivities();
        this.closeModal();
      } catch (error) {
        console.error("活动审核失败", error);
      }
      this.showConfirm = false;
    },
    cancelConfirm() {
      this.showConfirm = false;
      this.confirmMessage = "";
      this.confirmAction = "";
      this.selectedActivity = null;
    },
    setMenu() {
      this.$store.dispatch("setActiveSubMenu", "activity");
    },
  },
  created() {
    this.fetchActivities();
    this.setMenu();
  },
  watch: {
    $route() {
      this.setMenu();
    },
  },
};
</script>

<style scoped>
.admin-activity-review {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.admin-activity-review h1 {
  text-align: center;
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

h2 {
  margin: 0;
}

p {
  margin: 10px 0;
}

.action-buttons {
  display: flex;
  justify-content: space-between;
}

.detail-button,
.approve-button,
.reject-button {
  padding: 10px 20px;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s;
}

.detail-button {
  background-color: #007bff;
}

.detail-button:hover {
  background-color: #0056b3;
}

.approve-button {
  background-color: #42b983;
}

.approve-button:hover {
  background-color: #358a66;
}

.reject-button {
  background-color: #e74c3c;
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

/* Modal Styles */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 80%;
  max-width: 600px;
  position: relative;
}

.modal-content h2 {
  margin-top: 0;
}

.modal-content p {
  margin: 10px 0;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 24px;
  cursor: pointer;
  color: #333;
}

/* Confirm dialog styles */
.report-success {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}

.report-success-box {
  background: #2c3e50;
  padding: 40px;
  border-radius: 10px;
  text-align: center;
  width: 400px;
}

.confirm-button {
  padding: 15px 30px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  margin-top: 20px;
  font-size: 16px;
}

.confirm-button:hover {
  background-color: #358a66;
}

.confirm-buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.cancel-button {
  background-color: #e74c3c;
}

.cancel-button:hover {
  background-color: #c0392b;
}
</style>
