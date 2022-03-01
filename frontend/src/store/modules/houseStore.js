import {
  sidoList,
  gugunList,
  dongList,
  houseList,
  crimeList,
  crimesList,
  safeDeliveryList,
  cctvList,
} from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "시/도" }],
    guguns: [{ value: null, text: "구/군" }],
    dongs: [{ value: null, text: "동" }],
    houses: [],
    house: null,
    positions: [],
    crime: null,
    crimes: [],
    tolist: true,
    polices: null,
    deliveries: [],
    cctvs: [],
    displayDeliveries: false,
    displayCctvs: false,
    displayPolice: false,
  },

  getters: {},

  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    SET_POSITIONS_LIST: (state, infos) => {
      infos.forEach((info) => {
        state.positions.push(new Array(info.lat, info.lng));
      });
      console.log(state.positions);
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: null, text: "시/도" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: null, text: "구/군" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ value: null, text: "동" }];
    },
    CLEAR_HOUSE_LIST: (state) => {
      state.houses = [];
    },
    CLEAR_POSITIONS_LIST: (state) => {
      state.positions = [];
    },
    CLEAR_POLICES_LIST: (state) => {
      state.polices = [];
    },
    CLEAR_CITIES_LIST: (state) => {
      state.cities = [];
    },
    SET_HOUSE_LIST: (state, houses) => {
      //   console.log(houses);
      state.houses = houses;
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_CRIME_LIST: (state, crime) => {
      state.crime = crime;
    },
    SET_CRIMES_LIST: (state, crimes) => {
      state.crimes = crimes;
    },
    SET_TOLIST_TRUE: (state) => {
      state.tolist = true;
    },
    SET_TOLIST_FALSE: (state) => {
      state.tolist = false;
    },
    SET_DELIVERY_LIST: (state, deliveries) => {
      console.log("deliveries");
      console.log(deliveries);
      state.deliveries = deliveries["safeOpenBox"]["row"];
    },
    SET_CCTV_LIST: (state, cctvs) => {
      console.log("cctvs");
      console.log(cctvs);
      state.cctvs = cctvs["safeOpenCCTV"]["row"];
    },
    SET_DISPLAYDELIVERIES_FALSE: (state) => {
      state.displayDeliveries = false;
    },
    SET_DISPLAYDELIVERIES_TRUE: (state) => {
      console.log("deliveries became true");
      state.displayDeliveries = true;
    },
    SET_CCTVS_FALSE: (state) => {
      console.log("cctvs became false");
      state.displayCctvs = false;
    },
    SET_CCTVS_TRUE: (state) => {
      console.log("cctvs became true");
      state.displayCctvs = true;
    },
    SET_POLICE_FALSE: (state) => {
      state.displayPolice = false;
    },
    SET_POLICE_TRUE: (state) => {
      state.displayPolice = true;
    },
  },

  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      const params = {
        sido: sidoCode,
      };
      gugunList(
        params,
        ({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    getDong: ({ commit }, gugunCode) => {
      const params = {
        gugun: gugunCode,
      };
      dongList(
        params,
        ({ data }) => {
          // console.log(commit, response);
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    getHouseList: ({ commit }, dongCode) => {
      // vue cli enviroment variables 검색
      //.env.local file 생성.
      // 반드시 VUE_APP으로 시작해야 한다.
      // const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
      //   const SERVICE_KEY =
      //     "9Xo0vlglWcOBGUDxH8PPbuKnlBwbWU6aO7%2Bk3FV4baF9GXok1yxIEF%2BIwr2%2B%2F%2F4oVLT8bekKU%2Bk9ztkJO0wsBw%3D%3D";
      const params = {
        dong: dongCode,
      };
      houseList(
        params,
        ({ data }) => {
          //console.log(data);

          commit("SET_HOUSE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    detailHouse: ({ commit }, house) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
    getPosList: ({ commit }, infos) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_POSITIONS_LIST", infos);
    },
    getCrime: ({ commit }, gugunCode) => {
      const params = {
        gugun: gugunCode,
      };
      crimeList(
        params,
        ({ data }) => {
          // console.log("크라임리스트 실행", data);
          commit("SET_CRIME_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getCrimes: ({ commit }) => {
      crimesList(
        ({ data }) => {
          // console.log("크라임리스트 실행", data);
          commit("SET_CRIMES_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDeliveries: ({ commit }) => {
      safeDeliveryList(
        ({ data }) => {
          commit("SET_DELIVERY_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    getCctvList: ({ commit }, gugunName) => {
      cctvList(
        gugunName,
        ({ data }) => {
          commit("SET_CCTV_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default houseStore;
