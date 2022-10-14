package com.iu.home.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.iu.home.reviews.*;
import com.iu.home.util.ReviewsPager;

@Controller
@RequestMapping(value = "/kjk/member/*")
public class MemberController
{
	@Autowired
	@Qualifier("myservice")
	private MemberService memberService;

	@GetMapping(value = "login")
	public String login()
	{
		System.out.println("Login GET Test");

		return "kjk/member/login";
	}

	@PostMapping(value = "login")
	public ModelAndView login1(HttpSession session, MemberDTO memberDTO) throws Exception
	{
		ModelAndView mv = new ModelAndView();
		System.out.println("Login POST Test");
		// System.out.println(memberDTO.getUserid());
		// System.out.println(memberDTO.getPwd());
		memberDTO = memberService.getLogin(memberDTO);
		session.setAttribute("member", memberDTO);

		memberDTO = (MemberDTO) session.getAttribute("member");

		// System.out.println(memberDTO);

		String message = "로그인 실패";
		String url = "../member/login";
		if (memberDTO != null)
		{ // login succeed
			message = "로그인 성공";
			url = "../../";
		}
		mv.addObject("dto", memberDTO);
		mv.addObject("message", message);
		mv.addObject("url", url);
		mv.setViewName("kjk/common/rs");

		return mv;
	}

	@GetMapping(value = "logout")
	public String logout(HttpSession session) throws Exception
	{
		// 1. session�쓣 �냼硫몄떆�궎湲�
		session.invalidate();

		return "redirect:../../";
	}

	@GetMapping(value = "join")
	public String join() throws Exception
	{
		System.out.println("Join GET Test");

		return "kjk/member/join";
	}

	@PostMapping(value = "join")
	public ModelAndView join(MemberDTO memberDTO, ModelAndView mv) throws Exception
	{
		mv = new ModelAndView();
		System.out.println("Join POST Test");
		// System.out.println(memberDTO);
		// System.out.println(memberDTO.getUserid());
		// System.out.println(memberDTO.getUsername());
		// System.out.println(memberDTO.getGender());

		int rs = memberService.setJoin(memberDTO);
		// System.out.println(rs == 1);

		mv.addObject("join", memberDTO);
		mv.setViewName("redirect:./login");

		// return "redirect:./login";;
		return mv;
	}

	@GetMapping(value = "mypage")
	public ModelAndView mypage(HttpSession session, ReviewsDTO reviewsDTO) throws Exception
	{
		ModelAndView mv = new ModelAndView();
		System.out.println("mypage Get Test");

		MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
		memberDTO = memberService.getMyPage(memberDTO);

		reviewsDTO.setUserID(memberDTO.getUserID());
		List<ReviewsDTO> ar = memberService.reviewsList(reviewsDTO);
		// reviewsDTO = memberService.reviewsList(reviewsDTO);

		for (ReviewsDTO reviewsDTO2 : ar)
		{
			System.out.println("re ID: " + reviewsDTO2.getUserID());
			System.out.println("re title: " + reviewsDTO2.getTitle());
			System.out.println("re contents: " + reviewsDTO2.getContents());
			System.out.println(reviewsDTO2.getShopNum());
		}

		mv.addObject("reList", ar);

		mv.addObject("dto", memberDTO);

		// mv.setViewName("kjk/member/mypage");

		return mv;
	}

	// @PostMapping(value = "mypage")
	// public void mypage(ReviewsDTO reviewsDTO, HttpSession session) throws
	// Exception
	// {
	// ModelAndView mv = new ModelAndView();
	//
	// MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
	// memberDTO = memberService.getMyPage(memberDTO);
	//
	// reviewsDTO.setUserID(memberDTO.getUserID());
	// List<ReviewsDTO> ar = memberService.reviewsList(reviewsDTO);
	//
	// for (ReviewsDTO reviewsDTO2 : ar)
	// {
	// System.out.println("re Num: " + reviewsDTO2.getShopNum());
	// }
	// mv.addObject("rList", ar);
	// mv.addObject("dto", memberDTO);
	// mv.setViewName("kdy/shop/detailHTML?shopNum=" + reviewsDTO.getShopNum());
	// }

	@GetMapping(value = "adpage")
	public ModelAndView adMypage(MemberDTO memberDTO, HttpSession session) throws Exception
	{
		System.out.println("Adpage GEt");
		ModelAndView mv = new ModelAndView();
		List<MemberDTO> ar = memberService.adMyPage(memberDTO);
		// for (MemberDTO memberDTO2 : ar)
		// {
		// System.out.println(memberDTO2.getPwd());
		// System.out.println(memberDTO2.getEmail());
		// System.out.println(memberDTO2.getPhone());
		// System.out.println(memberDTO2.getUserName());
		// }
		mv.addObject("dto", ar);
		mv.setViewName("kjk/member/adpage");

		return mv;
	}

	@GetMapping(value = "update")
	public void update(HttpSession session, Model model) throws Exception
	{
		System.out.println("Update GET Page");

		MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
		memberDTO = memberService.getMyPage(memberDTO);

		System.out.println(memberDTO.getUserID());
		System.out.println(memberDTO.getPwd());
		System.out.println(memberDTO.getGender());

		model.addAttribute("dto", memberDTO);
	}

	@PostMapping(value = "update")
	public String setUpdate(MemberDTO memberDTO) throws Exception
	{
		System.out.println("update POST");
		int rs = memberService.setUpdate(memberDTO);
		System.out.println(rs == 1);

		return "redirect:./mypage?userID=" + memberDTO.getUserID();
	}

	@GetMapping(value = "delete")
	public void setDelete(HttpSession session, Model model) throws Exception
	{
		System.out.println("Delete GET Page");
		MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
		memberDTO = memberService.getMyPage(memberDTO);

		model.addAttribute("dto", memberDTO);
	}

	@PostMapping(value = "delete")
	public String setDelete(MemberDTO memberDTO, HttpSession session) throws Exception
	{
		System.out.println("Delete POST Page");
		int rs = memberService.setDelete(memberDTO);
		session.invalidate();

		return "redirect:../../";
	}

	@RequestMapping(value = "checkId")
	@ResponseBody
	public int checkId(String id) throws Exception
	{
		System.out.println("checkId GET");

		System.out.println(id);
		int rs = memberService.getCheckId(id);
		System.out.println(rs);
		return rs;
	}

	@RequestMapping("checkName")
	@ResponseBody
	public int checkName(String name) throws Exception
	{
		System.out.println("checkName GET");

		int rs = memberService.checkName(name);
		System.out.println(rs);
		return rs;
	}

	@RequestMapping("checkEmail")
	@ResponseBody
	public int checkEmail(String email) throws Exception
	{
		System.out.println("checkEmail GET");

		int rs = memberService.checkEmail(email);
		System.out.println(rs);
		return rs;
	}

	@RequestMapping("checkPhone")
	@ResponseBody
	public int checkPhone(String phone) throws Exception
	{
		System.out.println("checkPhone GET");

		int rs = memberService.checkPhone(phone);
		System.out.println(rs);
		return rs;
	}
}
