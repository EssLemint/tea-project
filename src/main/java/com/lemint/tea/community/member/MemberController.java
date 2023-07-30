package com.lemint.tea.community.member;

import com.lemint.tea.community.member.dto.MemberSaveRequest;
import com.lemint.tea.community.request.MemberGetRequest;
import com.lemint.tea.community.response.MemberGetResponse;
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

  /**
   * @apiNote find member info by userId
   * @param request
   * @return response : member info, exception : wrong password, member not found
   * @author lemint
   * @since 2023-07-30
   * */
  @Secured({USER, MANAGER, ADMIN})
  @GetMapping("/get")
  public ResponseEntity<?> findMemberInfoByUserId(@RequestBody @Valid MemberGetRequest request) {
    MemberGetResponse response = service.findMemberInfoByUserId(request);
    return ResponseEntity.ok(response);
  }

  /**
   * @apiNote create new member
   * @param request
   * @return member seq id
   * @author lemint
   * @since 2023-07-30
   * */
  @Secured(ANONYMOUS)
  @PostMapping("/save")
  public ResponseEntity<?> createMember(@RequestBody @Valid MemberSaveRequest request) {
    Long id = service.saveNewMember(request);
    return ResponseEntity.ok(id);
  }

}
