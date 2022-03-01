<template>
  <div id="map"></div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const houseStore = "houseStore";

export default {
  data() {
    return {
      markers: [],
      overlays: [],
      cctvmarkers: [],
      deliverymarkers: [],
      policemarkers: [],
    };
  },
  computed: {
    ...mapState(houseStore, [
      "positions",
      "houses",
      "house",
      "polices",
      "deliveries",
      "cctvs",
      "displayDeliveries",
      "displayCctvs",
      "displayPolice",
      "tolist",
    ]),
  },
  watch: {
    positions: function () {
      this.displayMarkers(this.map);
    },
    house: function () {
      this.focusOn(this.house);
      /* if (this.displayDeliveries === true) {
        this.showDeliveries(this.map);
      }
      if (this.displayCctvs === true) {
        this.showCctvs(this.map);
      }*/
      if (this.cctvmarkers.length > 0) {
        this.deleteCctvMarkers();
        this.cctvmarkers = [];
      }

      if (this.displayPolice === true) {
        this.searchPolice(this.map);
      } else {
        if (this.policemarkers.length > 0) {
          this.deletePoliceMarkers();
          this.policemarkers = [];
        }
      }

      console.log(this.house);
      this.CLEAR_CITIES_LIST();
      this.getCctvList(this.house.gugunName);
    },
    displayDeliveries: function () {
      console.log("delivery change");
      if (this.displayDeliveries === true) {
        console.log("delivery true");
        const gugun = this.house.gugunName;
        this.showDeliveries(this.map, gugun);
      } else {
        if (this.deliverymarkers.length > 0) {
          this.deleteDeliveryMarkers();
          this.deliverymarkers = [];
        }
      }
    },
    cctvs: function () {
      console.log("cctv change");
      if (this.displayCctvs === true) {
        console.log("cctv true");
        console.log(this.house.gugun);
        this.showCctvs(this.map);
      } else {
        if (this.cctvmarkers.length > 0) {
          this.deleteCctvMarkers();
          this.cctvmarkers = [];
        }
      }
    },
    displayCctvs: function () {
      console.log("cctv change");
      if (this.displayCctvs === true) {
        console.log("cctv true");
        console.log(this.house.gugun);
        this.showCctvs(this.map);
      } else {
        if (this.cctvmarkers.length > 0) {
          this.deleteCctvMarkers();
          this.cctvmarkers = [];
        }
      }
    },

    displayPolice: function () {
      if (this.displayPolice === true) {
        this.searchPolice(this.map);
      } else {
        if (this.policemarkers.length > 0) {
          this.deletePoliceMarkers();
        }
      }
    },
  },
  mounted() {
    console.log("kakaomap mounted");
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8a03a0a656581b3616528bd046925d99&libraries=services";
      document.head.appendChild(script);
      // this.searchPolice();
    }
  },
  methods: {
    ...mapMutations(houseStore, ["CLEAR_CITIES_LIST"]),
    ...mapActions(houseStore, ["detailHouse", "getDeliveries", "getCctvList"]),
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };
      this.map = new kakao.maps.Map(container, options);
    },
    focusOn(house) {
      var bounds = new kakao.maps.LatLngBounds();
      var platlng = new kakao.maps.LatLng(house.lat, house.lng);
      bounds.extend(platlng);
      this.map.setBounds(bounds);
    },
    displayMarkers(map) {
      var bounds = new kakao.maps.LatLngBounds();
      this.houses.forEach((house) => {
        var platlng = new kakao.maps.LatLng(house.lat, house.lng);
        var marker = new kakao.maps.Marker({
          map: this.map,
          position: platlng,
        });

        bounds.extend(platlng);
        this.map.setBounds(bounds);

        this.markers.push(marker);

        var intprice = parseInt(house.recentPrice.replace(",", "")) * 10000;
        var hprice = intprice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        var content = document.createElement("div");
        content.className = "wrap";

        var info = document.createElement("div");
        info.className = "info";

        var aptname = document.createElement("div");
        aptname.className = "aptName";
        aptname.appendChild(document.createTextNode(house.aptName));

        var close = document.createElement("div");
        close.className = "close";

        aptname.append(close);

        var body = document.createElement("div");
        body.className = "body";

        var img = new Image();
        img.className = "img";
        img.src =
          "https://newsimg.hankookilbo.com/cms/articlerelease/2021/03/28/717da810-e427-424e-badd-80b227d93bbd.jpg";
        img.width = "73";
        img.height = "70";

        var desc = document.createElement("div");
        desc.className = "desc";

        var addrs = document.createElement("div");
        addrs.className = "address";
        addrs.appendChild(
          document.createTextNode(
            house.sidoName + " " + house.gugunName + " " + house.dongName
          )
        );

        var buildYear = document.createElement("div");
        buildYear.className = "buildYear";
        buildYear.appendChild(
          document.createTextNode("건축연도:" + house.buildYear)
        );

        var housePrice = document.createElement("div");
        housePrice.className = "housePrice";
        housePrice.appendChild(
          document.createTextNode("매매가:" + hprice + "원")
        );

        var link = document.createElement("div");
        var linka = document.createElement("a");
        //  linka.appendChild(document.createTextNode("상세보기"));
        //  linka.setAttribute("href", "https://www.kakaocorp.com/main");
        //  linka.setAttribute("target", "_blank");
        linka.className = "link";
        link.appendChild(linka);

        desc.appendChild(addrs);
        desc.appendChild(buildYear);
        desc.appendChild(housePrice);
        desc.appendChild(link);
        body.appendChild(img);
        body.appendChild(desc);
        info.appendChild(aptname);
        info.appendChild(body);

        close.onclick = function () {
          overlay.setMap(null);
        };
        //  linka.onclick = () => {
        //    this.detailHouse(house);
        //  };

        body.onclick = () => {
          this.detailHouse(house);
        };

        content.appendChild(info);

        let overlay = new kakao.maps.CustomOverlay({
          position: platlng,
          content: content,
        });

        this.overlays.push(overlay);
        // console.log(content);

        kakao.maps.event.addListener(marker, "click", function () {
          overlay.setMap(map);
        });
      });
    },
    searchPolice(map) {
      const ps = new kakao.maps.services.Places();

      ps.keywordSearch(
        "경찰서",
        (data, status) => {
          // console.log(data);
          console.log(status);
          for (var i = 0; i < data.length; i++) {
            this.showPolice(data[i], map);
            this.polices.push(data[i]);
          }
        },
        {
          location: map.getCenter(),
          sort: kakao.maps.services.SortBy.DISTANCE,
          radius: 1000,
        }
      );
    },
    showPolice(place, map) {
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      // 마커를 생성하고 지도에 표시합니다
      const marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x),
        image: new kakao.maps.MarkerImage(
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
          new kakao.maps.Size(25, 35)
        ),
      });
      this.policemarkers.push(marker);
      // 마커에 클릭이벤트를 등록합니다
      kakao.maps.event.addListener(marker, "click", function () {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        if (infowindow.getMap()) infowindow.close();
        else {
          infowindow.setContent(
            '<div style="padding:5px;font-size:12px;">' +
              place.place_name +
              "</div>"
          );
          infowindow.open(map, marker);
        }
      });
    },

    deletePoliceMarkers() {
      this.policemarkers.forEach((marker) => {
        marker.setMap(null);
      });
    },

    showDeliveries(map) {
      this.getDeliveries();
      this.deliveries.forEach((elem) => {
        var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
        var platlng = new kakao.maps.LatLng(elem.WGSXPT, elem.WGSYPT);
        var marker = new kakao.maps.Marker({
          map: map,
          position: platlng,
          image: new kakao.maps.MarkerImage(
            "https://www.pikpng.com/pngl/m/36-369401_location-marker-icon-google-maps-pointer-elsavadorla-google.png",
            new kakao.maps.Size(25, 35)
          ),
        });
        this.deliverymarkers.push(marker);
        kakao.maps.event.addListener(marker, "click", function () {
          // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
          if (infowindow.getMap()) infowindow.close();
          else {
            infowindow.setContent(
              '<div style="padding:5px;font-size:12px;">' +
                elem.ANSIMINM +
                "</div>"
            );
            infowindow.open(map, marker);
          }
        });
      });
    },

    deleteDeliveryMarkers() {
      this.deliverymarkers.forEach((marker) => {
        marker.setMap(null);
      });
    },

    showCctvs(map) {
      console.log("CCTV리스트", this.cctvs);
      this.cctvs.forEach((elem) => {
        var platlng = new kakao.maps.LatLng(elem.WGSXPT, elem.WGSYPT);
        var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
        var marker = new kakao.maps.Marker({
          map: map,
          position: platlng,
          image: new kakao.maps.MarkerImage(
            "https://cdn.iconscout.com/icon/premium/png-256-thumb/cctv-camera-132-1086994.png",
            new kakao.maps.Size(25, 35)
          ),
        });
        this.cctvmarkers.push(marker);
        kakao.maps.event.addListener(marker, "click", function () {
          // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
          if (infowindow.getMap()) infowindow.close();
          else {
            infowindow.setContent(
              '<div style="padding:5px;font-size:12px;">' +
                elem.CCTVUSE +
                "</div>"
            );
            infowindow.open(map, marker);
          }
        });
      });
    },

    deleteCctvMarkers() {
      this.cctvmarkers.forEach((marker) => {
        marker.setMap(null);
      });
    },
  },
};
</script>

<style>
#map {
  width: 1500px;
  height: 800px;
}

.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .aptName {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .dongName {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>
