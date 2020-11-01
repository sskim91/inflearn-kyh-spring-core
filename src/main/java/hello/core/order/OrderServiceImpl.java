package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor  //lombok final이 붙은 필드를 모아서 생성자를 만들어준다.
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired  //생성자가 딱 하나 있으면 @Autowired를 생략해도 자동 주입 된다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        //OrderServiceImpl 입장에서는 할인 정책이 고정할인정책인지 정률할인 정책인지 모른다. 알 필요도 없음.
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        //단일 책임 원칙을 잘 지킨 코드
        //Why? 할인에 대한 정책이 변경되면 할인쪽만 변경하면 되기 때문이다. 주문쪽에선 할인된 가격만 받아오면 된다.
        //할인을 어떻게 할지는 몰라도 된다. 결과만 받으면된다.
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
