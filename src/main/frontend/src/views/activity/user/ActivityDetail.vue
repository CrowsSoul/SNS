<template>
  <div class="activity-detail" v-if="activity">
    <h1>{{ activity.name }}</h1>
    <p>时间: {{ formatDate(activity.time) }}</p>
    <p>地点: {{ activity.location }}</p>
    <p>发起者: {{ activity.initiator }}</p>
    <p>
      报名人数: {{ activity.currentParticipants }}/{{
        activity.maxParticipants
      }}
    </p>
    <p>简介: {{ activity.description }}</p>
    <div class="action-buttons">
      <button @click="goBack" class="back-button">返回</button>
      <template v-if="!isPastActivity">
        <button
          v-if="!isParticipant"
          @click="confirmRegister"
          class="register-button"
        >
          报名
        </button>
        <button
          v-if="isParticipant"
          @click="confirmUnregister"
          class="unregister-button"
        >
          取消报名
        </button>
      </template>
    </div>
    <div v-if="showConfirm" class="report-success">
      <div class="report-success-box">
        <p>{{ confirmMessage }}</p>
        <div v-if="confirmMessage === '您已报名过了！'" class="confirm-buttons">
          <button @click="cancelRegister" class="confirm-button">确定</button>
        </div>
        <div v-else class="confirm-buttons">
          <button
            v-if="confirmMessage === '确定要报名参加此活动吗？'"
            @click="executeRegister"
            class="confirm-button"
          >
            确认
          </button>
          <button
            v-if="confirmMessage === '确定要取消报名吗？'"
            @click="executeUnregister"
            class="confirm-button"
          >
            确认
          </button>
          <button @click="cancelRegister" class="confirm-button cancel-button">
            取消
          </button>
        </div>
      </div>
    </div>
    <div v-if="showSuccess" class="report-success">
      <div class="report-success-box">
        <p>{{ successMessage }}</p>
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
  name: "ActivityDetail",
  data() {
    return {
      activity: null,
      user: null,
      isParticipant: false,
      showConfirm: false,
      showSuccess: false,
      confirmMessage: "",
      successMessage: "",
    };
  },
  computed: {
    isPastActivity() {
      if (!this.activity) return false;
      const now = new Date();
      const activityDate = new Date(
        this.activity.time[0],
        this.activity.time[1] - 1,
        this.activity.time[2],
        this.activity.time[3],
        this.activity.time[4]
      );
      return activityDate <= now;
    },
  },
  methods: {
    async fetchActivity() {
      try {
        const response = await axios.get(
          `/activities/${this.$route.params.id}`
        );
        this.activity = response.data;
        this.checkParticipant();
      } catch (error) {
        console.error("获取活动详情失败", error);
      }
    },
    checkParticipant() {
      if (this.user && this.activity) {
        this.isParticipant = this.activity.participantList.includes(
          this.user.nickname
        );
      }
    },
    goBack() {
      const from = this.$route.query.from;
      if (from === "record") {
        this.$router.push({
          name: "ActivityRecord",
          query: { page: this.$route.query.page },
        });
      } else if (from === "participation") {
        this.$router.push({
          name: "MyParticipation",
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
    confirmRegister() {
      this.confirmMessage = "确定要报名参加此活动吗？";
      this.showConfirm = true;
    },
    async executeRegister() {
      this.activity.currentParticipants += 1;
      this.activity.participantList.push(this.user.nickname);
      try {
        await axios.put(`/activities/${this.activity.id}`, this.activity);
        this.successMessage = "报名成功";
        this.showSuccess = true;
        this.isParticipant = true;
      } catch (error) {
        console.error("报名失败", error);
      }
      this.showConfirm = false;
    },
    confirmUnregister() {
      this.confirmMessage = "确定要取消报名吗？";
      this.showConfirm = true;
    },
    async executeUnregister() {
      this.activity.currentParticipants -= 1;
      const index = this.activity.participantList.indexOf(this.user.nickname);
      if (index > -1) {
        this.activity.participantList.splice(index, 1);
      }
      try {
        await axios.put(`/activities/${this.activity.id}`, this.activity);
        this.successMessage = "取消报名成功";
        this.showSuccess = true;
        this.isParticipant = false;
      } catch (error) {
        console.error("取消报名失败", error);
      }
      this.showConfirm = false;
    },
    cancelRegister() {
      this.showConfirm = false;
    },
    closeSuccess() {
      this.showSuccess = false;
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
.register-button,
.unregister-button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.3s;
}

.back-button:hover,
.register-button:hover,
.unregister-button:hover {
  background-color: #358a66;
}

.register-button {
  background-color: #f0ad4e; /* 黄色 */
}

.register-button:hover {
  background-color: #ec971f; /* 较深的黄色 */
}

.unregister-button {
  background-color: #e74c3c; /* 红色 */
}

.unregister-button:hover {
  background-color: #c0392b; /* 较深的红色 */
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
</style>
