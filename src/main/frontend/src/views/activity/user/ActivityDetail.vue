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
      <button @click="confirmRegister" class="register-button">报名</button>
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
  name: "ActivityDetail",
  data() {
    return {
      activity: null,
      user: null,
      showConfirm: false,
      showSuccess: false,
      confirmMessage: "",
    };
  },
  methods: {
    async fetchActivity() {
      try {
        const response = await axios.get(
          `/activities/${this.$route.params.id}`
        );
        this.activity = response.data;
      } catch (error) {
        console.error("获取活动详情失败", error);
      }
    },
    goBack() {
      const from = this.$route.query.from;
      if (from === "preparing") {
        this.$router.push({
          name: "ActivityPreparing",
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
      return `${timeArray[0]}-${timeArray[1]}-${timeArray[2]} ${timeArray[3]
        .toString()
        .padStart(2, "0")}:${timeArray[4].toString().padStart(2, "0")}`;
    },
    confirmRegister() {
      if (this.activity && this.user) {
        if (this.activity.participantList.includes(this.user.nickname)) {
          this.confirmMessage = "您已报名过了！";
        } else if (
          this.activity.currentParticipants >= this.activity.maxParticipants
        ) {
          this.confirmMessage = "报名人数已满！";
        } else {
          this.confirmMessage = "确定要报名参加此活动吗？";
        }
        this.showConfirm = true;
      }
    },
    async executeRegister() {
      if (this.confirmMessage === "确定要报名参加此活动吗？") {
        this.activity.currentParticipants += 1;
        this.activity.participantList.push(this.user.nickname);
        try {
          await axios.put(`/activities/${this.activity.id}`, this.activity);
          this.showSuccess = true;
        } catch (error) {
          console.error("报名失败", error);
        }
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
.register-button {
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
.register-button:hover {
  background-color: #358a66;
}

.register-button {
  background-color: #f0ad4e;
}

.register-button:hover {
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
</style>
