package org.library.library_backend.Service;


import org.library.library_backend.Model.Member;
import org.library.library_backend.Repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImplementation implements MemberService{
    @Autowired
    MemberRepo memberRepo;
    @Override
    public ResponseEntity<Member> addMember(Member member) {
        Member memberObj = memberRepo.save(member);

        return new ResponseEntity<>(memberObj, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Member> updateMemberById(Long id, Member newMemberData) {
        Optional<Member> oldMemberData = memberRepo.findById(id);
        if(oldMemberData.isPresent()){
            Member updatedMemberData = oldMemberData.get();
            updatedMemberData.setMemberName(newMemberData.getMemberName());
            updatedMemberData.setEmail(newMemberData.getEmail());
            updatedMemberData.setPhone(newMemberData.getPhone());
            updatedMemberData.setAddress(newMemberData.getAddress());
            updatedMemberData.setRegisteredDate(newMemberData.getRegisteredDate());

            Member memberObj = memberRepo.save(updatedMemberData);
            return new ResponseEntity<>(memberObj,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteMemberById(Long id) {
        memberRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Member>> getAllMembers(){
        try{
            List<Member> memberList = new ArrayList<>();
            memberRepo.findAll().forEach(memberList::add);

            if (memberList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(memberList,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<Member> getMemberById(Long id) {
        Optional<Member> memberData = memberRepo.findById(id);
        if(memberData.isPresent()){
            return new ResponseEntity<>(memberData.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
