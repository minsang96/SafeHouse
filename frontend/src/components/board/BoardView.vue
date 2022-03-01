<template>
  <b-container class="bv-example-row mt-3">
    <div class="text-center">
      <h1 class="font-weight-bold">글 보기</h1>
      <hr class="divider" />
    </div>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="listArticle">목록</b-button>
      </b-col>
      <b-col class="text-right" v-show="isShow">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
          >글수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="deleteArticle"
          >글삭제</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <!-- <b-col>
        <b-card
          :header-html="`<h3>${article.articleno}.
          ${article.subject} [${article.hit}]</h3><div><h6>작성자 : ${article.userid}</div><div>${article.regtime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col> -->
      <b-jumbotron
        class="m-3"
        style="border: black; background-color: white"
        border-variant="dark"
      >
        <div class="text-left mt-3">
          <h3 class="font-weight-bold">
            {{ article.subject }} [{{ article.hit }}]
          </h3>
          <h5 class="font-weight-bold text-right">
            작성자 : {{ article.userid }}
          </h5>
          <h5 class="font-weight-bold text-right">
            {{ article.regtime }}
          </h5>
          <hr class="divider" />
          <div border-variant="dark" style="background-color: white">
            <div v-html="message"></div>
          </div>
        </div>
      </b-jumbotron>
    </b-row>
    <b-row class="mb-1"
      ><h5 style="text-align: left; font-weight: bold">
        댓글({{ comments.length }})
      </h5></b-row
    >
    <b-row>
      <comment-write :articleno="articleno" :checkUserInfo="checkUserInfo" />
      <comment-write
        v-if="isModifyShow && modifyComment != null"
        :modifyComment="this.modifyComment"
        @modify-comment-cancel="onModifyCommentCancel"
      />
      <comment
        v-for="(comment, index) in comments"
        :key="index"
        :comment="comment"
        :userInfoId="checkUserInfo.userid"
        :isCommentShow="check(comment)"
        @modify-comment="onModifyComment"
      />
    </b-row>
  </b-container>
</template>

<script>
// import moment from "moment";
import http from "@/util/http-common";
import CommentWrite from "@/components/comment/CommentWrite.vue";
import Comment from "@/components/comment/Comment.vue";
import { mapGetters } from "vuex";
import store from "@/store/index.js";
export default {
  components: {
    CommentWrite,
    Comment,
  },
  data() {
    return {
      article: {},
      articleno: "",
      checkUserInfo: Object,
      isModifyShow: false,
      modifyComment: Object,
      isShow: true,
      isCommentShow: true,
    };
  },
  computed: {
    ...mapGetters("commentStore", ["comments"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
      if (this.article.userid === this.checkUserInfo.userid) this.isShow = true;
      else this.isShow = false;
    });
    http.get(`/comment/${this.$route.params.articleno}`).then(({ data }) => {
      this.comments = data;
    });
    this.articleno = this.$route.params.articleno;
    this.$store.dispatch("commentStore/getComments", this.articleno);
    this.checkUserInfo = store.getters["memberStore/checkUserInfo"];
  },
  methods: {
    check(param) {
      if (param.user_id === this.checkUserInfo.userid) return true;
      else return false;
    },
    listArticle() {
      this.$router.push({ name: "BoardList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "BoardUpdate",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "BoardDelete",
          params: { articleno: this.article.articleno },
        });
      }
    },
    onModifyComment(comment) {
      this.isModifyShow = true;
      this.modifyComment = comment;
    },
    onModifyCommentCancel(isShow) {
      this.isModifyShow = isShow;
    },
  },
};
</script>

<style>
.btn-outline-primary,
.btn-outline-primary:hover,
.btn-outline-primary:active,
.btn-outline-primary:visited {
  color: #fff;
  background-color: #f4623a;
  border-color: #f4623a;
}
</style>
