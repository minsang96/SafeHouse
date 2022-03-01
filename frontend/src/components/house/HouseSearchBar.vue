<template>
  <!-- <b-col class="sm-3">
      <b-form-input
        v-model.trim="dongCode"
        placeholder="동코드 입력...(예 : 11110)"
        @keypress.enter="sendKeyword"
      ></b-form-input>
    </b-col>
    <b-col class="sm-3" align="left">
      <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
    </b-col> -->

  <div class="card w-50 mb-3 mt-3">
    <div class="btn-group mb-3 mt-3 ml-2">
      <b-form-select
        class="w-50 mr-2"
        v-model="sidoCode"
        :options="sidos"
        @change="gugunList"
      ></b-form-select>
      <b-form-select
        class="w-50 mr-2"
        v-model="gugunCode"
        :options="guguns"
        @change="dongList"
      ></b-form-select>
      <b-form-select
        class="w-50 mr-2"
        v-model="dongCode"
        :options="dongs"
        @change="[searchApt()]"
      ></b-form-select>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const houseStore = "houseStore";
export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
    };
  },
  computed: {
    ...mapState(houseStore, [
      "sidos",
      "guguns",
      "dongs",
      "houses",
      "positions",
    ]),

    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.getSido();
    (this.sidoCode = null), (this.gugunCode = null), (this.dongCode = null);
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
      "getPosList",
      "getCrime",
      "getCrimes",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "CLEAR_POSITIONS_LIST",
      "CLEAR_HOUSE_LIST",
      "CLEAR_POLICES_LIST",
    ]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      // console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      // console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) {
        this.getDong(this.gugunCode);
        this.getCrime(this.gugunCode);
        this.getCrimes();
      }
    },
    searchApt() {
      if (this.dongCode) {
        this.CLEAR_HOUSE_LIST();
        this.getHouseList(this.dongCode);
      }
    },
    searchPos() {
      if (this.dongCode) {
        // console.log(this.houses);
        this.CLEAR_POSITIONS_LIST();
        this.CLEAR_POLICES_LIST();
        this.getPosList(this.houses);

        // console.log(this.positions);
      }
    },
  },
  watch: {
    houses: function () {
      this.searchPos();
    },
  },
};
</script>

<style scoped></style>
