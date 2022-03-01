package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.WishListDto;
import com.ssafy.vue.model.WishListParameterDto;

@Mapper
public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public boolean signup(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public boolean modify(MemberDto memberDto) throws SQLException;
	public List<WishListDto> getWishList(String userid) throws SQLException;
	public boolean addWishList(WishListParameterDto wishListParameterDto) throws SQLException;
	public boolean deleteWishList(int wishlist_no) throws SQLException;
}
