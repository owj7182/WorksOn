package com.sh.workson.attend.controller;

import com.sh.workson.attend.entity.Attend;
import com.sh.workson.attend.entity.AttendListDto;
import com.sh.workson.attend.repository.AttendRepository;
import com.sh.workson.attend.service.AttendService;
import com.sh.workson.auth.vo.EmployeeDetails;
import com.sh.workson.employee.entity.Employee;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/attend")
public class AttendController {

    @Autowired
    private AttendService attendService;
    @Autowired
    private AttendRepository attendRepository;

    @GetMapping ("/attendList.do")
    public void attendList(@PageableDefault(value = 5, page = 0)Pageable pageable, Model model){
        Long id = 952L;
    Page<AttendListDto> attendPage = attendService.findAll(pageable, id);
    model.addAttribute("attends",attendPage.getContent());
    model.addAttribute("totalCount", attendPage.getTotalElements());


    Attend firstAttend = attendService.findByOrderByStartAt(id);
        model.addAttribute("attend", firstAttend);
        log.debug("attend = {}", firstAttend);
        log.debug("attends = {}", attendPage.getContent());
    }

    // 출근 버튼을 처리하는 메소드
    @PostMapping("/startWork.do")
    public ResponseEntity<?> startWork(
            @AuthenticationPrincipal EmployeeDetails employeeDetails,
            RedirectAttributes redirectAttributes
    ) {
        employeeDetails.getEmployee().getId(); // 사용자 아이디
        log.debug("employeeId = {}", employeeDetails.getEmployee().getId());
        Attend attend = Attend.builder()
                .employee(employeeDetails.getEmployee())
                .build();

        attendService.insertAttend(attend);
        log.debug("attends = {}", attend);

        return ResponseEntity.ok("출근 등록이 완료 됐습니다.");
    }
}