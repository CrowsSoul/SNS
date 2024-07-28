<template>
  <div class="admin-activity-delete">
    <h1>活动删除</h1>
    <div class="filters">
      <input type="text" v-model="searchQuery" placeholder="搜索活动名称" />
      <button @click="applySearch">搜索</button>
    </div>
    <ul v-if="filteredActivities.length > 0">
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
          <button @click="confirmDelete(activity)" class="delete-button">
            删除
          </button>
        </div>
      </li>
    </ul>
    <div v-else>
      <p>没有找到相关活动...</p>
    </div>
    <div class="pagination" v-if="totalPages > 1">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">
        下一页
      </button>
    </div>

    <!-- Confirm dialog -->
    <div v-if="showConfirm" class="report-success">
      <div class="report-success-box">
        <p>确定要删除活动 "{{ selectedActivity.name }}" 吗？</p>
        <div class="confirm-buttons">
          <button @click="executeDelete" class="confirm-button">确认</button>
          <button @click="cancelDelete" class="confirm-button cancel-button">
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
  name: "AdminActivityDelete",
  data() {
    return {
      activities: [],
      searchQuery: "",
      currentPage: 1,
      activitiesPerPage: 3,
      showConfirm: false,
      selectedActivity: null,
    };
  },
  computed: {
    filteredActivities() {
      let filtered = this.activities.filter(
        (activity) => activity.status === "通过"
      );

      if (this.searchQuery) {
        const searchLower = this.searchQuery.toLowerCase();
        filtered = filtered.filter((activity) =>
          activity.name.toLowerCase().includes(searchLower)
        );
      }

      return filtered;
    },
    paginatedActivities() {
      const start = (this.currentPage - 1) * this.activitiesPerPage;
      const end = start + this.activitiesPerPage;
      return this.filteredActivities.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredActivities.length / this.activitiesPerPage);
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
    applySearch() {
      this.currentPage = 1;
    },
    formatDate(timeArray) {
      return `${timeArray[0]}-${String(timeArray[1]).padStart(2, "0")}-${String(
        timeArray[2]
      ).padStart(2, "0")} ${String(timeArray[3]).padStart(2, "0")}:${String(
        timeArray[4]
      ).padStart(2, "0")}`;
    },
    confirmDelete(activity) {
      this.selectedActivity = activity;
      this.showConfirm = true;
    },
    async executeDelete() {
      try {
        await axios.delete(`/activities/${this.selectedActivity.id}`);
        this.fetchActivities();
        this.cancelDelete();
      } catch (error) {
        console.error("删除活动失败", error);
      }
    },
    cancelDelete() {
      this.showConfirm = false;
      this.selectedActivity = null;
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
.admin-activity-delete {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.admin-activity-delete h1 {
  text-align: center;
}

.filters {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.filters input {
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #ccc;
  margin-right: 10px;
}

.filters button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.filters button:hover {
  background-color: #358a66;
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

.delete-button {
  padding: 10px 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s;
}

.delete-button:hover {
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
