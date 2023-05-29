package com.lemint.tea.community.member;

import com.lemint.tea.community.member.dto.MemberSaveRequest;
import com.lemint.tea.community.request.MemberGetRequest;
import com.lemint.tea.community.response.MemberGetResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService service;

  @GetMapping("/get")
  public ResponseEntity<?> findMemberByIdAndPassword(MemberGetRequest request) {
    MemberGetResponse response = service.findMemberByIdAndPassword(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/save")
  public ResponseEntity<?> createMember(@RequestBody @Valid MemberSaveRequest request) {
    Long id = service.saveNewMember(request);
    return ResponseEntity.ok(id);
  }

}
