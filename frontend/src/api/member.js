import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function signup(user, success, fail) {
  await api
    .post(`/user/signup`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function modify(user, success, fail) {
  await api.put(`/user/modify`, JSON.stringify(user)).then(success).catch(fail);
}

async function getWishList(userid, success, fail) {
  await api.get(`/user/wishlist/${userid}`).then(success).catch(fail);
}
async function addWishList(apt, success, fail) {
  await api
    .post(`/user/wishlist/`, JSON.stringify(apt))
    .then(success)
    .catch(fail);
}

async function deleteWishList(wishlist_no, success, fail) {
  await api.delete(`/user/wishlist/${wishlist_no}`).then(success).catch(fail);
}

// function logout(success, fail)

export {
  login,
  findById,
  signup,
  modify,
  getWishList,
  addWishList,
  deleteWishList,
};
