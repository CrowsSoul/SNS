<template>
  <div class="activity-preparation">
    <h1>活动筹备</h1>
    <ul>
      <li v-for="activity in paginatedActivities" :key="activity.id">
        <h2>
          {{ activity.name }}
          <span
            v-if="activity.status === '审核中'"
            class="status status-pending"
            >审核中</span
          >
          <span
            v-if="activity.status === '未过审'"
            class="status status-rejected"
            >未过审</span
          >
        </h2>
        <p>时间: {{ formatDate(activity.time) }}</p>
        <p>地点: {{ activity.location }}</p>
        <p>
          报名人数: {{ activity.currentParticipants }}/{{
            activity.maxParticipants
          }}
        </p>
        <div class="action-buttons">
          <router-link
            :to="{
              name: 'ActivityDetailWithParticipants',
              params: { id: activity.id },
              query: { page: currentPage, from: 'preparing' },
            }"
            class="detail-button"
            >查看详情</router-link
          >
        </div>
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
  name: "ActivityPreparation",
  data() {
    return {
      activities: [],
      user: null,
      currentPage: 1,
      activitiesPerPage: 3,
    };
  },
  computed: {
    upcomingActivities() {
      const now = new Date();
      return this.activities.filter((activity) => {
        const activityDate = new Date(
          activity.time[0],
          activity.time[1] - 1,
          activity.time[2],
          activity.time[3],
          activity.time[4]
        );
        return activityDate > now;
      });
    },
    paginatedActivities() {
      const start = (this.currentPage - 1) * this.activitiesPerPage;
      const end = start + this.activitiesPerPage;
      return this.upcomingActivities.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.upcomingActivities.length / this.activitiesPerPage);
    },
  },
  methods: {
    async fetchActivities() {
      if (!this.user) return;
      try {
        const response = await axios.get("/activities");
        this.activities = response.data.filter(
          (activity) => activity.initiator === this.user.nickname
        );
      } catch (error) {
        console.error("获取活动列表失败", error);
      }
    },
    formatDate(timeArray) {
      const formattedTime = `${timeArray[0]}-${String(timeArray[1]).padStart(
        2,
        "0"
      )}-${String(timeArray[2]).padStart(2, "0")} ${String(
        timeArray[3]
      ).padStart(2, "0")}:${String(timeArray[4]).padStart(2, "0")}`;
      return formattedTime;
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
    const userData = localStorage.getItem("user");
    if (userData) {
      this.user = JSON.parse(userData);
    }
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
.activity-preparation {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.activity-preparation h1 {
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

li h2 {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.status {
  padding: 5px 10px;
  border-radius: 10px;
  color: white;
}

.status-pending {
  background-color: #f0ad4e;
}

.status-rejected {
  background-color: #d9534f;
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
.participants-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s;
  text-decoration: none; /* 去掉下划线 */
}

.detail-button:hover,
.participants-button:hover {
  background-color: #0056b3;
}

.participants-button {
  background-color: #f0ad4e;
}

.participants-button:hover {
  background-color: #ec971f;
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
