<template>
  <div id="app">
    <header v-if="showHeader">
      <h1>超级无敌SNS平台</h1>
    </header>
    <div class="main-container">
      <nav v-if="showNav">
        <ul>
          <li>
            <router-link to="/">
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
            <a @click="toggleSubMenu('task')">
              <font-awesome-icon icon="tasks" class="nav-icon" /> 发包
            </a>
            <ul v-if="activeSubMenu === 'task'" class="submenu">
              <li>
                <router-link to="/task1">发包内容1</router-link>
              </li>
              <li>
                <router-link to="/task2">发包内容2</router-link>
              </li>
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

header {
  background-color: #42b983;
  color: white;
  padding: 10px;
  text-align: center;
}

.logo {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

.main-container {
  display: flex;
  flex: 1;
}

nav {
  background-color: #35495e;
  padding: 20px;
  width: 200px;
}

nav ul {
  list-style: none;
  padding: 0;
}

nav ul li {
  margin-bottom: 10px;
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
  background-color: #42b983;
  color: white;
}

nav ul li a.active {
  background-color: #42b983;
  color: white;
}

.nav-icon {
  margin-right: 10px;
}

.submenu {
  list-style: none;
  padding-left: 20px;
  margin: 10px 0 0 0;
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
</style>
