<template>
  <div class="container-fluid">
    <div class="text-center mt-3">
      <h1 class="font-weight-bold">아파트 찾기</h1>
      <h7 class="font-weight-bold"
        >시/군구/동을 선택하면 해당 지역의 아파트 매물을 확인할 수 있습니다.</h7
      >
      <hr class="divider" />
    </div>
    <house-search-bar />
    <div class="row no-gutters">
      <kakao-map />
      <div class="col" v-if="tolist === true">
        <house-list />
      </div>
      <div class="col" v-else>
        <house-detail />
      </div>
    </div>
  </div>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";

import KakaoMap from "@/components/house/KakaoMap.vue";
import HouseList from "@/components/house/ScrollView/HouseList.vue";

//import Test from "@/components/house/Test.vue";
import { mapState } from "vuex";
import HouseDetail from "../components/house/ScrollView/HouseDetail.vue";

export default {
  name: "House",
  components: {
    HouseSearchBar,
    KakaoMap,
    HouseDetail,
    HouseList,
  },
  computed: {
    ...mapState("houseStore", ["tolist"]),
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 149, 27, 0.3) 30%
  );
}
</style>
