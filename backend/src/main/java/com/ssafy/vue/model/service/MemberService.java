package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.WishListDto;
import com.ssafy.vue.model.WishListParameterDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public boolean signup(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public boolean modify(MemberDto memberDto) throws Exception;
	public List<WishListDto> getWishList(String userid) throws Exception;
	public boolean addWishList(WishListParameterDto wishListParameterDto) throws Exception;
	public boolean deleteWishList(int wishlist_noo) throws Exception;
}
