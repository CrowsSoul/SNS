<template>
  <div class="home-content">
    <div class="header">
      <h1 v-if="user" class="welcome-message">
        欢迎来到中软国际SNS平台, {{ user.nickname }}！
      </h1>
    </div>
    <div class="ads">
      <div class="ad">
        <h2>想成为互联网领域大神吗？</h2>
        <div class="ad-image">
          <img src="@/assets/chinasoft.png" alt="图片无法显示" />
        </div>
        <p>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          爱因斯坦曾说：“如果我没在大连成为马征老师的学生，人类将不会发现广义相对论。”
          马化腾被问起最后悔的事时坦言：“如果我创立的不是腾讯而是中软国际，那我赚的钱将翻上10倍。”
          就连五星上将麦克阿瑟也感慨道：“如果美国拥有中软国际那样的电力系统，二战的结束时间将提早两年半。”
          如今，一个属于中软的时代正在到来！
        </p>
        <div class="button-container">
          <button @click="redirectToCourse1">点击查看！</button>
        </div>
      </div>
      <div class="ad">
        <h2>原来你也玩原神!</h2>
        <div class="ad-image">
          <img src="@/assets/genshin.png" alt="图片无法显示" />
        </div>
        <p>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          《原神》是由米哈游自主研发的一款全新开放世界冒险游戏。游戏发生在一个被称作「提瓦特」的幻想世界，在这里，被神选中的人将被授予「神之眼」，导引元素之力。
          你将扮演一位名为「旅行者」的神秘角色，在自由的旅行中邂逅性格各异、能力独特的同伴们，和他们一起击败强敌，找回失散的亲人——同时，逐步发掘「原神」的真相。
        </p>
        <div class="button-container">
          <button @click="redirectToCourse2">点击查看！</button>
        </div>
      </div>
      <div class="ad">
        <h2>数C9时却只能数出8所？</h2>
        <div class="ad-image">
          <img src="@/assets/hit.png" alt="图片无法显示" width="100%" />
        </div>
        <p>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          注意看，这是全世界唯一拥有核打击能力的大学，只要校长于大淇一声令下，整个地球都会被夷为平地。
          近年来，哈工大的分数线逐年提高，有人预测，到2030年，哈工大将与清华大学、北京大学并称“清北哈”，成为中国大学三极中的一极。
          规格严格，不让及格；功夫到家，不行回家。
          让我们大声喊出：我们是C9！我们是C9！
        </p>
        <div class="button-container">
          <button @click="redirectToCourse3">点击查看！</button>
        </div>
      </div>
    </div>
    <div v-if="signInDialogVisible" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeDialog">&times;</span>
        <p>签到成功！</p>
        <button @click="closeDialog" class="confirm-button">确认</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";

export default {
  name: "HomeContent",
  data() {
    return {
      signInDialogVisible: false,
      isSignedInToday: false,
      user: null,
    };
  },
  computed: {
    ...mapState({
      user: (state) => state.user,
      unreadMessages: (state) => state.unreadMessages,
      unpaidCourses: (state) => state.unpaidCourses,
      unfinishedEvaluations: (state) => state.unfinishedEvaluations,
    }),
  },
  created() {
    this.fetchUnreadMessages();
    this.fetchUnpaidCourses();
    this.fetchUnfinishedEvaluations();
    this.checkSignInStatus();
  },
  methods: {
    loadUser() {
      const userData = localStorage.getItem("user");
      if (userData) {
        this.user = JSON.parse(userData);
      }
    },
    ...mapActions([
      "fetchUnreadMessages",
      "fetchUnpaidCourses",
      "fetchUnfinishedEvaluations",
    ]),
    async signIn() {
      if (this.isSignedInToday) return;

      try {
        await axios.post("http://localhost:3000/api/sign");
        this.signInDialogVisible = true;
        this.isSignedInToday = true;
      } catch (error) {
        console.error("Failed to sign in:", error);
      }
    },
    async checkSignInStatus() {
      try {
        const response = await axios.get("http://localhost:3000/api/sign");
        const lastSignInDate = response.data.date;
        const today = new Date().toISOString().split("T")[0];

        this.isSignedInToday = lastSignInDate === today;
      } catch (error) {
        console.error("Failed to check sign-in status:", error);
      }
    },
    closeDialog() {
      this.signInDialogVisible = false;
    },
    redirectToCourse1() {
      window.open("https://www.chinasofti.com/", "_blank");
    },
    redirectToCourse2() {
      window.open("https://ys.mihoyo.com/", "_blank");
    },
    redirectToCourse3() {
      window.open("https://www.hit.edu.cn/", "_blank");
    },
  },
  created() {
    this.loadUser();
  },
};
</script>

<style scoped>
.home-content {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.sign-in-button {
  padding: 15px 30px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 18px;
  margin-right: 20px;
}

.sign-in-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.sign-in-button:hover {
  background-color: #218838;
}

.sign-in-button:active {
  background-color: #1e7e34;
}

.welcome-message {
  flex-grow: 1;
  text-align: center;
  font-size: 28px;
  margin: 0;
}

.home-content .header h1 {
  font-size: 28px;
  margin: 10px 0 30px 0;
  text-align: center;
}

.announcement {
  background-color: #f0f2f5;
  padding: 20px;
  border: 1px solid #aaa;
  margin-bottom: 20px;
  position: relative;
  border-radius: 10px;
}

.announcement h2 {
  position: absolute;
  top: 0;
  left: 0;
  margin: 0% 5px;
  font-size: 25px;
}

.notification {
  text-align: left;
  padding-left: 20px;
}

.red-dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  background-color: red;
  border-radius: 50%;
  margin-right: 10px;
}

.ads {
  display: flex;
  gap: 20px;
}

.ad {
  flex: 1;
  background-color: #fff;
  padding: 20px;
  border: 1px solid #aaa;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: 10px;
}

.ad h2 {
  font-size: 20px;
  margin-bottom: 10px;
}

.ad-image {
  background-color: #fff;
  height: 250px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}

.ad-image img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.ad p {
  font-size: 14px;
  color: #666;
  text-align: left;
  flex-grow: 1;
}

.button-container {
  text-align: center;
}

button {
  padding: 10px 20px;
  font-size: 16px;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, box-shadow 0.3s;
}

button:hover {
  background-color: #0056b3;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

button:active {
  background-color: #004085;
}

/* Modal Styles */
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fefefe;
  padding: 20px;
  border: 1px solid #aaa;
  width: 300px;
  border-radius: 10px;
  text-align: center;
  position: relative;
}

.close {
  color: #aaa;
  position: absolute;
  top: 10px;
  right: 20px;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.confirm-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 20px;
}

.confirm-button:hover {
  background-color: #0056b3;
}

.confirm-button:active {
  background-color: #004085;
}
</style>
