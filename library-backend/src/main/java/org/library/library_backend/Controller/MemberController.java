package org.library.library_backend.Controller;


import org.library.library_backend.Model.Member;
import org.library.library_backend.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    MemberService memberService;

    @PostMapping("/addMember")
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }

    @PostMapping("/updateMemberById/{id}")
    public ResponseEntity<Member> updateMemberById(@PathVariable Long id, @RequestBody Member newMemberData) {
        return memberService.updateMemberById(id, newMemberData);

    }

    @DeleteMapping("/deleteMemberById/{id}")
    public ResponseEntity<HttpStatus> deleteMemberById(@PathVariable Long id) {
        return memberService.deleteMemberById(id);
    }

    @GetMapping("/getAllMembers")
    public ResponseEntity<List<Member>> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/getMemberById/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }
}
