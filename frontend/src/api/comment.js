import { apiInstance } from "./index.js";

const api = apiInstance();

function listComment(articleno, success, fail) {
  api.get(`/comment/${articleno}`).then(success).catch(fail);
}

function writeComment(article, success, fail) {
  api.post(`/comment`, JSON.stringify(article)).then(success).catch(fail);
}

function modifyComment(article, success, fail) {
  api.put(`/comment`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteComment(articleno, success, fail) {
  api.delete(`/comment/${articleno}`).then(success).catch(fail);
}

export { listComment, writeComment, modifyComment, deleteComment };
