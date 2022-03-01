<template>
  <div>
    <nav
      class="navbar navbar-expand-lg navbar-light position-relative py-3"
      id="mainNav"
    >
      <div class="container px-4 px-lg-5">
        <router-link to="/">
          <a class="navbar-brand" href="#"> Safe House</a>
        </router-link>
        <button
          class="navbar-toggler navbar-toggler-right"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarResponsive"
          aria-controls="navbarResponsive"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ms-auto my-2 my-lg-0">
            <li class="nav-item">
              <a class="nav-link" href="#"
                ><router-link :to="{ name: 'Home' }">홈</router-link></a
              >
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#"
                ><router-link :to="{ name: 'Board' }">QnA</router-link></a
              >
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#"
                ><router-link :to="{ name: 'House' }"
                  >아파트찾기</router-link
                ></a
              >
            </li>
          </ul>
          <b-navbar-nav class="ml-auto" v-if="userInfo">
            <b-nav-item class="align-self-center">
              {{ userInfo.username }}({{ userInfo.userid }})님 좋은 하루
              되세요!</b-nav-item
            >
            <b-nav-item class="link align-self-center"
              ><router-link :to="{ name: 'MyPage' }"
                >내정보보기</router-link
              ></b-nav-item
            >
            <b-nav-item
              class="link align-self-center"
              @click.prevent="onClickLogout"
              >로그아웃</b-nav-item
            >
          </b-navbar-nav>
          <b-navbar-nav class="ml-auto" v-else>
            <b-nav-item class="align-self-center">
              <router-link :to="{ name: 'SignUp' }"
                ><b-icon icon="person-circle"></b-icon> 회원가입</router-link
              ></b-nav-item
            >
            <b-nav-item class="align-self-center">
              <router-link :to="{ name: 'SignIn' }"
                ><b-icon icon="key"></b-icon> 로그인</router-link
              ></b-nav-item
            >
          </b-navbar-nav>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "NaviBar",
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  created() {
    window.addEventListener("scroll", this.navbarShrink);
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
    },
    navBarShrink() {
      // Navbar shrink function

      const navbarCollapsible = document.body.querySelector("#mainNav");
      if (!navbarCollapsible) {
        return;
      }
      if (window.scrollY === 0) {
        navbarCollapsible.classList.remove("navbar-shrink");
      } else {
        navbarCollapsible.classList.add("navbar-shrink");
      }

      // Activate Bootstrap scrollspy on the main nav element
      //   const mainNav = document.body.querySelector("#mainNav");
      /*   if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
          target: "#mainNav",
          offset: 74,
        });
      }*/

      // Collapse responsive navbar when toggler is visible
      const navbarToggler = document.body.querySelector(".navbar-toggler");
      const responsiveNavItems = [].slice.call(
        document.querySelectorAll("#navbarResponsive .nav-link")
      );
      responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener("click", () => {
          if (window.getComputedStyle(navbarToggler).display !== "none") {
            navbarToggler.click();
          }
        });
      });
    },
  },
};
</script>
<style scoped src="@/assets/styles.css"></style>
