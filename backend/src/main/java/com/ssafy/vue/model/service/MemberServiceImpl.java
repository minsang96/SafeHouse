package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.WishListDto;
import com.ssafy.vue.model.WishListParameterDto;
import com.ssafy.vue.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if(memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
	}
	
	@Override
	public boolean signup(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).signup(memberDto);
	}

	@Override
	public boolean modify(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).modify(memberDto);
	}

	@Override
	public List<WishListDto> getWishList(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).getWishList(userid);
	}
	
	@Override
	public boolean addWishList(WishListParameterDto wishListParameterDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).addWishList(wishListParameterDto);
	}

	@Override
	public boolean deleteWishList(int wishlist_no) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).deleteWishList(wishlist_no);
	}

}
