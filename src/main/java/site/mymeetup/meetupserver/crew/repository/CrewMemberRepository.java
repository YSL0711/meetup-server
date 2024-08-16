package site.mymeetup.meetupserver.crew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.mymeetup.meetupserver.crew.entity.Crew;
import site.mymeetup.meetupserver.crew.entity.CrewMember;
import site.mymeetup.meetupserver.crew.role.CrewMemberRole;
import site.mymeetup.meetupserver.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    // 모임과 회원으로 모임멤버 가져오기
    Optional<CrewMember> findByCrewAndMember(Crew crew, Member member);

    // 특정 role인 멤버 조회
    Optional<CrewMember> findByCrewAndMemberAndRoleIn(Crew crew, Member member, List<CrewMemberRole> roles);

    // 특정 모임의 권한을 가졌는지 확인 (ex. 모임장인지)
    boolean existsByCrewAndMemberAndRole(Crew crew, Member member, CrewMemberRole role);

    // 특정 모임의 유효한 권한을 가진 멤버인지 확인 (ex. 일반, 운영진, 모임장)
    boolean existsByCrewAndMemberAndRoleIn(Crew crew, Member member, List<CrewMemberRole> roles);

    // 모임원 조회
    List<CrewMember> findByCrewAndRoleInOrderByRoleDesc(Crew crew, List<CrewMemberRole> roles);

    // 특정 모임에서 승인 대기 상태의 모임원 조회
    List<CrewMember> findByCrewAndRole(Crew crew, CrewMemberRole role);

    Optional<CrewMember> findByCrew_CrewIdAndMember_MemberId(Long crewId, Long memberId);

}