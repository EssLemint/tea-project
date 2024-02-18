package com.lemint.tea.community.member;

import com.lemint.tea.community.member.dto.MemberFindPwdRequest;
import com.lemint.tea.community.member.dto.MemberSaveRequest;
import com.lemint.tea.community.request.MemberGetRequest;
import com.lemint.tea.community.response.MemberGetResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.lemint.tea.enums.Role.SecRoles.*;


/**
 * 사용자 비밀번호 암호화에 대한 고민
 * 사용자 암호는 단방향이 맞을까 양방향이 맞을까?
 * 암호는 단방으로 진행하고 비밀번호는 임시 비밀 번호를 발급받고 사용자가 변경을 진행하는 것으로 하는게 맞을듯 하오
 * 그 외의 이름/주소/전화번호 같은 부분은 양방향으로 진행하는게 맞는 그림이라고 생각한다. MAYBE
 * */
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
  @GetMapping("/save")
  public ResponseEntity<?> createMember(@RequestBody @Valid MemberSaveRequest request) {
    Long id = service.saveNewMember(request);
    return ResponseEntity.ok(id);
  }

  @Secured(ANONYMOUS)
  @PostMapping("/find/pwd")
  public ResponseEntity<?> findPassword(@RequestBody @Valid MemberFindPwdRequest request) {
    String password = service.findPassword(request);
    return ResponseEntity.ok(password);
  }

}
