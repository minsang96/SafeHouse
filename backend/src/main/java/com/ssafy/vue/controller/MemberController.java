package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.WishListDto;
import com.ssafy.vue.model.WishListParameterDto;
import com.ssafy.vue.model.service.JwtServiceImpl;
import com.ssafy.vue.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api("사용자 컨트롤러  API V1")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
			//access-token을 만든다
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (jwtService.isUsable(request.getHeader("access-token"))) { //헤더에 담은 것
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원가입", notes = "회원가입", response = String.class)
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody @ApiParam(value = "회원가입시 필요한 정보", required = true) MemberDto memberDto) throws Exception {
		logger.info("signup - 호출");
		try {
			memberService.signup(memberDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "회원가입 시 아아디 유효성 검사", notes = "회원가입 시 아아디 유효성 검사", response = String.class)
	@GetMapping("/{userid}")
	public ResponseEntity<String> checkid(@PathVariable("userid") @ApiParam(value = "유저 아이디", required = true) String userid) throws Exception {
		logger.info("checkid - 호출");
		System.out.println(userid);
		if (memberService.userInfo(userid) == null) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);		
	}

	@ApiOperation(value = "회원정보 수정", notes = "회원정보 수정", response = String.class)
	@PutMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody @ApiParam(value = "회원정보 수정시 필요한 정보", required = true) MemberDto memberDto) throws Exception {
		logger.info("modify - 호출");
		try {
			memberService.modify(memberDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "찜한 아파트 목록", notes = "찜한 아파트 목록의 정보를 반환한다.", response = List.class)
	@GetMapping("/wishlist/{userid}")
	public ResponseEntity<List<WishListDto>> getWishList(@PathVariable("userid") @ApiParam(value = "찜한 목록을 확인할 유저의 ID", required = true) String userid) throws Exception {
		logger.info("getWishList - 호출");
		return new ResponseEntity<List<WishListDto>>(memberService.getWishList(userid), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트 찜 목록에 등록", notes = "해당 아파트의 코드를 찜 목록에 등록한다.", response = String.class)
	@PostMapping("/wishlist")
	public ResponseEntity<String> addWishList(@RequestBody @ApiParam(value = "찜 할 아파트 매물의 아파트 코드", required = true) WishListParameterDto wishListParameterDto) throws Exception {
		logger.info("addWishList - 호출");
		if (memberService.addWishList(wishListParameterDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "아파트 찜 목록 삭제", notes = "찜 목록에서 해당 아파트를 삭제한다. 성공 시 success, 실패 시 fail 반환", response = String.class)
	@DeleteMapping("/wishlist/{wishlist_no}")
	public ResponseEntity<String> deleteWishList(@PathVariable @ApiParam(value = "삭제할 아파트 코드", required = true) int wishlist_no) throws Exception {
		logger.info("deleteWishList - 호출");
		if (memberService.deleteWishList(wishlist_no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
