<template>
  <div class="col">
    <b-card no-body class="card-header-custom">
      <b-nav pills card-header slot="header" v-b-scrollspy:nav-scroller>
        <b-nav-item href="#detail">매물보기</b-nav-item>
        <b-nav-item href="#suggest">추천매물</b-nav-item>
      </b-nav>

      <b-card-body
        class="card-custom pl-0 pr-0"
        id="nav-scroller"
        ref="content"
        style="height: 735px; overflow-y: scroll"
      >
        <h4 class="card-header border detailarea" id="detail">매물보기</h4>
        <div id="list" class="cgroup" v-if="houses && houses.length != 0">
          <div
            v-for="(house, index) in houses"
            :key="index"
            :house="house"
            class="card mt-2"
          >
            <img
              class="card-img-top"
              :src="house.img"
              alt="Card image cap"
              @click="setHouse(house)"
            />
            <div class="card-body">
              <h5 class="card-text" style="font-weight: bold">
                {{ house.aptName }}
                <img
                  id="wish"
                  v-if="checkWishList(house)"
                  src="@/assets/wishlist-selected.png"
                  alt="Card image cap"
                  height="30"
                  width="30"
                  align="right"
                  @click="removeWishList(house)"
                />
                <img
                  v-else
                  id="nowish"
                  src="@/assets/wishlist.png"
                  alt="Card image cap"
                  height="30"
                  width="30"
                  align="right"
                  @click="addWishList(house)"
                />
              </h5>
              <h6 class="card-text">
                <!-- 매매가 :
                {{
                  (parseInt(house.recentPrice.replace(",", "")) * 10000) | price
                }}
                원 -->
                {{ house.recentPrice }}
              </h6>
            </div>
          </div>
        </div>
        <b-container v-else class="bv-example-row mt-3">
          <b-row>
            <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
          </b-row>
        </b-container>

        <p>
          <b-card-body class="card-custom pl-0 pr-0">
            <h4 class="card-header border detailarea" id="suggest">추천매물</h4>
            <div id="list" class="cgroup" v-if="houses && houses.length != 0">
              <div
                v-for="(house, index) in makeRecommend"
                :key="index"
                :house="house"
                class="card mt-2"
              >
                <img
                  class="card-img-top"
                  :src="house.house.img"
                  alt="Card image cap"
                  @click="setHouse(house.house)"
                />
                <div class="card-body">
                  <h5 class="card-text" style="font-weight: bold">
                    {{ index + 1 }}순위 - {{ house.house.aptName }}
                    <!-- | {{ calculate(house.house) }} -->
                    <img
                      v-if="checkWishList(house.house)"
                      src="@/assets/wishlist-selected.png"
                      alt="Card image cap"
                      height="30"
                      width="30"
                      align="right"
                      @click="removeWishList(house.house)"
                    />
                    <img
                      v-else
                      src="@/assets/wishlist.png"
                      alt="Card image cap"
                      height="30"
                      width="30"
                      align="right"
                      @click="addWishList(house.house)"
                    />
                  </h5>
                  <h6 class="card-text">
                    <!-- {{
                      (parseInt(house.recentPrice.replace(",", "")) * 10000)
                        | price
                    }}
                    원 -->
                    {{ house.house.recentPrice }}
                  </h6>
                </div>
              </div>
            </div>
          </b-card-body>
        </p>
      </b-card-body>
    </b-card>
  </div>
</template>

<script>
//import HouseListRow from "@/components/house/HouseListRow.vue";
import { mapState, mapMutations, mapActions } from "vuex";
import http from "@/util/http-common.js";
export default {
  name: "HouseList",
  components: {
    // HouseListRow,
  },
  created() {
    this.makeWishList(this.userInfo.userid);
  },
  computed: {
    ...mapState("houseStore", ["houses", "house", "crime", "polices", "cctvs"]),
    ...mapState("memberStore", ["userInfo", "wishlist"]),
    makeRecommend() {
      const recommends = [];
      // const scores = [];
      let size = this.houses.length;
      if (size >= 5) size = 5;
      for (let i = 0; i < size; i++) {
        recommends.push({
          house: this.houses[i],
          score: this.calculate(this.houses[i]),
        });
      }
      recommends.sort(function (a, b) {
        return b.score - a.score;
      });
      console.log(recommends);
      return recommends;
    },
    getScore(house) {
      this.calculate(house);
      return this.score;
    },
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    ...mapMutations("houseStore", [
      "SET_TOLIST_FALSE",
      "SET_DETAIL_HOUSE",
      "SET_WISH_LIST",
    ]),
    ...mapActions("memberStore", [
      "makeWishList",
      "addApttoWishList",
      "deleteAptfromWishList",
    ]),
    setHouse(house) {
      this.SET_DETAIL_HOUSE(house);
      this.SET_TOLIST_FALSE();
    },
    async addWishList(house) {
      const info = {
        userid: this.userInfo.userid,
        aptCode: house.aptCode,
        dongCode: house.dongCode,
      };
      await this.addApttoWishList(info);
      alert("찜 등록에 성공하였습니다.");
      this.$router.go(); // 새로고침
    },
    removeWishList(house) {
      http.get("/user/wishlist/" + this.userInfo.userid).then(({ data }) => {
        console.log(data);
        data.forEach((element) => {
          if (element.aptCode == house.aptCode) {
            this.deleteAptfromWishList(element.wishlist_no);
          }
        });
        this.makeWishList(this.userInfo.userid);
      });
      alert("찜 목록에서 삭제되었습니다.");
      this.$router.go(); // 새로고침
    },
    checkWishList(house) {
      for (let i = 0; i < this.wishlist.length; i++) {
        if (this.wishlist[i].aptCode == house.aptCode) {
          return true;
        }
      }
      return false;
    },
    calculate(house) {
      var score = 0;
      const c = this.crime[0].total;
      const p = this.polices.length;
      var cctv = 0;
      console.log("cctv들", this.cctvs);
      for (let i = 0; i < this.cctvs.length; i++) {
        /* console.log(
          this.getDistance(
            house.lat,
            house.lng,
            this.cctvs[i].WGSXPT,
            this.cctvs[i].WGSYPT
          )
        );*/
        if (
          this.getDistance(
            house.lat,
            house.lng,
            this.cctvs[i].WGSXPT,
            this.cctvs[i].WGSYPT
          ) <= 1
        )
          cctv++;
      }
      //  console.log("총 범죄 발생 건수", c);
      //  console.log("경찰서 개수", p);
      //  console.log("cctv 개수", cctv);
      score = Math.ceil(((p * 20 + cctv * 10) / c) * 1000);

      return score;
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

<style scoped>
.card-img-top {
  width: 100%;
  height: 120px;
  object-fit: cover;
}

.card-custom {
  background-color: #f2f2f2;
}
.card-header-custom {
  background-color: #fff;
}
.detailarea {
  width: "100%";
  background-color: #fff;
}
.nav-pills .nav-link.active,
.nav-pills .show > .nav-link {
  color: #fff;
  background-color: #c34e2e;
}

.card-header {
  background-color: #f4623a;
  color: #fff;
}
</style>
