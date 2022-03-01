import http from "@/util/http-common.js";

const commentStore = {
  namespaced: true,
  state: { comments: [] },
  getters: {
    comments(state) {
      return state.comments;
    },
  },
  mutations: {
    SET_COMMENTS(state, payload) {
      state.comments = payload;
    },
  },
  actions: {
    getComments({ commit }, payload) {
      http
        .get("/comment/" + payload)
        .then(({ data }) => {
          commit("SET_COMMENTS", data);
        })
        .catch(() => {
          alert("댓글조회: 에러발생!");
        });
    },
  },
};

export default commentStore;
