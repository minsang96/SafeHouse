<template>
  <b-row>
    <div class="comment">
      <div class="head">
        {{ comment.user_name }} ({{ getFormatDate(comment.comment_time) }})
      </div>
      <div class="content" v-html="enterToBr(comment.comment)"></div>
      <div class="cbtn" v-show="isCommentShow">
        <label @click="modifyCommentView">수정</label> |
        <label @click="deleteComment">삭제</label>
      </div>
    </div>
  </b-row>
</template>

<script>
import http from "@/util/http-common.js";
import moment from "moment";

export default {
  props: {
    comment: Object,
    userInfoId: String,
    isCommentShow: Boolean,
  },
  methods: {
    modifyCommentView() {
      this.$emit("modify-comment", {
        comment_no: this.comment.comment_no,
        comment: this.comment.comment,
        articleno: this.comment.articleno,
      });
    },
    deleteComment() {
      if (confirm("삭제할까요?")) {
        http.delete(`/comment/${this.comment.comment_no}`).then(({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          this.$store.dispatch(
            "commentStore/getComments",
            this.comment.articleno
          );
        });
      }
    },
    getFormatDate(regtime) {
      return moment(regtime).format("YYYY-MM-DD HH:mm:ss");
    },
    numberWithCommas(x) {
      return String(x).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    enterToBr(str) {
      // 문자열에 enter값을 <br />로 변경.(html상에서 줄바꿈 처리)
      return String(str).replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
  },
};
</script>

<style scoped>
.comment {
  text-align: left;
  border-radius: 5px;
  background-color: #d6e7fa;
  padding: 10px 20px;
  margin: 10px;
  margin-left: 30px;
  width: 100vh;
  height: auto;
}
.head {
  font-weight: bold;
  margin-bottom: 5px;
}
.content {
  padding: 5px;
}
.cbtn {
  text-align: right;
  color: steelblue;
  margin: 5px 0px;
}
</style>
