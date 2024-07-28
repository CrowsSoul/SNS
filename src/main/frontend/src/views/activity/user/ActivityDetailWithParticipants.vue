<template>
  <div class="activity-detail" v-if="activity">
    <h1>{{ activity.name }}</h1>
    <p>时间: {{ formatDate(activity.time) }}</p>
    <p>地点: {{ activity.location }}</p>
    <p>
      报名人数: {{ activity.currentParticipants }}/{{
        activity.maxParticipants
      }}
    </p>
    <p>简介: {{ activity.description }}</p>
    <div class="action-buttons">
      <button @click="goBack" class="back-button">返回</button>
      <button @click="viewParticipants" class="participants-button">
        显示参与者
      </button>
    </div>
    <div v-if="showParticipantsModal" class="participants-modal">
      <div class="participants-modal-box">
        <h2>报名人员</h2>
        <ul>
          <li v-for="participant in paginatedParticipants" :key="participant">
            {{ participant }}
          </li>
        </ul>
        <div class="pagination">
          <button
            @click="prevParticipantPage"
            :disabled="participantCurrentPage === 1"
            class="pagination-button"
          >
            上一页
          </button>
          <span class="pagination-text"
            >第 {{ participantCurrentPage }} 页，共
            {{ participantTotalPages }} 页</span
          >
          <button
            @click="nextParticipantPage"
            :disabled="participantCurrentPage === participantTotalPages"
            class="pagination-button"
          >
            下一页
          </button>
        </div>
        <button @click="closeParticipantsModal" class="close-button">
          关闭
        </button>
      </div>
    </div>
    <div v-if="showConfirm" class="report-success">
      <div class="report-success-box">
        <p>{{ confirmMessage }}</p>
        <button
          v-if="confirmMessage === '您已报名过了！'"
          @click="cancelRegister"
          class="confirm-button"
        >
          确定
        </button>
        <div v-else class="confirm-buttons">
          <button @click="executeRegister" class="confirm-button">确认</button>
          <button @click="cancelRegister" class="confirm-button cancel-button">
            取消
          </button>
        </div>
      </div>
    </div>
    <div v-if="showSuccess" class="report-success">
      <div class="report-success-box">
        <p>报名成功</p>
        <button @click="closeSuccess" class="confirm-button">确定</button>
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
  name: "ActivityDetailWithParticipants",
  data() {
    return {
      activity: null,
      user: null,
      showParticipantsModal: false,
      participants: [],
      participantCurrentPage: 1,
      participantsPerPage: 5,
    };
  },
  computed: {
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
    async fetchActivity() {
      try {
        const response = await axios.get(
          `/activities/${this.$route.params.id}`
        );
        this.activity = response.data;
        this.participants = this.activity.participantList;
      } catch (error) {
        console.error("获取活动详情失败", error);
      }
    },
    goBack() {
      const from = this.$route.query.from;
      if (from === "preparing") {
        this.$router.push({
          name: "ActivityPreparation",
          query: { page: this.$route.query.page },
        });
      } else {
        this.$router.push({
          name: "ActivityHall",
          query: { page: this.$route.query.page },
        });
      }
    },
    setMenu() {
      this.$store.dispatch("setActiveSubMenu", "activity");
    },
    formatDate(timeArray) {
      return `${timeArray[0]}-${String(timeArray[1]).padStart(2, "0")}-${String(
        timeArray[2]
      ).padStart(2, "0")} ${String(timeArray[3]).padStart(2, "0")}:${String(
        timeArray[4]
      ).padStart(2, "0")}`;
    },
    viewParticipants() {
      this.showParticipantsModal = true;
    },
    closeParticipantsModal() {
      this.showParticipantsModal = false;
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
  },
  created() {
    this.fetchActivity();
    this.setMenu();
    const userData = localStorage.getItem("user");
    if (userData) {
      this.user = JSON.parse(userData);
    }
  },
  watch: {
    $route() {
      this.setMenu();
    },
  },
};
</script>

<style scoped>
.activity-detail {
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 0 auto;
}

.activity-detail h1 {
  margin-top: 0;
}

.activity-detail p {
  margin: 10px 0;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

.back-button,
.pagination-button,
.participants-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s;
}

.back-button:hover,
.pagination-button:hover,
.participants-button:hover {
  background-color: #358a66;
}

.participants-button {
  background-color: #f0ad4e;
  margin-left: 10px;
}

.participants-button:hover {
  background-color: #ec971f;
}

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
  border-radius: 30px;
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
  background: #eef2f3;
  color: black;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.participants-modal-box .pagination {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.participants-modal-box .pagination-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

.participants-modal-box .pagination-button:hover {
  background-color: #358a66;
}

.participants-modal-box .pagination-text {
  margin: 0 10px;
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
