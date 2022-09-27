package com.iu.home.shop;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.iu.home.menu.MenuDTO;
import com.iu.home.util.ShopPager;

@Controller
@RequestMapping(value = "/shop/*")
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	@GetMapping(value = "add")
	public String setAdd()throws Exception{	
		System.out.println("getadd");
		return "kdy/shop/add";
	}
	@PostMapping(value = "add")
	public ModelAndView setAdd(ShopDTO shopDTO, MultipartFile[] files, HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView();	
		System.out.println("postadd");
		int result = shopService.setAdd(shopDTO, files, session.getServletContext());
		
		mv.addObject("result", result);
		
		mv.setViewName("redirect:./listHTML");
		return mv;
	}
	
	@GetMapping(value = "addMenu")
	public ModelAndView setAddMenu(Long shopNum)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("shopNum", shopNum);
		mv.setViewName("kdy/shop/addMenu");
		return mv;
	}
	@PostMapping(value = "addMenu")
	public ModelAndView setAddMenu(ShopDTO shopDTO)throws Exception{
		ModelAndView mv = new ModelAndView();
		
		System.out.println("postAddMenu");
		System.out.println(shopDTO.getShopNum());
		int result = shopService.setAddMenu(shopDTO);
		
		if(result == 1) {
			System.out.println("성공");
		} else {
			System.out.println("실패");
		}
		mv.addObject("detail", shopDTO);
		mv.setViewName("redirect:./detailHTML?shopNum="+shopDTO.getShopNum());
		
		return mv;
	}
	
	@GetMapping(value = "listHTML")
	public ModelAndView getList(ShopPager shopPager)throws Exception{
		System.out.println("getList");
		ModelAndView mv = new ModelAndView();
		List<ShopDTO> ar = shopService.getList(shopPager);
		mv.addObject("list", ar);
		mv.addObject("shopPager", shopPager);
		mv.setViewName("kdy/shop/listHTML");
		return mv;
	}
	@GetMapping(value = "detailHTML")
	public ModelAndView getDetail(ShopDTO shopDTO)throws Exception{
		System.out.println("getDetail");
		System.out.println(shopDTO.getMenuName());
		ModelAndView mv = new ModelAndView();
		shopDTO = shopService.getDetail(shopDTO);
		mv.setViewName("kdy/shop/detailHTML");
		mv.addObject("detail", shopDTO);
		return mv;
	}
	
	@GetMapping(value = "update")
	public void setUpdate(ShopDTO shopDTO, Model model)throws Exception{
		shopDTO = shopService.getDetail(shopDTO);
		model.addAttribute("detail", shopDTO);
		
	}
	@PostMapping(value = "update")
	public ModelAndView setUpdate(ShopDTO shopDTO)throws Exception{
		ModelAndView mv = new ModelAndView();
		int result = shopService.setUpdate(shopDTO);
		mv.setViewName("redirect:./detail?shopNum="+shopDTO.getShopNum());
		return mv;
	}
	@GetMapping(value = "updateMenu")
	public ModelAndView setUpdateMenu(Long shopNum)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("shopNum", shopNum);
		mv.setViewName("kdy/shop/addMenu");
		return mv;
	}
	@PostMapping(value = "updateMenu")
	public ModelAndView setUddateMenu(ShopDTO shopDTO)throws Exception{
		ModelAndView mv = new ModelAndView();
		int result = shopService.setUpdateMenu(shopDTO);
		mv.setViewName("redirect:./detailHTML?shopNum="+shopDTO.getShopNum());
		return mv;
	}
	
	@GetMapping("delete")
	public ModelAndView setDelete(ShopDTO shopDTO)throws Exception{
		ModelAndView mv = new ModelAndView();
		int result = shopService.setDelete(shopDTO);
		mv.setViewName("redirect:./list");
		return mv;
	}
	
	@GetMapping("deleteMenu")
	public ModelAndView setDeleteMenu(ShopDTO shopDTO)throws Exception{
		ModelAndView mv = new ModelAndView();
		int result = shopService.setDeleteMenu(shopDTO);
		mv.setViewName("redirect:./detailHTML?shopNum="+shopDTO.getShopNum());
		return mv;
	}
	//===========================file
	
	

}