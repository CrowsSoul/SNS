<template>
  <div id="app">
    <header v-if="showHeader" class="header">
      <img src="./assets/icon.png" alt="Logo" class="logo" />
      <h1>中软国际SNS平台</h1>
      <div class="user-info">
        <img src="./assets/man.png" alt="Logo" class="circle-image" />
        <span>{{ user ? user.username : "访客" }}</span>
        <button @click="logout">退出</button>
      </div>
    </header>
    <div class="main-container">
      <nav v-if="showNav">
        <ul>
          <li>
            <router-link to="/home">
              <font-awesome-icon icon="home" class="nav-icon" /> 主页
            </router-link>
          </li>
          <li>
            <a @click="toggleSubMenu('blog')">
              <font-awesome-icon icon="file-alt" class="nav-icon" /> 博文
            </a>
            <ul v-if="activeSubMenu === 'blog'" class="submenu">
              <template v-if="isAdmin">
                <li>
                  <router-link to="/admin/blog-review">博文审核</router-link>
                </li>
                <li>
                  <router-link to="/admin/comment-review">评论审核</router-link>
                </li>
                <li>
                  <router-link to="/admin/featured-blogs">精选博文</router-link>
                </li>
              </template>
              <template v-else>
                <li>
                  <router-link to="/blogs">博文展示</router-link>
                </li>
                <li>
                  <router-link to="/my-blogs">我的博文</router-link>
                </li>
                <li>
                  <router-link to="/publish-blog" @click="resetEditBlog"
                    >发布博文</router-link
                  >
                </li>
              </template>
            </ul>
          </li>
          <li>
            <a @click="toggleSubMenu('recommend')">
              <font-awesome-icon icon="star" class="nav-icon" /> 内推
            </a>
            <ul v-if="activeSubMenu === 'recommend'" class="submenu">
              <template v-if="isStudent">
                <li>
                  <router-link to="/recommend-list">内推一览</router-link>
                </li>
                <li>
                  <router-link to="/my-favorites">我的收藏</router-link>
                </li>
              </template>
              <template v-else-if="isEmployedStudent">
                <li>
                  <router-link to="/recommend-list">内推一览</router-link>
                </li>
                <li>
                  <router-link to="/publish-recommend">发布内推</router-link>
                </li>
                <li>
                  <router-link to="/my-recommend">我的内推</router-link>
                </li>
              </template>
              <template v-else-if="isAdmin">
                <li>
                  <router-link to="/admin/recommend-review"
                    >内推审核</router-link
                  >
                </li>
                <li>
                  <router-link to="/admin/recommend-delete"
                    >删除内推</router-link
                  >
                </li>
              </template>
            </ul>
          </li>
          <li>
            <a @click="toggleSubMenu('activity')">
              <font-awesome-icon icon="calendar-alt" class="nav-icon" /> 活动
            </a>
            <ul v-if="activeSubMenu === 'activity'" class="submenu">
              <template v-if="isStudent || isEmployedStudent">
                <li>
                  <router-link to="/activity-hall">活动大厅</router-link>
                </li>
                <li>
                  <router-link to="/publish-activity">发布活动</router-link>
                </li>
                <li>
                  <router-link to="/activity-preparation">活动筹备</router-link>
                </li>
                <li>
                  <router-link to="/my-participation">我的参与</router-link>
                </li>
                <li>
                  <router-link to="/activity-record">活动记录</router-link>
                </li>
              </template>
              <template v-else-if="isAdmin">
                <li>
                  <router-link to="/admin/activity-review"
                    >审核活动</router-link
                  >
                </li>
                <li>
                  <router-link to="/admin/activity-delete"
                    >删除活动</router-link
                  >
                </li>
              </template>
            </ul>
          </li>
          <li>
            <a @click="toggleSubMenu('order')">
              <font-awesome-icon icon="tasks" class="nav-icon" /> 发包
            </a>
            <ul v-if="activeSubMenu === 'order'" class="submenu">
              <template v-if="isAdmin">
                <li>
                  <router-link to="/admin/order-hall">订单一览</router-link>
                </li>
                <li>
                  <router-link to="/admin/order-review">审核订单</router-link>
                </li>
              </template>
              <template v-else>
                <li>
                  <router-link to="/order-hall">订单大厅</router-link>
                </li>
                <li>
                  <router-link to="/resume-management">简历管理</router-link>
                </li>
                <li>
                  <router-link to="/my-tasks">我的发包</router-link>
                </li>
                <li>
                  <router-link to="/my-orders">我的接单</router-link>
                </li>
              </template>
            </ul>
          </li>
        </ul>
      </nav>
      <main>
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      user: null,
    };
  },
  computed: {
    showHeader() {
      return this.$route.path !== "/login" && this.$route.path !== "/register";
    },
    showNav() {
      return this.$route.path !== "/login" && this.$route.path !== "/register";
    },
    isAdmin() {
      return this.user && this.user.role === "admin";
    },
    isStudent() {
      return this.user && this.user.role === "student";
    },
    isEmployedStudent() {
      return this.user && this.user.role === "employed_student";
    },
    activeSubMenu() {
      return this.$store.getters.getActiveSubMenu;
    },
  },
  methods: {
    toggleSubMenu(menu) {
      if (this.activeSubMenu === menu) {
        this.$store.dispatch("setActiveSubMenu", null);
      } else {
        this.$store.dispatch("setActiveSubMenu", menu);
      }
    },
    loadUser() {
      const userData = localStorage.getItem("user");
      if (userData) {
        this.user = JSON.parse(userData);
      }
    },
    resetSubMenus() {
      if (this.$route.path.startsWith("/admin")) {
        this.$store.dispatch("setActiveSubMenu", "blog");
      } else if (
        this.$route.path.startsWith("/blogs") ||
        this.$route.path.startsWith("/my-blogs") ||
        this.$route.path.startsWith("/publish-blog") ||
        this.$route.path.startsWith("/edit-blog")
      ) {
        this.$store.dispatch("setActiveSubMenu", "blog");
      } else if (this.$route.path.startsWith("/recommend")) {
        this.$store.dispatch("setActiveSubMenu", "recommend");
      } else if (this.$route.path.startsWith("/activity")) {
        this.$store.dispatch("setActiveSubMenu", "activity");
      } else if (this.$route.path.startsWith("/task")) {
        this.$store.dispatch("setActiveSubMenu", "task");
      } else if (this.$route.path.startsWith("/order")) {
        this.$store.dispatch("setActiveSubMenu", "order");
      } else {
        this.$store.dispatch("setActiveSubMenu", null);
      }
    },
    resetEditBlog() {
      if (this.$route.name === "PublishBlog") {
        this.$router.replace({
          name: "PublishBlog",
          query: { reset: Date.now() },
        });
      } else {
        this.$router.push({ name: "PublishBlog" });
      }
    },
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/login");
    },
  },
  created() {
    this.loadUser();
    this.resetSubMenus();
  },
  watch: {
    $route() {
      this.loadUser();
      this.resetSubMenus();
    },
  },
};
</script>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.circle-image {
  width: 50px; /* 设置图像的宽度 */
  height: 50px; /* 设置图像的高度 */
  border-radius: 50%; /* 设置圆角半径为50%，即形成圆形 */
  object-fit: cover; /* 确保图像填充整个容器 */
  margin-right: 10px;
}

.logo {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-info span {
  margin-right: 20px;
}

.main-container {
  display: flex;
  flex: 1;
}

nav {
  background: linear-gradient(to bottom, #697685, #404852);
  padding: 20px;
  width: 180px;
}

nav ul {
  list-style: none;
  padding: 0;
}

nav ul li {
  margin-bottom: 10px;
  background-color: #3d454d;
  border-radius: 10px;
}

nav ul li a {
  display: flex;
  align-items: center;
  color: white;
  text-decoration: none;
  font-size: 18px;
  padding: 10px;
  border-radius: 5px;
  transition: background-color 0.3s, color 0.3s;
  cursor: pointer;
}

nav ul li a:hover {
  background-color: #80e289;
  color: rgb(255, 255, 255);
}

nav ul li a.active {
  background-color: #80e289;
  color: rgb(0, 0, 0);
}

.nav-icon {
  margin-right: 10px;
}

.submenu {
  list-style: none;
  padding-left: 20px;
  margin: 10px;
  width: 100px;
}

.submenu li a {
  font-size: 16px;
  padding: 5px;
  margin: 5px 0;
}

main {
  flex: 1;
  padding: 20px;
  background-color: #f5f5f5;
  overflow: auto;
}

header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: linear-gradient(to right, #1a1718ce, #1a1718ce);
  color: white;
  padding: 10px;
  text-align: center;
}

.header img.logo {
  margin-right: 10px;
}

.header h1 {
  flex: 1;
  text-align: left;
}

.header .user-info {
  display: flex;
  align-items: center;
}

.header .user-info span {
  margin-right: 10px;
  font-size: 18px; /* 调整字体大小 */
}

.header .user-info button {
  padding: 10px 20px; /* 调整按钮大小 */
  background-color: rgb(196, 109, 109); /* 调整按钮背景颜色 */
  color: white;
  border: none;
  border-radius: 10px; /* 调整按钮圆角 */
  font-size: 18px; /* 调整按钮字体大小 */
  cursor: pointer;
}

.header .user-info button:hover {
  background-color: rgb(121, 75, 75); /* 调整按钮悬停背景颜色 */
}
</style>
