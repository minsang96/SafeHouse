<template>
  <header class="masthead">
    <div class="container px-4 px-lg-5 h-100">
      <div
        class="
          row
          gx-4 gx-lg-5
          h-100
          align-items-center
          justify-content-center
          text-center
        "
      >
        <div class="col-lg-8 align-self-end">
          <h1 class="text-white font-weight-bold">Safe House</h1>
          <hr class="divider" />
        </div>
        <div class="col-lg-8 align-self-baseline">
          <p class="text-white-75 mb-5">
            안전하고 좋은 집을 고르고 싶으세요?<br />한눈에 들어오는 매물정보와
            추천서비스를 제공합니다. 지금 시작하세요!
          </p>

          <router-link :to="{ name: 'House' }"
            ><button class="btn-primary btn-xl" style="color: white">
              아파트찾기
            </button></router-link
          >
        </div>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "Main",
  props: {
    msg: String,
  },
};
</script>

<style scoped>
.btn-primary,
.btn-primary:hover,
.btn-primary:active,
.btn-primary:visited {
  background-color: #f4623a !important;
}
.btn-primary {
  border-color: #f4623a;
}
</style>
