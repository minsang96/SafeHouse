<template>
  <b-row class="mb-1">
    <div class="text-center">
      <h1 class="font-weight-bold">마이 페이지</h1>
      <hr class="divider" />
    </div>
    <b-col style="text-align: left">
      <h3 style="font-weight: bold">내 정보</h3>
      <b-form @submit="onSubmit">
        <b-form-group id="userid-group" label="아이디:" label-for="userid">
          <b-form-input
            id="userid"
            type="text"
            required
            placeholder="아이디 입력..."
            v-model="user.userid"
            disabled
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label="비밀번호:"
          label-for="userpwd"
          description="비밀번호를 입력하세요."
        >
          <b-form-input
            type="password"
            id="userpwd"
            required
            placeholder="비밀번호 입력...."
            v-model="user.userpwd"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          v-if="PwChecking == 'empty'"
          label="비밀번호 확인:"
          label-for="userpwdcheck"
          description="비밀번호를 다시 입력하세요."
        >
          <b-form-input
            type="password"
            id="userpwdcheck"
            required
            placeholder="비밀번호 입력...."
            v-model="user.userpwdcheck"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          v-else-if="PwChecking == 'success'"
          label="비밀번호 확인:"
          label-for="userpwdcheck"
        >
          <b-form-input
            type="password"
            id="userpwdcheck"
            required
            placeholder="비밀번호 입력...."
            v-model="user.userpwdcheck"
          ></b-form-input>
          <p class="description" style="color: blue">확인되었습니다.</p>
        </b-form-group>

        <b-form-group
          v-else
          class="wrong"
          label="비밀번호 확인:"
          label-for="userpwdcheck"
        >
          <b-form-input
            type="password"
            id="userpwdcheck"
            required
            placeholder="비밀번호 입력...."
            v-model="user.userpwdcheck"
          ></b-form-input>
          <p class="description" style="color: red">
            비밀번호를 다시 확인해주세요.
          </p>
        </b-form-group>

        <b-form-group
          id="name-group"
          label="이름:"
          label-for="name"
          description="이름을 입력하세요."
        >
          <b-form-input
            id="name"
            type="text"
            required
            placeholder="이름 입력..."
            v-model="user.username"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="email-group"
          label="이메일:"
          label-for="email"
          description="이메일을 입력하세요."
        >
          <b-form-input
            id="email"
            type="text"
            required
            placeholder="이메일 입력..."
            v-model="user.email"
          ></b-form-input>
        </b-form-group>

        <button
          v-if="PwChecking == 'success'"
          type="submit"
          class="btn-primary btn-xl"
        >
          정보 수정
        </button>
        <button v-else type="submit" class="btn-primary btn-xl" disabled>
          정보 수정
        </button>
      </b-form>
    </b-col>
    <b-col style="text-align: left">
      <h3 style="font-weight: bold">위시리스트</h3>
      <b-list-group>
        <b-list-group-item
          v-for="(wish, index) in wishlist"
          :key="index"
          :sidoCode="wishlist[index].dongCode.substring(0, 2)"
          :gugunCode="wishlist[index].dongCode.substring(0, 5)"
          :dongCode="wishlist[index].dongCode"
          @click="moveMapPage"
          >{{ wishlist[index].aptName }}</b-list-group-item
        >
      </b-list-group>
    </b-col>
  </b-row>
</template>
<script>
import { mapState } from "vuex";
import http from "@/util/http-common";

export default {
  name: "MemberMyPage",
  data() {
    return {
      user: {
        userid: "",
        username: "",
        userpwd: "",
        userpwdcheck: "",
        email: "",
      },
      PwCheck: "empty",
    };
  },
  computed: {
    ...mapState("memberStore", ["userInfo", "wishlist"]),
    PwChecking: function () {
      this.checkPw();
      console.log(this.PwCheck);
      return this.PwCheck;
    },
  },
  created() {
    this.user.userid = this.userInfo.userid;
    this.user.username = this.userInfo.username;
    this.user.email = this.userInfo.email;
  },
  methods: {
    onSubmit() {
      http
        .put(`/user/modify`, {
          userid: this.user.userid,
          userpwd: this.user.userpwd,
          username: this.user.username,
          email: this.user.email,
        })
        .then(() => {
          alert("회원정보가 정상적으로 수정되었습니다.");
          this.user.userid = this.userInfo.userid;
          this.user.username = this.userInfo.username;
          this.user.email = this.userInfo.email;
        });
      this.$router.push({ name: "Home" });
    },
    checkPw() {
      if (this.user.userpwd != "" && this.user.userpwdcheck != "") {
        if (this.user.userpwd == this.user.userpwdcheck) {
          console.log("1번");
          this.PwCheck = "success";
        } else {
          this.PwCheck = "fail";
        }
      } else {
        this.PwCheck == "empty";
      }
    },
    moveMapPage() {
      this.$router.push({ name: "House" });
    },
  },
};
</script>

<style scoped>
.description {
  font-size: 10pt;
}

.btn-primary,
.btn-primary:hover,
.btn-primary:active,
.btn-primary:visited {
  background-color: #f4623a !important;
}
</style>
