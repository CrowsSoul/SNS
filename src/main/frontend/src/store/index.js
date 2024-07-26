import { createStore } from 'vuex';

export default createStore({
  state: {
    user: null,
    activeSubMenu: null
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    setActiveSubMenu(state, menu) {
      state.activeSubMenu = menu;
    }
  },
  actions: {
    login({ commit }, user) {
      // 在这里可以调用后端API
      commit('setUser', user);
    },
    logout({ commit }) {
      commit('setUser', null);
    },
    setActiveSubMenu({ commit }, menu) {
      commit('setActiveSubMenu', menu);
    }
  },
  getters: {
    isAuthenticated: state => !!state.user,
    getUser: state => state.user,
    getActiveSubMenu: state => state.activeSubMenu
  }
});
