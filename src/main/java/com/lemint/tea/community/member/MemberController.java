package com.lemint.tea.community.member;

import com.lemint.tea.community.member.dto.MemberSaveRequest;
import com.lemint.tea.community.request.MemberGetRequest;
import com.lemint.tea.community.response.MemberGetResponse;
import com.lemint.tea.enums.Role;
import com.lemint.tea.enums.Role.SecRoles;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.lemint.tea.enums.Role.SecRoles.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService service;

  @Secured({USER, MANAGER, ADMIN})
  @GetMapping("/get")
  public ResponseEntity<?> findMemberByIdAndPassword(MemberGetRequest request) {
    MemberGetResponse response = service.findMemberByIdAndPassword(request);
    return ResponseEntity.ok(response);
  }

  @Secured({ANONYMOUS})
  @PostMapping("/save")
  public ResponseEntity<?> createMember(@RequestBody @Valid MemberSaveRequest request) {
    Long id = service.saveNewMember(request);
    return ResponseEntity.ok(id);
  }

}
