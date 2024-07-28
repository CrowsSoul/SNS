<template>
  <div class="my-participation">
    <h1>我的参与</h1>
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
        <router-link
          :to="{
            name: 'ActivityDetail',
            params: { id: activity.id },
            query: { from: 'participation', page: currentPage },
          }"
          class="detail-button"
        >
          查看详情
        </router-link>
      </li>
    </ul>
    <div v-else>
      <p>暂无参与的活动</p>
    </div>
    <div class="pagination" v-if="totalPages > 1">
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
  name: "MyParticipation",
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
        this.activities = response.data.filter((activity) =>
          activity.participantList.includes(this.user.nickname)
        );
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
.my-participation {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.my-participation h1 {
  text-align: center;1
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

.detail-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s;
  text-decoration: none;
}

.detail-button:hover {
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

.pagination span {
  margin: 0 10px;
}
</style>
