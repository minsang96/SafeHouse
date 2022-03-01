<template>
  <div>
    <b-card-body class="card-custom pl-0 pr-0">
      <ul class="list-group list-group-flush">
        <li class="list-group-item titlecolor">
          <h3>{{ house.aptName }}</h3>
        </li>
        <li class="list-group-item">
          <b-img :src="house.img" fluid-grow></b-img>
        </li>
        <li class="list-group-item">일련번호 : {{ house.aptCode }}</li>
        <li class="list-group-item">아파트 이름 : {{ house.aptName }}</li>
        <li class="list-group-item">건축연도 : {{ house.buildYear }}</li>
        <li class="list-group-item">법정동 : {{ house.dongName }}</li>
        <li class="list-group-item">
          거래금액 :
          {{ (parseInt(house.recentPrice.replace(",", "")) * 10000) | price }}원
        </li>
        <li class="list-group-item">면적 : {{ house.area }}</li>
        <li class="list-group-item">층 : {{ house.floor }}</li>
      </ul>
    </b-card-body>
    <div class="card mt-2">
      <h6 class="card-subtitle mb-2 mt-2 ml-2" style="font-weight: bold">
        지도에서 안전시설 확인하기
      </h6>
      <div class="form-check ml-3">
        <input
          class="form-check-input"
          type="checkbox"
          value="police"
          v-model="selected"
          id="checkPolice"
        />
        <label class="form-check-label" for="checkPolice"> 경찰서 </label>
      </div>
      <div class="form-check ml-3">
        <input
          class="form-check-input"
          type="checkbox"
          value="delivery"
          v-model="selected"
          id="checkDelivery"
        />
        <label class="form-check-label" for="checkDelivery">
          여성 안심 택배 서비스
        </label>
      </div>
      <div class="form-check ml-3">
        <input
          class="form-check-input"
          type="checkbox"
          value="cctv"
          v-model="selected"
          id="checkCctv"
        />
        <label class="form-check-label" for="checkCctv"> CCTV </label>
      </div>
      <button
        type="button"
        class="btn btn-outline-danger mt-2 mb-2 ml-2 mr-2"
        @click="setinfos"
      >
        조회
      </button>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const houseStore = "houseStore";
export default {
  name: "HouseDetail",
  data() {
    return {
      selected: [],
    };
  },
  computed: {
    ...mapState(houseStore, ["house"]),
  },

  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    ...mapMutations("houseStore", [
      "SET_CCTVS_FALSE",
      "SET_CCTVS_TRUE",
      "SET_DISPLAYDELIVERIES_FALSE",
      "SET_DISPLAYDELIVERIES_TRUE",
      "SET_POLICE_TRUE",
      "SET_POLICE_FALSE",
    ]),

    setinfos() {
      console.log(this.selected);
      if (this.selected.includes("police") === true) {
        this.SET_POLICE_TRUE();
      } else {
        this.SET_POLICE_FALSE();
      }

      if (this.selected.includes("delivery") === true) {
        console.log("delivery selected");
        this.SET_DISPLAYDELIVERIES_TRUE();
      } else {
        this.SET_DISPLAYDELIVERIES_FALSE();
      }

      if (this.selected.includes("cctv") === true) {
        console.log("cctv selected");
        this.SET_CCTVS_TRUE();
      } else {
        this.SET_CCTVS_FALSE();
      }
    },
  },
};
</script>

<style scoped>
.card-custom {
  background-color: #f2f2f2;
}

.titlecolor {
  background-color: #f4623a;
  color: #fff;
  text-align: center;
}

.list-group .list-group-flush .list-group-item {
  background-color: #f4623a;
}
</style>
