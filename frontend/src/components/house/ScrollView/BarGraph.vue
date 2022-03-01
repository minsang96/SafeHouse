<template>
  <div class="card mt-2">
    <h6 class="card-subtitle mb-2 mt-2 ml-2 text-muted">
      범죄 건수 비교분석(건)
    </h6>
    <b-form-select
      class="w-50 mr-2 ml-2"
      v-model="selected"
      :options="options"
      @change="calc"
    ></b-form-select>
    <canvas id="barChart" class="mb-2"></canvas>
    <h6 class="card-subtitle mb-2 mt-2 ml-2 text-muted">
      범죄 다발 지역: {{ maxgu }} <br />
      범죄 안전 지역: {{ mingu }}
    </h6>
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
      barChart: null,
      selected: "null",
      options: [
        {
          text: "범죄 유형 선택",
          value: null,
        },
        {
          text: "절도",
          value: "theft",
        },
        {
          text: "살인",
          value: "murder",
        },
        {
          text: "강도",
          value: "robbery",
        },
        {
          text: "방화",
          value: "fire",
        },
        {
          text: "폭행",
          value: "attack",
        },
        {
          text: "상해",
          value: "injury",
        },
        {
          text: "주거침입",
          value: "housebreak",
        },
        {
          text: "교통방해",
          value: "obstructTraffic",
        },
        {
          text: "무면허",
          value: "noLicense",
        },
        {
          text: "음주운전",
          value: "drunkenRide",
        },
        {
          text: "교통사고",
          value: "roadViolation",
        },
        {
          text: "성범죄",
          value: "rape",
        },
      ],
      min: 0,
      max: 0,
      avg: null,
      now: null,
      mingu: null,
      maxgu: null,
    };
  },
  computed: {
    ...mapState(houseStore, ["crimes", "crime", "house"]),
  },
  watch: {
    house: function () {
      this.fillData();
    },
  },
  methods: {
    calc() {
      const type = this.selected;
      const c = this.crimes;
      var total = 0;
      this.min = 1000000;
      this.max = 0;
      c.forEach((elem) => {
        total += elem[type];
        if (elem[type] < this.min) {
          this.min = elem[type];
          this.mingu = elem.gugunName;
        }
        if (elem[type] > this.max) {
          this.max = elem[type];
          this.maxgu = elem.gugunName;
        }
      });
      this.avg = total / c.length;
      this.now = this.crime[0][type];
      if (this.min === 1000000) this.min = 0;
      this.fillData();
    },

    fillData() {
      if (this.barChart != null) {
        this.barChart.destroy();
      }
      let result = [this.min, this.avg, this.now, this.max];

      const data = {
        labels: ["최소", "평균", this.house.gugunName, "최대"],
        datasets: [
          {
            label: "집계 유형",
            data: result,
            backgroundColor: [
              "rgb(255,178, 119)",
              "rgb(119, 155, 255)",
              "rgb(237, 119, 255)",
              "rgb(255, 246, 119)",
            ],
            hoverOffset: 4,
          },
        ],
      };

      const config = {
        type: "bar",
        data: data,
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      };
      this.barChart = new Chart(document.getElementById("barChart"), config);
    },
  },
  mounted() {
    this.fillData();
  },
};
</script>

<style scoped>
.card-custom {
  background-color: #f2f2f2;
}
</style>
