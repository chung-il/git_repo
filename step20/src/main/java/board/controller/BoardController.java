package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.bean.BoardDTO;
import board.dao.BoardDAO;
import member.bean.MemberDAO;
import oracle.net.aso.m;
@Controller
public class BoardController {
	
	
	@Autowired
	private BoardService boardservice;
	
	@RequestMapping(value = "/board/boardList.do")
	public ModelAndView boardList(HttpServletRequest request ) throws Exception {
		//1.데이터
		int pg = 1;
		if(request.getParameter("pg") != null){
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		//2.DB
		//1페이지당 목록 5개
		int endNum = pg*5;
		int startNum = endNum - 4;
		
		//BoardDAO boardDAO = new BoardDAO();
		List<BoardDTO> list = boardservice.boardList(startNum, endNum);
		

		//페이징 숫자 3개씩
		int totalA = boardservice.getTotalA();
		int totalp = (totalA + 4 ) / 5; 
		int startPage = (pg-1)/3*3 + 1;
		int endPage = startPage +2;
		if(endPage > totalp ) endPage = totalp;
		//3.화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		//데이터 공유
		modelAndView.addObject("list",list);
		modelAndView.addObject("pg",pg);
		modelAndView.addObject("totalp",totalp);
		modelAndView.addObject("startPage",startPage);
		modelAndView.addObject("startPage",startPage);
		modelAndView.addObject("endPage",endPage);
		
		//view 처리 jsp 파일  이름 리턴 
		modelAndView.setViewName("boardList.jsp");
		return modelAndView;
		
	}
	@RequestMapping(value = "/board/boardListJson.do")
	public ModelAndView boardListJson(HttpServletRequest request) {
		//1.데이터
				int pg = 1;
				if(request.getParameter("pg") != null){
					pg = Integer.parseInt(request.getParameter("pg"));
				}
				//2.DB
				//1페이지당 목록 5개
				int endNum = pg*5;
				int startNum = endNum - 4;
				
				//BoardDAO boardDAO = new BoardDAO();
				List<BoardDTO> list = boardservice.boardList(startNum, endNum);
				
		//JSON으로  결과값 반환
		
				String rt = null;
				int total = list.size();
				
				if (total > 0 ) {
					rt = "OK";	
				}else {
					rt = "FAIL";
				}
				//JSON 객체생성
				JSONObject json = new JSONObject();
				json.put("rt", rt);
				json.put("total", total);
				
				if(total > 0 ) {
					JSONArray item = new JSONArray();
					for(int i=0; i<list.size(); i++) {
						BoardDTO boardDTO = list.get(i);
						JSONObject temp = new JSONObject();
						temp.put("seq", boardDTO.getSeq());
						temp.put("id", boardDTO.getId());
						temp.put("name", boardDTO.getName());
						temp.put("subject", boardDTO.getSubject());
						temp.put("content", boardDTO.getContent());
						temp.put("hit", boardDTO.getHit());
						temp.put("logtime", boardDTO.getLogtime());
						//JSONArray 에 저장
						item.put(i,temp);
					}
					//Json 객체에 저장
					json.put("item", item);
				}
				//검색 결과를 서블리으로 리턴
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.addObject("json",json);
				modelAndView.setViewName("boardListJson.jsp");
				return modelAndView;
	}

	@RequestMapping(value = "/board/boardView.do")
	public ModelAndView boardView(HttpServletRequest request) throws Exception {
		//1.데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		//2.DB
		//BoardDAO boardDAO = new BoardDAO();
		//조회수 증가
		boardservice.updateHit(seq);
		//상세 데이터 얻기
		BoardDTO boardDTO = boardservice.boardview(seq);
		//3.화면네비게이션
		ModelAndView modelAndView =new ModelAndView();
		//4.데이터 공유
		modelAndView.addObject("boardDTO",boardDTO);
		modelAndView.addObject("pg",pg);
		//view 처리 파일
		modelAndView.setViewName("boardView.jsp");
		return modelAndView;
	}

	@RequestMapping(value = "/board/boardWriteForm.do")
	public ModelAndView boardWriteForm() throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("boardWriteForm.jsp");
		
		return modelAndView;
	}

	@RequestMapping(value = "/board/boardWrite.do")
	public ModelAndView boardWrite(HttpServletRequest request) throws Exception {
		//1.데이터
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String id = (String)session.getAttribute("memId");
		String name = (String)session.getAttribute("memName");
		
		//DB
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setName(name);
		boardDTO.setId(id);
		boardDTO.setSubject(subject);
		boardDTO.setContent(content);
		
		
		//BoardDAO boardDAO = new BoardDAO();
		int su = boardservice.boardWrite(boardDTO);
		
		//데이터 공유
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("su",su);
		//view 처리 파일
		modelAndView.setViewName("boardWrite.jsp");
		return modelAndView;
	}

	// ModelAndView  : 데이터 처리 결과(공유데이터) 와  view 처리 파일 이름 저장
	@RequestMapping(value = "/board/boardDelete.do")
	public ModelAndView boardDelete(HttpServletRequest request) throws Exception {
		//1.데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		//2.DB
		//BoardDAO boardDAO = new BoardDAO();
		int su = boardservice.boardDelete(seq);
		//3.화면 네비 게이션
		ModelAndView modelAndView = new ModelAndView();
		//데이터 공유 저장
		modelAndView.addObject("su",su);
		modelAndView.addObject("pg",pg);
		//request.setAttribute("pg", pg);
		//request.setAttribute("su", su);
		//view 처리 파일
		modelAndView.setViewName("boardDelete.jsp");
		//viewPage= "../board/boardDelete.jsp";
		return modelAndView;
	}

	@RequestMapping(value = "/member/login.do")
	public ModelAndView login(HttpServletRequest request) throws Exception {
		//1.데이터
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		//2.DB
		MemberDAO memberDAO = new MemberDAO();
		String name = memberDAO.login(id,pwd);
		//3.화면 이동 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		if (name== null) {
			modelAndView.setViewName("loginFrom.jsp");
		}else {
			HttpSession session = request.getSession();
			session.setAttribute("memId", id);
			session.setAttribute("memName", name);
			modelAndView.setViewName("redirect:../board/boardList.do?=pg1");
		}
			return modelAndView;

	}
	
}
