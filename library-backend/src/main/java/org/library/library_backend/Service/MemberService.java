package org.library.library_backend.Service;

import org.library.library_backend.Model.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    public ResponseEntity<Member> addMember(Member member);
    public ResponseEntity<Member> updateMemberById(Long id, Member newMemberData);
    public ResponseEntity<HttpStatus> deleteMemberById(Long id);
    public ResponseEntity<List<Member>> getAllMembers();
    public ResponseEntity<Member> getMemberById(Long id);
}
