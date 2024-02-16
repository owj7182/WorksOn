package com.sh.workson.chat.controller;

import com.sh.workson.auth.vo.EmployeeDetails;
import com.sh.workson.chat.dto.ChatLogCreateDto;
import com.sh.workson.chat.dto.ChatLogReturnDto;
import com.sh.workson.chat.dto.ChatRoomCreateDto;
import com.sh.workson.chat.entity.ChatLog;
import com.sh.workson.chat.entity.ChatRoom;
import com.sh.workson.chat.service.ChatService;
import com.sh.workson.employee.entity.Employee;
import com.sh.workson.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/chatMain.do")
    public void chatList(@AuthenticationPrincipal EmployeeDetails employeeDetails, Model model) {
        Long id = employeeDetails.getEmployee().getId();
//        log.debug("id = {}", id);
        List<ChatRoom> chatRooms = chatService.findAll();
//        log.debug("chatRooms = {}", chatRooms);

        List<ChatRoom> myChatRooms = new ArrayList<>();
        chatRooms.forEach(chatRoom -> {
//            log.debug("chatRoom = {}", chatRoom);
            chatRoom.getChatEmps().forEach(employee -> {
//                log.debug("employee = {}", employee);
                if (employee.getId().equals(id)) {
                    myChatRooms.add(chatRoom);
                }
            });
        });
//        log.debug("myChatRooms = {}", myChatRooms);
        model.addAttribute("myChatRooms", myChatRooms);
    }

    @GetMapping("/chatRoom.do")
    public void chatRoom(@RequestParam("id") Long id, Model model) {
        log.debug("id = {}", id);
        List<ChatLog> chatRooms = chatService.findLogByRoomId(id);
        log.debug("chatRooms = {}", chatRooms);
        model.addAttribute("chatRoomId", id);
        model.addAttribute("chatRooms", chatRooms);
    }


    @MessageMapping("/chatRoom/{chatRoomId}")
    @SendTo("/sub/chatRoom/{chatRoomId}")
    public ChatLogReturnDto subMessage(@DestinationVariable Long chatRoomId, ChatLogCreateDto chatLogCreateDto) {

//        log.debug("chatLogCreateDto = {}", chatLogCreateDto);
        chatService.createChatLog(chatLogCreateDto);
        String name = employeeService.findNameByEmpId(chatLogCreateDto.getEmployeeId());
//        log.debug("name = {}", name);
        ChatLogReturnDto chatLogReturnDto = ChatLogReturnDto.builder()
                .content(chatLogCreateDto.getContent())
                .empId(chatLogCreateDto.getEmployeeId())
                .name(name)
                .build();
//        log.debug("chatLogReturnDto = {}", chatLogReturnDto);
        return chatLogReturnDto;
    }

    @GetMapping("/createChatRoom.do")
    public void createChatRoom() {}

    @PostMapping("/createChatRoom.do")
    public String createChatRoom(ChatRoomCreateDto chatRoomCreateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        log.debug("chatRoomCreateDto = {}", chatRoomCreateDto);
        chatService.createChatRoom(chatRoomCreateDto);
        redirectAttributes.addFlashAttribute("채팅방 생성 완료!!😎");
        return "redirect:chatMain.do";
    }

    @PostMapping("/deleteChatRoom.do")
    public String deleteChatRoom(@RequestParam("id") Long id, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        log.debug("chatRoomId = {}", id);
        chatService.deleteChatRoom(id);
        redirectAttributes.addFlashAttribute("채팅방 나가기 완료!!😎");
        return "redirect:chatMain.do";
    }
}
