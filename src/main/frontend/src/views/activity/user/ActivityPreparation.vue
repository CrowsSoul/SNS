<template>
  <div class="activity-preparation">
    <h1>活动筹备</h1>
    <ul v-if="paginatedActivities.length">
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
          <router-link
            :to="{
              name: 'ActivityDetail',
              params: { id: activity.id },
              query: { from: 'preparation' },
            }"
            class="detail-button"
            >查看详情</router-link
          >
          <button
            @click="viewParticipants(activity)"
            class="participants-button"
          >
            查看报名情况
          </button>
        </div>
      </li>
    </ul>
    <div v-else>
      <p>暂无活动</p>
    </div>
    <div class="pagination" v-if="totalPages > 1">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">
        下一页
      </button>
    </div>

    <div v-if="showParticipantsModal" class="participants-modal">
      <div class="participants-modal-box">
        <h2>报名情况</h2>
        <ul v-if="paginatedParticipants.length">
          <li v-for="participant in paginatedParticipants" :key="participant">
            {{ participant }}
          </li>
        </ul>
        <div v-else>
          <p>暂无报名</p>
        </div>
        <div class="pagination" v-if="participantTotalPages > 1">
          <button
            @click="prevParticipantPage"
            :disabled="participantCurrentPage === 1"
          >
            上一页
          </button>
          <span
            >第 {{ participantCurrentPage }} 页，共
            {{ participantTotalPages }} 页</span
          >
          <button
            @click="nextParticipantPage"
            :disabled="participantCurrentPage === participantTotalPages"
          >
            下一页
          </button>
        </div>
        <button @click="closeParticipantsModal" class="close-button">
          关闭
        </button>
      </div>
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
      showParticipantsModal: false,
      participants: [],
      participantCurrentPage: 1,
      participantsPerPage: 5,
    };
  },
  computed: {
    paginatedActivities() {
      const start = (this.currentPage - 1) * this.activitiesPerPage;
      const end = start + this.activitiesPerPage;
      return this.activities.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.activities.length / this.activitiesPerPage);
    },
    paginatedParticipants() {
      const start =
        (this.participantCurrentPage - 1) * this.participantsPerPage;
      const end = start + this.participantsPerPage;
      return this.participants.slice(start, end);
    },
    participantTotalPages() {
      return Math.ceil(this.participants.length / this.participantsPerPage);
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
    viewParticipants(activity) {
      this.participants = activity.participantList;
      this.participantCurrentPage = 1;
      this.showParticipantsModal = true;
    },
    closeParticipantsModal() {
      this.showParticipantsModal = false;
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
    nextParticipantPage() {
      if (this.participantCurrentPage < this.participantTotalPages) {
        this.participantCurrentPage++;
      }
    },
    prevParticipantPage() {
      if (this.participantCurrentPage > 1) {
        this.participantCurrentPage--;
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
  border-radius: 5px;
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

.participants-modal {
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

.participants-modal-box {
  background: #2c3e50;
  padding: 40px;
  border-radius: 30px;
  text-align: center;
  width: 400px;
  max-height: 80vh;
  overflow-y: auto;
}

.participants-modal-box h2 {
  margin-top: 0;
}

.participants-modal-box ul {
  list-style: none;
  padding: 0;
}

.participants-modal-box li {
  background: white;
  color: black;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.participants-modal-box .pagination {
  margin-top: 10px;
}

.participants-modal-box .close-button {
  padding: 10px 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  margin-top: 20px;
}

.participants-modal-box .close-button:hover {
  background-color: #c0392b;
}
</style>
