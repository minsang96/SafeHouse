<template>
  <div class="col">
    <b-card no-body class="card-header-custom">
      <b-nav pills card-header slot="header" v-b-scrollspy:nav-scroller>
        <button
          type="button"
          class="btn btn-dark ml-1"
          @click="SET_TOLIST_TRUE"
        >
          back
        </button>
        <b-nav-item href="#detail" @click="scrollIntoView">상세정보</b-nav-item>
        <b-nav-item href="#crime" @click="scrollIntoView">범죄현황</b-nav-item>
      </b-nav>

      <b-card-body
        class="card-custom pl-0 pr-0"
        id="nav-scroller"
        ref="content"
        style="height: 735px; overflow-y: scroll"
      >
        <h4 class="card-header border detailarea" id="detail">상세정보</h4>

        <p><detail-info /></p>
        <h4 class="card-header border detailarea" id="crime">범죄현황</h4>
        <div class="cgroup">
          <p><crime /></p>
          <p><pie-graph /></p>
          <p><bar-graph /></p>
        </div>
      </b-card-body>
    </b-card>
  </div>
</template>
<script>
import DetailInfo from "@/components/house/ScrollView/DetailInfo.vue";
import { mapState, mapMutations } from "vuex";
import PieGraph from "./PieGraph.vue";
import BarGraph from "./BarGraph.vue";
import Crime from "@/components/house/Crime.vue";
export default {
  computed: {
    ...mapState("houseStore", ["houses"]),
    //   PieGraph,
  },
  components: {
    DetailInfo,
    PieGraph,
    BarGraph,
    Crime,
  },
  methods: {
    ...mapMutations("houseStore", ["SET_TOLIST_TRUE"]),
    scrollIntoView(event) {
      event.preventDefault();
      const href = event.target.getAttribute("href");
      const el = href ? document.querySelector(href) : null;
      if (el) {
        this.$refs.content.scrollTop = el.offsetTop;
      }
    },
  },
  data() {
    return {
      text: ``,
    };
  },
};
</script>
<style scoped>
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
a {
  color: #f4623a;
}
.nav-pills .nav-link.active,
.nav-pills .show > .nav-link {
  color: #fff;
  background-color: #f4623a;
}

.card-header {
  background-color: black;
  color: #fff;
}
</style>
