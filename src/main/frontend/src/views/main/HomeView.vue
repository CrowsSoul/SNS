<template>
  <div class="home">
    <h1>欢迎 {{ user ? user.nickname : "访客" }}</h1>
    <button @click="logout">退出</button>
  </div>
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
      user: null,
    };
  },
  created() {
    const userData = localStorage.getItem("user");
    if (userData) {
      this.user = JSON.parse(userData);
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.home {
  text-align: center;
  padding: 20px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

button {
  padding: 10px 20px;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  margin-top: 20px;
}

button:hover {
  background: #358a66;
}

.router-link {
  display: block;
  margin-top: 10px;
  color: #42b983;
  text-decoration: none;
}

.router-link:hover {
  text-decoration: underline;
}
</style>
