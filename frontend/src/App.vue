<template>
  <div id="app">
    <link rel="stylesheet" href="@/assets/styles.css" />
    <navi-bar />
    <router-view />
    <Footer />
  </div>
</template>

<script>
import NaviBar from "./components/layout/NaviBar.vue";
import Footer from "./components/layout/Footer.vue";

export default {
  name: "App",
  components: {
    NaviBar,
    Footer,
  },
};
</script>

<style src="@/assets/styles.css">
@font-face {
  font-family: "GothicA1";
  src: (
      "C:/SSAFY/work_view/d1119/happyhouse_front/src/assets/font/Gothic_A1/GothicA1-ExtraBold.ttf"
    )
    format("woff");
  font-weight: normal;
  font-style: normal;
}

#app {
  font-family: "GothicA1";
}

a:hover {
  text-decoration: none;
  font-weight: bold;
}
</style>
