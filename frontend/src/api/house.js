import { apiInstance } from "./index.js";

const api = apiInstance();
//const house = houseInstance();

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

function gugunList(params, success, fail) {
  api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
}

function dongList(params, success, fail) {
  api.get(`/map/dong`, { params: params }).then(success).catch(fail);
}

function houseList(params, success, fail) {
  api.get(`/map/apt`, { params: params }).then(success).catch(fail);
}

function crimeList(params, success, fail) {
  api.get(`/map/crime`, { params: params }).then(success).catch(fail);
}

function crimesList(success, fail) {
  api.get(`/map/totalcrimes`).then(success).catch(fail);
}

function safeDeliveryList(success, fail) {
  const SERVICE_URL =
    "http://openapi.seoul.go.kr:8088/6666656f796c737936377858597950/json/safeOpenBox/1/500";
  api.get(SERVICE_URL).then(success).catch(fail);
}

function cctvList(param, success, fail) {
  const SERVICE_URL =
    "http://openapi.seoul.go.kr:8088/6666656f796c737936377858597950/json/safeOpenCCTV/1/100/" +
    param;
  api.get(SERVICE_URL).then(success).catch(fail);
}
export {
  sidoList,
  gugunList,
  houseList,
  dongList,
  crimeList,
  crimesList,
  safeDeliveryList,
  cctvList,
};
