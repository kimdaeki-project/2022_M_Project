package com.iu.home.reviews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iu.home.reviewsComment.ReviewsCommentDTO;
import com.iu.home.util.Pager;
import com.iu.home.util.ReviewsCommentPager;
import com.iu.home.util.ReviewsPager;

@Controller
@RequestMapping(value = "/reviews/*")
public class ReviewsController {
	
	@Autowired
	private ReviewsService reviewsService;
	
	//리뷰 리스트
	@GetMapping(value = "list")
	public ModelAndView getReviewsList(ReviewsPager reviewsPager) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		
		System.out.println("리뷰 List 실행");
		
		List<ReviewsDTO> ar = reviewsService.getReviewsList(reviewsPager);
		
		mv.addObject("reviewsPager", reviewsPager);
		mv.addObject("list", ar);
		mv.setViewName("reviews/list");
		
		return mv;
	}
	
	//리뷰 디테일
	@GetMapping(value = "detail")
	public ModelAndView getReviewsDetail(ReviewsDTO reviewsDTO) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		System.out.println("리뷰 Detail 실행");
		
		reviewsDTO = reviewsService.getReviewsDetail(reviewsDTO);
		
		mv.addObject("dto", reviewsDTO);
		mv.setViewName("reviews/detail");
		
		return mv;
	}
	
	//리뷰 Add
	@GetMapping(value = "add")
	public String setReviewsAdd(ReviewsDTO reviewsDTO) throws Exception{
		System.out.println("리뷰 Add Get 실행");
		
		return "reviews/add";
	}
	
	@PostMapping(value = "add")
	public ModelAndView setReviewsAdd(ReviewsDTO reviewsDTO, MultipartFile [] files, HttpSession session) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		
		int result = reviewsService.setReviewsAdd(reviewsDTO, files, session.getServletContext());
		
		System.out.println("리뷰 Add Post 실행");
		mv.addObject("result", result);
		mv.addObject("dto", reviewsDTO);
		mv.setViewName("redirect:./list");
		
		if(result == 1) {
			System.out.println("리뷰 글이 작성되었습니다!!");
		}else {
			System.out.println("리뷰 글 작성이 실패하였습니다 ㅜㅜ");
		}
		
		return mv;
	}
	
	//리뷰 수정
	@GetMapping(value = "update")
	public String setReviewsUpdate(ReviewsDTO reviewsDTO, Model model) throws Exception{
		
		
		System.out.println("리뷰글 수정하기 GET 실행");
	
		reviewsDTO = reviewsService.getReviewsDetail(reviewsDTO);
		
		model.addAttribute("dto", reviewsDTO);
	
	
		
		return "reviews/update";
		
	}
	
	
	@PostMapping(value = "update")
	public ModelAndView setReviewsUpdate(ReviewsDTO reviewsDTO) throws Exception{
		
		System.out.println("리뷰 글 수정하기 POST 실행");
		
		ModelAndView mv = new ModelAndView();
		
		int result = reviewsService.setReviewsUpdate(reviewsDTO);
		
		mv.addObject("dto", reviewsDTO);
		mv.setViewName("redirect:./list");
		
		if(result == 1) {
			System.out.println("리뷰 글 수정하기가 성공하였습니다!!");
		}else {
			System.out.println("리뷰 글 수정하기가 실패하였습니다 ㅜㅜ");
		}
		
		return mv;
	}
	
	//리뷰 삭제 
	@GetMapping(value = "delete")
	public ModelAndView setReviewsDelete(ReviewsDTO reviewsDTO) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		
		System.out.println("리뷰 글 삭제 실행");
		
		
		System.out.println(reviewsDTO.getReviewNum());
		System.out.println(reviewsDTO.getTitle());
		System.out.println(reviewsDTO.getContents());
		
		int result = reviewsService.setReviewsDelete(reviewsDTO);
		
		mv.addObject("dto", reviewsDTO);
		mv.setViewName("redirect:./list");
		
		if(result == 1) {
			System.out.println("글 삭제가 성공하였씁니다!!");
		}else {
			System.out.println("글 삭제가 실패하였습니다 ㅜㅜ");
		}
		
		return mv;
	}
	
	@PostMapping("reviewsFilesDelete")
	@ResponseBody
	public int setReviewsFilesDelete(ReviewsFilesDTO reviewsFilesDTO, HttpSession session) throws Exception{
		int result = reviewsService.setReviewsFilesDelete(reviewsFilesDTO, session.getServletContext());
		
		return result;
	}
	
	
	
	// jsp에 출력하고 결과물을 응답으로 전송
	@GetMapping(value = "reviewsCommentList")
	@ResponseBody
	public Map<String, Object> reviewsCommentList(ReviewsCommentPager reviewsCommentPager) throws Exception{
		
		List<ReviewsCommentDTO> ar = reviewsService.getReviewsCommentList(reviewsCommentPager);
		
		System.out.println("답글 목록");
		System.out.println(ar.size());
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", ar);
		map.put("reviewsCommentPager", reviewsCommentPager);
		
		return map;
	}
	
	
	
//	@GetMapping(value = "reviewsCommentAdd")
//	public void reviewsCommentAdd () throws Exception{
//		
//		System.out.println("답글달기 GET 실행");
//	}
//	
	
	
	@PostMapping(value = "reviewsCommentAdd")
	@ResponseBody
	public int reviewsCommentAdd(ReviewsCommentDTO reviewsCommentDTO) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		System.out.println("답글달기 POST 실행");
		
		int result = reviewsService.setReviewsCommentAdd(reviewsCommentDTO);
		
		mv.addObject("result", result);
		
		if(result == 1) {
			System.out.println("답글이 성공적으로 달렸습니다!!");
		}else {
			System.out.println("답글이 달리지 못했씁니다ㅜㅜ");
		}
		
		return result;
	}
	
	
	
	@PostMapping(value = "reviewsCommentUpdate")
	@ResponseBody
	
	//jsp를 안거치고 body에 바로 담아 응답으로 내보냄
	public int reviewsCommentUpdate(ReviewsCommentDTO reviewsCommentDTO) throws Exception{
		
		System.out.println("답글 수정하기 POST실행");
		
		int result = reviewsService.setReviewsCommentUpdate(reviewsCommentDTO);
		
		if(result == 1) {
			System.out.println("답글 수정 완료!!");
		}else {
			System.out.println("답글 수정 실패 ㅜㅜ");
		}
		
		return result;
	}
	
	
	@PostMapping(value = "reviewsCommentDelete")
	@ResponseBody
	//jsp를 안거치고 body에 바로 담아 응답으로 내보냄
	public int setReviewsCommentDelete(ReviewsCommentDTO reviewsCommentDTO) throws Exception{
		
		System.out.println("답글 삭제 POST 실행");
		
		int result = reviewsService.setReviewsCommentDelete(reviewsCommentDTO);
		
		return result;
	}
	
	

}