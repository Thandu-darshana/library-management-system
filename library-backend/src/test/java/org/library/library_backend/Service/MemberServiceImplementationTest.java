package org.library.library_backend.Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.library.library_backend.Model.Member;
import org.library.library_backend.Repo.MemberRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplementationTest {

    @Mock
    private MemberRepo memberRepo;

    @InjectMocks
    private MemberServiceImplementation memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setMemberId(1L);
        member.setMemberName("John Doe");
        member.setEmail("john.doe@example.com");
        member.setPhone("1234567890");
        member.setAddress("123 Street, City");
        member.setRegisteredDate(LocalDate.now());
    }

    @Test
    public void testAddMember() {
        when(memberRepo.save(any(Member.class))).thenReturn(member);

        ResponseEntity<Member> response = memberService.addMember(member);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(member, response.getBody());

        verify(memberRepo, times(1)).save(any(Member.class));
    }

    @Test
    public void testGetAllMembers() {
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);

        when(memberRepo.findAll()).thenReturn(memberList);

        ResponseEntity<List<Member>> response = memberService.getAllMembers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(memberList, response.getBody());

        verify(memberRepo, times(1)).findAll();
    }

    @Test
    public void testGetAllMembers_NoContent() {
        List<Member> memberList = new ArrayList<>();

        when(memberRepo.findAll()).thenReturn(memberList);

        ResponseEntity<List<Member>> response = memberService.getAllMembers();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(memberRepo, times(1)).findAll();
    }

    @Test
    public void testGetMemberById() {
        when(memberRepo.findById(1L)).thenReturn(Optional.of(member));

        ResponseEntity<Member> response = memberService.getMemberById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(member, response.getBody());

        verify(memberRepo, times(1)).findById(1L);
    }

    @Test
    public void testGetMemberById_NotFound() {
        when(memberRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Member> response = memberService.getMemberById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(memberRepo, times(1)).findById(1L);
    }

    @Test
    public void testUpdateMemberById() {
        Member updatedMember = new Member();
        updatedMember.setMemberName("Jane Doe");
        updatedMember.setEmail("jane.doe@example.com");
        updatedMember.setPhone("0987654321");
        updatedMember.setAddress("456 Another Street, City");
        updatedMember.setRegisteredDate(LocalDate.now());

        when(memberRepo.findById(1L)).thenReturn(Optional.of(member));
        when(memberRepo.save(any(Member.class))).thenReturn(updatedMember);

        ResponseEntity<Member> response = memberService.updateMemberById(1L, updatedMember);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedMember, response.getBody());

        verify(memberRepo, times(1)).findById(1L);
        verify(memberRepo, times(1)).save(any(Member.class));
    }

    @Test
    public void testUpdateMemberById_NotFound() {
        Member updatedMember = new Member();

        when(memberRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Member> response = memberService.updateMemberById(1L, updatedMember);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(memberRepo, times(1)).findById(1L);
    }

    @Test
    public void testDeleteMemberById() {
        doNothing().when(memberRepo).deleteById(1L);

        ResponseEntity<HttpStatus> response = memberService.deleteMemberById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(memberRepo, times(1)).deleteById(1L);
    }
}
