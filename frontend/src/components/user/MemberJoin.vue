<template>
  <b-row class="mb-1">
    <div class="text-center">
      <h1 class="font-weight-bold">회원가입</h1>
      <hr class="divider" />
    </div>
    <b-col />
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          v-if="IdChecking == 'success'"
          id="userid-group"
          label="아이디:"
          label-for="userid"
        >
          <b-form-input
            id="userid"
            type="text"
            required
            placeholder="아이디 입력..."
            v-model="user.userid"
          ></b-form-input>
          <p class="description" style="color: blue">
            사용 가능한 아이디입니다.
          </p>
        </b-form-group>
        <b-form-group
          v-else
          id="userid-group"
          label="아이디:"
          label-for="userid"
        >
          <b-form-input
            id="userid"
            type="text"
            required
            placeholder="아이디 입력..."
            v-model="user.userid"
          ></b-form-input>
          <p class="description" style="color: red">
            사용 불가능한 아이디입니다.
          </p>
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
          v-if="IdChecking == 'success' && PwChecking == 'success'"
          type="submit"
          variant="info"
          class="btn btn-primary"
        >
          회원가입
        </button>
        <button v-else disabled variant="info" class="btn btn-primary">
          회원가입
        </button>

        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
    <b-col />
  </b-row>
</template>

<script>
import { mapActions } from "vuex";
import http from "@/util/http-common.js";
export default {
  name: "MemberJoin",
  data() {
    return {
      user: {
        userid: "",
        username: "",
        userpwd: "",
        userpwdcheck: "",
        email: "",
      },
      IdCheck: "",
      PwCheck: "empty",
    };
  },
  computed: {
    IdChecking: function () {
      this.checkid();
      return this.IdCheck;
    },
    PwChecking: function () {
      this.checkPw();
      console.log(this.PwCheck);
      return this.PwCheck;
    },
  },
  created() {
    this.checkid();
  },
  methods: {
    ...mapActions("memberStore", ["setUserSignUp"]),
    async onSubmit() {
      await this.setUserSignUp(this.user);
      alert("회원가입이 정상적으로 처리되었습니다.");
      this.$router.push({ name: "SignIn" });
    },
    onReset() {},
    checkid() {
      http.get(`/user/${this.user.userid}`).then((response) => {
        this.IdCheck = response.data;
      });
    },
    checkPw() {
      if (this.user.userpwd != "" && this.user.userpwdcheck != "") {
        if (this.user.userpwd == this.user.userpwdcheck) {
          this.PwCheck = "success";
        } else {
          this.PwCheck = "fail";
        }
      } else {
        this.PwCheck == "empty";
      }
    },
  },
};
</script>

<style scoped>
.description {
  font-size: 10pt;
}
</style>
