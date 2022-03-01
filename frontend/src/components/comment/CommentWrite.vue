<template>
  <div class="register">
    <div v-if="modifyComment != null" class="regist_form">
      <textarea
        name="comment"
        id="comment"
        v-model="modifyComment.comment"
        cols="400"
        rows="2"
        style="resize: none"
      ></textarea>
      <button class="btn-primary small" @click="updateCommentCancel">
        취소
      </button>
      <button class="btn-primary small" @click="updateComment">수정</button>
    </div>
    <div v-else class="regist_form">
      <textarea
        name="comment"
        id="comment"
        v-model="comment"
        cols="500"
        rows="2"
        style="resize: none"
      ></textarea>
      <button class="btn-primary small" @click="registComment">등록</button>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import { mapGetters } from "vuex";

export default {
  props: {
    articleno: String,
    modifyComment: Object,
    checkUserInfo: Object,
  },
  data() {
    return {
      comment: "",
    };
  },
  computed: {
    ...mapGetters(["commentStore", "getComments"]),
  },
  methods: {
    registComment() {
      http
        .post("/comment/", {
          user_name: this.checkUserInfo.username,
          comment: this.comment,
          articleno: this.articleno,
          user_id: this.checkUserInfo.userid,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.comment = "";
          this.$store.dispatch("commentStore/getComments", this.articleno);
        });
    },
    updateComment() {
      http
        .put(`/comment`, {
          comment_no: this.modifyComment.comment_no,
          comment: this.modifyComment.comment,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.$store.dispatch(
            "commentStore/getComments",
            this.modifyComment.articleno
          );
          this.updateCommentCancel();
        });
    },
    updateCommentCancel() {
      this.$emit("modify-comment-cancel", false);
    },
  },
};
</script>

<style scoped>
textarea {
  width: 90%;
  font-size: large;
}
button {
  float: right;
}
button.small {
  padding: 0.4rem 1.6rem;
  font-size: 0.85rem;
  font-weight: 700;
  text-transform: uppercase;
  border: none;
  border-radius: 10rem;
}
</style>
