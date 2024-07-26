<template>
  <div class="activity-hall">
    <h1>活动大厅</h1>
    <div class="filters">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="搜索活动名称、发起者、地点"
      />
      <button @click="applySearch">搜索</button>
    </div>
    <div class="advanced-filters">
      <label for="dateFilter">按日期筛选:</label>
      <select id="dateFilter" v-model="dateFilter">
        <option value="all">无限制</option>
        <option value="3days">近3日</option>
        <option value="7days">近7日</option>
        <option value="30days">近30日</option>
        <option value="180days">近180日</option>
      </select>

      <label for="participantFilter">按人数筛选:</label>
      <select id="participantFilter" v-model="participantFilter">
        <option value="all">无限制</option>
        <option value="10">10人以内</option>
        <option value="50">10-50人</option>
        <option value="50plus">50人以上</option>
      </select>
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
        <router-link
          :to="{
            name: 'ActivityDetail',
            params: { id: activity.id },
            query: { page: currentPage, from: 'hall' },
          }"
          class="action-button"
          >查看详情</router-link
        >
      </li>
    </ul>
    <div v-else>
      <p>没有相关活动...</p>
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
  name: "ActivityHall",
  data() {
    return {
      searchQuery: "",
      dateFilter: "all",
      participantFilter: "all",
      activities: [],
      currentPage: 1,
      activitiesPerPage: 3,
      loading: true,
    };
  },
  computed: {
    filteredActivities() {
      let filtered = this.activities.filter(
        (activity) => activity.status === "通过"
      );

      if (this.searchQuery) {
        const searchLower = this.searchQuery.toLowerCase();
        filtered = filtered.filter(
          (activity) =>
            activity.name.toLowerCase().includes(searchLower) ||
            activity.initiator.toLowerCase().includes(searchLower) ||
            activity.location.toLowerCase().includes(searchLower)
        );
      }

      if (this.dateFilter !== "all") {
        const now = new Date();
        filtered = filtered.filter((activity) => {
          const activityDate = new Date(...activity.time);
          if (this.dateFilter === "3days") {
            return now - activityDate <= 3 * 24 * 60 * 60 * 1000;
          } else if (this.dateFilter === "7days") {
            return now - activityDate <= 7 * 24 * 60 * 60 * 1000;
          } else if (this.dateFilter === "30days") {
            return now - activityDate <= 30 * 24 * 60 * 60 * 1000;
          } else if (this.dateFilter === "180days") {
            return now - activityDate <= 180 * 24 * 60 * 60 * 1000;
          }
          return true;
        });
      }

      if (this.participantFilter !== "all") {
        if (this.participantFilter === "10") {
          filtered = filtered.filter(
            (activity) => activity.maxParticipants <= 10
          );
        } else if (this.participantFilter === "50") {
          filtered = filtered.filter(
            (activity) =>
              activity.maxParticipants > 10 && activity.maxParticipants <= 50
          );
        } else if (this.participantFilter === "50plus") {
          filtered = filtered.filter(
            (activity) => activity.maxParticipants > 50
          );
        }
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
    formatDate(timeArray) {
      const [year, month, day, hour, minute] = timeArray;
      const formattedMinute = minute < 10 ? `0${minute}` : minute;
      return `${year}-${month}-${day} ${hour}:${formattedMinute}`;
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
    searchQuery() {
      this.applySearch();
    },
    dateFilter() {
      this.applyFilters();
    },
    participantFilter() {
      this.applyFilters();
    },
    $route() {
      this.setMenu();
    },
  },
};
</script>

<style scoped>
.activity-hall {
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

.filters label,
.advanced-filters label {
  margin-right: 10px;
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

.filters button,
.advanced-filters button {
  padding: 10px 20px;
  border: none;
  background-color: #f0ad4e;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.filters button:hover,
.advanced-filters button:hover {
  background-color: #ec971f;
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
