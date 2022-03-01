<template>
  <b-container class="bv-example-row mt-3">
    <div class="text-center">
      <h1 class="font-weight-bold">QnA</h1>
      <h7 class="font-weight-bold"
        >사이트 이용 문의나 기타 문의 사항들을 글로 남겨주세요.</h7
      >
      <hr class="divider" />
    </div>
    <br />
    <b-row>
      <b-col>
        <form id="searchForm" class="form-inline">
          <input type="hidden" name="currentPage" id="currentPage" />
          <label class="font-weight-bold">검색 컬럼 : </label>
          <select
            name="key"
            id="key"
            v-model="key"
            class="form-control ml-2 mr-2"
          >
            <option value="none">전체</option>
            <option value="subject">제목</option>
            <option value="userid">작성자</option>
            <option value="content">내용</option>
          </select>
          <label class="font-weight-bold">검색어 : </label>
          <input
            type="text"
            name="word"
            id="word"
            v-model="word"
            class="form-control ml-2"
          />
          <div class="flex-grow-1 text-right">
            <input
              type="button"
              value="검색"
              class="btn-primary btn-xl"
              id="btnSearch"
              @click="search"
            />
          </div>
        </form>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col v-if="articles.length">
        <b-table-simple hover responsive>
          <b-thead style="background-color: #f4623a; color: white">
            <b-tr>
              <b-th>글번호</b-th>
              <b-th>제목</b-th>
              <b-th>조회수</b-th>
              <b-th>작성자</b-th>
              <b-th>작성일</b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <board-list-row
              v-for="(article, index) in articles"
              :key="index"
              v-bind="article"
            />
          </tbody>
        </b-table-simple>
      </b-col>
      <b-col v-else class="text-center">해당 게시글이 없습니다.</b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-right">
        <button class="btn-primary btn-xl" @click="moveWrite()">글쓰기</button>
      </b-col>
    </b-row>
    <div>
      <b-button class="mr-3" variant="outline-primary" @click="before()"
        >이전 페이지</b-button
      >
      <b-button
        v-for="i in Math.ceil(size / 10)"
        :key="i"
        class="mr-3"
        variant="outline-primary"
        @click="movePage(i)"
        >{{ i }}</b-button
      >
      <b-button variant="outline-primary" @click="next()">다음 페이지</b-button>
    </div>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
import BoardListRow from "@/components/board/child/BoardListRow";
import { mapState } from "vuex";

export default {
  name: "BoardList",
  components: {
    BoardListRow,
  },
  computed: {
    ...mapState[("key", "word")],
  },
  data() {
    return {
      articles: [],
      size: 1,
      key: "전체",
      word: null,
      params: {
        key: null,
        pg: 1,
        spp: 10,
        start: 1,
        word: null,
      },
    };
  },
  created() {
    this.search();
  },
  methods: {
    moveWrite() {
      this.pageReset();
      this.$router.push({ name: "BoardWrite" });
    },
    next() {
      const params = this.params;
      if (this.articles.length >= this.params.spp) {
        this.params.pg++;
        this.params.key = this.key;
        this.params.word = this.word;
        http.get(`/board`, { params }).then(({ data }) => {
          this.articles = data;
          // console.log(this.params.key);
          // console.log(this.params.word);
          // console.log(this.params.pg);
        });
      }
    },
    before() {
      const params = this.params;
      if (this.params.pg > 1) {
        this.params.pg--;
        this.params.key = this.key;
        this.params.word = this.word;
        http.get(`/board`, { params }).then(({ data }) => {
          this.articles = data;
          // console.log(this.$store.state.key);
          // console.log(this.$store.state.word);
        });
      }
    },
    pageReset() {
      this.params.pg = 1;
      this.key = null;
      this.word = null;
      // console.log(this.params.pg);
    },
    search() {
      console.log("실행");
      const params = this.params;
      if (this.key) {
        this.params.pg = 1;
        this.params.key = this.key;
        this.params.word = this.word;
      } else {
        this.params.word = this.word;
      }
      http.get(`/board`, { params }).then(({ data }) => {
        this.articles = data;
        this.size = this.articles[0].articleno;
        console.log(this.size);
        // console.log(data);
      });
    },
    movePage(i) {
      const params = this.params;
      this.params.pg = i;
      this.params.key = this.key;
      this.params.word = this.word;
      http.get(`/board`, { params }).then(({ data }) => {
        this.articles = data;
        // console.log(this.params.key);
        // console.log(this.params.word);
        // console.log(this.params.pg);
      });
    },
  },
};
</script>

<style>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}

.btn-primary,
.btn-primary:hover,
.btn-primary:active,
.btn-primary:visited {
  background-color: #f4623a !important;
}
.btn-outline-primary,
.btn-outline-primary:active,
.btn-outline-primary:visited {
  color: #f4623a;

  border-color: #f4623a;
}

.btn-outline-primary:hover {
  color: #fff;
  background-color: #f4623a;
  border-color: #f4623a;
}
</style>
