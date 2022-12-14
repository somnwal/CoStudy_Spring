package com.costudy.controller;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.costudy.entity.GroupReg;
import com.costudy.entity.StudyGroup;
import com.costudy.entity.User;
import com.costudy.service.GroupRegService;
import com.costudy.service.StudyGroupService;
import com.costudy.service.UserService;
import com.costudy.vo.UserVO;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupRegService groupRegService;
	
	@Autowired
	private StudyGroupService studyGroupService;
	
	@GetMapping
	public String index(Model m, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user != null) {
			return "redirect:/dashboard";
		}
		
		return "index";
	}
	
	@GetMapping("/index")
	public String index2(Model m, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user != null) {
			return "redirect:/dashboard";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}
	
	@GetMapping("/login")
	public String login(Model m, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		String error = (String) session.getAttribute("error");
		String message = (String) session.getAttribute("message");
		
		if(user != null) {
			return "redirect:/dashboard";
		}
		
		if(error != null) {
			m.addAttribute("error", error);
			session.removeAttribute("error");
		}
		
		if(message != null) {
			m.addAttribute("message", message);
			session.removeAttribute("message");
		}
		
		return "login";
	}
	
	@PostMapping("/login")
	public String loginAction(Model m, HttpServletRequest req, UserVO userVO) {
		
		HttpSession session = req.getSession();
		
		Optional<User> user = userService.findById(userVO.getUserID());
		
		if(user.isPresent()) {
			if(user.get().getUserID().equals(userVO.getUserID()) && user.get().getUserPassword().equals(userVO.getUserPassword())) {
				System.out.println("????????? ?????? :::");
				
				session.setAttribute("user", user.get());
				
				return "redirect:/dashboard";
			} else {
				m.addAttribute("error", "????????? ?????? ??????????????? ???????????? ????????????.");
				
				return "login";
			}
		} else {
			m.addAttribute("error", "????????? ?????? ??????????????? ???????????? ????????????.");
			
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		
		User user = (User) req.getAttribute("user");
		
		if(user != null) {
			session.removeAttribute("user");
		}
		
		session.invalidate();
		
		return "redirect:/index";
	}
	
	@GetMapping("/register")
	public String regsiter(Model m, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user != null) {
			return "redirect:/dashboard";
		}
		
		return "register";
	}
	
	@PostMapping("/register")
	public String registerAction(Model m, UserVO userVO) {
		
		System.out.println("/registerAction :::");
		System.out.println(userVO.toString());
		
		if(userService.findById(userVO.getUserID()).isPresent()) {
			
			m.addAttribute("error", "?????? ???????????? ??????????????????.");
			
			return "register";
		}
		
		if(!userVO.getUserPassword().equals(userVO.getUserPassword2())) {
			
			m.addAttribute("error", "1??? ??????????????? 2??? ??????????????? ???????????? ????????????.");
			
			return "register";
		}
		
		if(userService.findByUserNick(userVO.getUserNick()).isPresent()) {
			m.addAttribute("error", "?????? ???????????? ??????????????????.");
			
			return "register";
		}
		
		User user = new User(userVO.getUserID(), userVO.getUserPassword(), userVO.getUserNick(), userVO.getUserEmail(), userVO.getUserBorn());
		
		userService.save(user);
		
		return "redirect:/login";
	}
	
	// =======================================================
	// ????????? ?????? ?????? ????????? !!
	// =======================================================
	// =======================================================
	// ????????????
	// =======================================================
	@GetMapping("/dashboard")
	public String dashboard(Model m, HttpServletRequest req) {
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		List<GroupReg> registered_tmp = groupRegService.findRegisteredGroupByUserID(user.getUserID());
		List<GroupReg> waiting_tmp = groupRegService.findRegisterWaitingGroupByUserID(user.getUserID());
		
		List<StudyGroup> registered = new ArrayList<>();
		List<StudyGroup> waiting = new ArrayList<>();
		
		for (GroupReg reg : registered_tmp) {
			registered.add(studyGroupService.findById(reg.getGroupID()).get());
		}
		
		for (GroupReg reg : waiting_tmp) {
			waiting.add(studyGroupService.findById(reg.getGroupID()).get());
		}
		
		// ?????? ??????
		m.addAttribute("user", user);
		
		// ????????? ??????
		m.addAttribute("registered", registered);
		
		// ?????? ?????? ??????
		m.addAttribute("waiting", waiting);
		
		System.out.println(user.toString());
		System.out.println(registered.toString());
		System.out.println(waiting.toString());
		
		return "dashboard";
	}
	
	@GetMapping("/group/list")
	public String groupList(Model m, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		String error = (String) session.getAttribute("error");
		String message = (String) session.getAttribute("message");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		if(error != null) {
			m.addAttribute("error", error);
			session.removeAttribute("error");
		}
		
		if(message != null) {
			m.addAttribute("message", message);
			session.removeAttribute("message");
		}
		
		List<StudyGroup> groupList = studyGroupService.findAll();
		
		// ?????? ??????
		m.addAttribute("user", user);
		
		// ?????? ????????? ??????
		m.addAttribute("groupList", groupList);
		
		return "group_list";
	}
	
	@GetMapping("/group/add")
	public String groupAdd(Model m, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		// ?????? ??????
		m.addAttribute("user", user);
		
		return "group_add";
	}
	
	@PostMapping("/group/add")
	public String groupAddAction(Model m, HttpServletRequest req, StudyGroup studyGroup) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		// ?????? ??????
		m.addAttribute("user", user);
				
		
		if(studyGroupService.findByStudyName(studyGroup.getStudyName()).isPresent()) {
			m.addAttribute("error", "?????? ???????????? ??????????????? ????????????.");
			
			return "group_add";
		}
		
		System.out.println("??????????????? ?????? :::");
		System.out.println(studyGroup.toString());
		
		StudyGroup tmp = studyGroupService.save(studyGroup);
		
		if(tmp != null) {
			groupRegService.applyUser(tmp.getGroupMaster(), tmp.getGroupID());
			groupRegService.acceptUser(tmp.getGroupMaster(), tmp.getGroupID());
		}
		
		session.setAttribute("message", "????????? ?????? ????????? ?????????????????????.");
		
		return "redirect:/group/view?id=" + tmp.getGroupID();
	}
	
	@GetMapping("/group/view")
	public String groupView(Model m, HttpServletRequest req, @RequestParam(value="id") long groupID) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		String error = (String) session.getAttribute("error");
		String message = (String) session.getAttribute("message");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		if(error != null) {
			m.addAttribute("error", error);
			session.removeAttribute("error");
		}
		
		if(message != null) {
			m.addAttribute("message", message);
			session.removeAttribute("message");
		}
		
		String regState = groupRegService.getRegState(user.getUserID(), groupID);
		
		Optional<StudyGroup> studyGroup = studyGroupService.findById(groupID);
		
		// ?????? ??????
		m.addAttribute("user", user);
		
		// ?????? ???????????? ??????
		m.addAttribute("regState", regState);
		
		// ?????? ?????? ??????
		m.addAttribute("studyGroup", studyGroup.get());
		
		return "group_view";
	}
	
	@PostMapping("/group/register")
	public String groupRegister(Model m, HttpServletRequest req, @RequestParam(value="userID") String userID, @RequestParam(value="groupID") long groupID) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		
		groupRegService.applyUser(userID, groupID);
		
		session.setAttribute("message", "????????? ?????? ?????? ????????? ?????? ???????????????. ???????????? ???????????? ?????? ???????????????.");
		
		return "redirect:/group/view?id=" + groupID;
	}
	
	@PostMapping("/group/unregister")
	public String groupUnregister(Model m, HttpServletRequest req, @RequestParam(value="userID") String userID, @RequestParam(value="groupID") long groupID) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		groupRegService.denyUser(userID, groupID);
		studyGroupService.decreaseGroupMemCount(groupID);
		
		session.setAttribute("message", "????????? ???????????? ?????????????????????.");
		
		return "redirect:/group/view?id=" + groupID;
	}
	
	@PostMapping("/group/cancel")
	public String groupCancel(Model m, HttpServletRequest req, @RequestParam(value="userID") String userID, @RequestParam(value="groupID") long groupID) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		groupRegService.denyUser(userID, groupID);
		
		session.setAttribute("message", "????????? ?????? ?????? ????????? ?????????????????????.");
		
		return "redirect:/group/view?id=" + groupID;
	}
	
	
	@GetMapping("/group/accept")
	public String groupAccept(Model m, HttpServletRequest req, @RequestParam(value="id") long groupID) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		String error = (String) session.getAttribute("error");
		String message = (String) session.getAttribute("message");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		if(error != null) {
			m.addAttribute("error", error);
			session.removeAttribute("error");
		}
		
		if(message != null) {
			m.addAttribute("message", message);
			session.removeAttribute("message");
		}
		
		String regState = groupRegService.getRegState(user.getUserID(), groupID);
		
		Optional<StudyGroup> studyGroup = studyGroupService.findById(groupID);
		
		
		List<GroupReg> groupRegList = groupRegService.findRegisterWaitingGroupByGroupID(groupID);
		
		// ?????? ??????
		m.addAttribute("user", user);
		
		// ?????? ???????????? ??????
		m.addAttribute("regState", regState);
		
		// ?????? ?????? ??????
		m.addAttribute("studyGroup", studyGroup.get());
		
		
		// ?????? ?????? ?????? ????????? ??????
		m.addAttribute("groupRegList", groupRegList);
		
		return "group_accept";
	}
	
	@PostMapping("/group/accept")
	public String groupAcceptAction(Model m, HttpServletRequest req, @RequestParam(value="userID") String userID, @RequestParam(value="groupID") long groupID) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		groupRegService.acceptUser(userID, groupID);
		studyGroupService.increaseGroupMemCount(groupID);
		
		session.setAttribute("message", "?????? ?????? ( " + userID +" )??? ????????? ?????? ????????? ?????????????????????.");
		
		return "redirect:/group/accept?id=" + groupID;
	}
	
	@PostMapping("/group/deny")
	public String groupDenyAction(Model m, HttpServletRequest req, @RequestParam(value="userID") String userID, @RequestParam(value="groupID") long groupID) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		groupRegService.denyUser(userID, groupID);
		
		session.setAttribute("error", "?????? ?????? ( " + userID +" )??? ????????? ?????? ????????? ?????????????????????.");
		
		return "redirect:/group/accept?id=" + groupID;
	}
	
	@GetMapping("/group/modify")
	public String groupModify(Model m, HttpServletRequest req, @RequestParam(value="id") long groupID) {
		
		System.out.println("?????? ?????? :::");
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		String regState = groupRegService.getRegState(user.getUserID(), groupID);
		
		Optional<StudyGroup> studyGroup = studyGroupService.findById(groupID);
		
		// ?????? ??????
		m.addAttribute("user", user);
		
		// ?????? ???????????? ??????
		m.addAttribute("regState", regState);
		
		// ?????? ?????? ??????
		m.addAttribute("studyGroup", studyGroup.get());
		
		return "group_modify";
	}
	
	@PostMapping("/group/modify")
	public String groupModifyAction(Model m, HttpServletRequest req, StudyGroup studyGroup) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		studyGroupService.save(studyGroup);
		
		return "redirect:/group/view?id=" + studyGroup.getGroupID();
	}

	@PostMapping("/group/delete")
	public String groupModifyAction(Model m, HttpServletRequest req, @RequestParam(value="groupID") long id) {
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		session.setAttribute("error", "????????? ?????????????????????.");
		
		studyGroupService.deleteById(id);
		
		return "redirect:/group/list";
	}
	
	@GetMapping("/user/settings")
	public String userSettings(Model m, HttpServletRequest req) {
		
		System.out.println("?????? ?????? :::");
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		String error = (String) session.getAttribute("error");
		String message = (String) session.getAttribute("message");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		if(error != null) {
			m.addAttribute("error", error);
			session.removeAttribute("error");
		}
		
		if(message != null) {
			m.addAttribute("message", message);
			session.removeAttribute("message");
		}
		
		// ?????? ??????
		m.addAttribute("user", user);
		
		return "user_settings";
	}
	
	@PostMapping("/user/settings")
	public String userSettingsAction(Model m, HttpServletRequest req, UserVO userVO) {
		
		System.out.println("?????? ?????? :::");
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			session.setAttribute("error", "????????? ????????? ????????????.");
			
			return "redirect:/login";
		}
		
		System.out.println("/registerAction :::");
		System.out.println(userVO.toString());
		
		if(!userVO.getUserPassword().equals(userVO.getUserPassword2())) {
			
			session.setAttribute("error", "1??? ??????????????? 2??? ??????????????? ???????????? ????????????.");
			
			return "redirect:/user/settings";
		}
		
		if((!user.getUserNick().equals(userVO.getUserNick())) && userService.findByUserNick(userVO.getUserNick()).isPresent()) {
			session.setAttribute("error", "?????? ???????????? ??????????????????.");
			
			return "redirect:/user/settings";
		}
		
		User tmp = new User(userVO.getUserID(), userVO.getUserPassword(), userVO.getUserNick(), userVO.getUserEmail(), userVO.getUserBorn());
		
		userService.save(tmp);
		
		session.setAttribute("user", tmp);
		session.setAttribute("message", "?????? ????????? ?????????????????????.");
		
		return "redirect:/user/settings";
	}
}
