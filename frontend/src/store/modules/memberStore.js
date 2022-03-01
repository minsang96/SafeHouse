import jwt_decode from "jwt-decode";
import {
  login,
  findById,
  signup,
  getWishList,
  addWishList,
  deleteWishList,
} from "@/api/member.js";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    wishlist: [],
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    SET_WISH_LIST: (state, wishlist) => {
      state.wishlist = wishlist;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    setUserSignUp({ commit }, user) {
      signup(
        user,
        (response) => {
          console.log("응답데이터", response.data);
          if (response.data === "success") {
            this.state.memberStore.isSignup = true;
            commit();
          } else {
            this.state.memberStore.isSignup = false;
            commit();
          }
        },
        (error) => {
          console.log("에러", error);
          commit();
        }
      );
    },
    makeWishList({ commit }, userid) {
      getWishList(
        userid,
        (response) => {
          // console.log(response.data);
          commit("SET_WISH_LIST", response.data);
        },
        (err) => {
          console.log(err);
        }
      );
    },
    addApttoWishList({ commit }, apt) {
      addWishList(
        apt,
        (response) => {
          console.log(response.data);
          commit();
        },
        (err) => {
          console.log(err);
        }
      );
    },
    deleteAptfromWishList({ commit }, wishlist_no) {
      deleteWishList(
        wishlist_no,
        (response) => {
          console.log(response.data);
          commit();
        },
        (err) => {
          console.log(err);
        }
      );
    },
  },
};

export default memberStore;
