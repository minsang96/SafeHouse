<template>
  <div class="card mt-2">
    <h6 class="card-subtitle mb-2 mt-2 ml-2 text-muted">범죄 유형별 비율(%)</h6>
    <canvas id="myChart" class="mb-2"></canvas>
  </div>
</template>

<script>
import { Chart, registerables } from "chart.js";
import { mapState } from "vuex";
const houseStore = "houseStore";
Chart.register(...registerables);

export default {
  data() {
    return {
      myChart: null,
    };
  },
  computed: {
    ...mapState(houseStore, ["crime", "house"]),
  },
  watch: {
    house: function () {
      this.fillData();
    },
  },
  methods: {
    fillData() {
      if (this.myChart != null) {
        this.myChart.destroy();
      }
      console.log("gu change");
      let result = [];
      const elem = this.crime[0];
      result.push((elem.theft / elem.total) * 100);
      result.push((elem.murder / elem.total) * 100);
      result.push((elem.robbery / elem.total) * 100);
      result.push((elem.fire / elem.total) * 100);
      result.push((elem.attack / elem.total) * 100);
      result.push((elem.injury / elem.total) * 100);
      result.push((elem.housebreak / elem.total) * 100);
      result.push((elem.obstructTraffic / elem.total) * 100);
      result.push((elem.noLicense / elem.total) * 100);
      result.push((elem.drunkenRide / elem.total) * 100);
      result.push((elem.roadViolation / elem.total) * 100);
      result.push((elem.rape / elem.total) * 100);

      console.log(result);

      const data = {
        labels: [
          "절도",
          "살인",
          "강도",
          "방화",
          "폭행",
          "상해",
          "주거침입",
          "교통방해",
          "무면허",
          "음주운전",
          "교통사고",
          "성범죄",
        ],
        datasets: [
          {
            label: "crime type",
            data: result,
            backgroundColor: [
              "rgb(255,188, 54)",
              "rgb(149, 207, 255)",
              "rgb(230, 196, 255)",

              "rgb(130, 172, 255)",
              "rgb(240, 125, 175)",
              "rgb(255,158, 161)",
              "rgb(255,252, 158)",
              "rgb(158,255, 252)",
              "rgb(158,161, 255)",

              "rgb(255,252, 93)",
              "rgb(221,233, 93)",
              "rgb(210,120, 93)",
            ],
            hoverOffset: 4,
          },
        ],
      };

      const config = {
        type: "doughnut",
        data: data,
      };
      this.myChart = new Chart(document.getElementById("myChart"), config);
    },
  },
  mounted() {
    this.fillData();
  },
};
</script>

<style></style>
