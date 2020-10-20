package hello.core.member;

public class MemberServiceImpl implements MemberService{

    //인터페이스만 있다는 것을 생각해서 보자. 이제는 인터페이스에만 의존! 추상화에만 의존한다!!
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
