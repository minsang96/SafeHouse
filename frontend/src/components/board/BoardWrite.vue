<template>
  <b-container class="bv-example-row mt-3">
    <div class="text-center">
      <h1 class="font-weight-bold">글 작성</h1>
      <hr class="divider" />
    </div>
    <board-write-form type="register" />
  </b-container>
</template>

<script>
import BoardWriteForm from "./child/BoardWriteForm.vue";

export default {
  name: "BoardWrite",
  components: {
    BoardWriteForm,
  },
};
</script>

<style></style>
