<template>
  <div class="card mt-2 text-center" style="color: #f4623a; font-weight: bold">
    <p class="ml-3 p-3">
      현재 구 : {{ crime[0].gugunName }} <br />총 범죄 발생 건수 :
      {{ crime[0].total }}
    </p>
    <h3 class="ml-3" style="font-weight: bold">추천지수 : {{ score }}</h3>
    <h5
      v-if="getScore > 80"
      class="ml-3"
      style="color: blue; font-weight: bold"
    >
      높은 점수입니다.
    </h5>
    <h5
      v-else-if="getScore > 50"
      class="ml-3"
      style="color: black; font-weight: bold"
    >
      평균 점수입니다.
    </h5>
    <h5 v-else class="ml-3" style="color: red; font-weight: bold">
      낮은 점수입니다.
    </h5>

    <ul class="list-group list-group-flush mt-3">
      <li class="list-group-item">절도 : {{ crime[0].theft }}건</li>
      <li class="list-group-item">살인 : {{ crime[0].murder }}건</li>
      <li class="list-group-item">강도 : {{ crime[0].robbery }}건</li>
      <li class="list-group-item">방화 : {{ crime[0].fire }}건</li>
      <li class="list-group-item">폭행 : {{ crime[0].attack }}건</li>
      <li class="list-group-item">상해 : {{ crime[0].injury }}건</li>
      <li class="list-group-item">주거침입 :{{ crime[0].housebreak }}건</li>
      <li class="list-group-item">
        교통방해 : {{ crime[0].obstructTraffic }}건
      </li>
      <li class="list-group-item">무면허 : {{ crime[0].noLicense }}건</li>
      <li class="list-group-item">음주운전 :{{ crime[0].drunkenRide }}건</li>
      <li class="list-group-item">교통사고 : {{ crime[0].roadViolation }}건</li>
      <li class="list-group-item">성범죄 : {{ crime[0].rape }}건</li>
    </ul>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const houseStore = "houseStore";
export default {
  name: "Crime",
  data() {
    return {
      isShow: false,
      score: "",
      temp: "",
    };
  },
  computed: {
    ...mapState(houseStore, ["crime", "polices", "cctvs", "house"]),
    getScore() {
      this.calculate();
      return this.score;
    },
  },
  created() {},
  methods: {
    ...mapActions("houseStore", ["getCctvList"]),
    showDetail() {
      console.log(this.polices.length);
      if (this.isShow == true) {
        this.isShow = false;
      } else {
        this.isShow = true;
      }
    },
    calculate() {
      this.score = 0;
      const c = this.crime[0].total;
      const p = this.polices.length;
      var cctv = 0;
      for (let i = 0; i < this.cctvs.length; i++) {
        // console.log(this.getDistance(this.house.lat,this.house.lng,this.cctvs[i].WGSXPT,this.cctvs[i].WGSYPT));
        if (
          this.getDistance(
            this.house.lat,
            this.house.lng,
            this.cctvs[i].WGSXPT,
            this.cctvs[i].WGSYPT
          ) <= 1
        )
          cctv++;
      }
      console.log("총 범죄 발생 건수", c);
      console.log("경찰서 개수", p);
      console.log("cctv 개수", cctv);
      this.score = Math.ceil(((p * 20 + cctv * 10) / c) * 1000);
    },
    getDistance(lat1, lng1, lat2, lng2) {
      function deg2rad(deg) {
        return deg * (Math.PI / 180);
      }
      var R = 6371;
      var dLat = deg2rad(lat2 - lat1);
      var dLon = deg2rad(lng2 - lng1);
      var a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) *
          Math.cos(deg2rad(lat2)) *
          Math.sin(dLon / 2) *
          Math.sin(dLon / 2);
      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      var d = R * c;
      return Math.ceil(d);
    },
  },
};
</script>

<style></style>
