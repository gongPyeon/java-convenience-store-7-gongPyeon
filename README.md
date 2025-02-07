# java-convenience-store-precourse
* 기능 목록
- [x] 상품 목록과 행사 목록을 파일 입출력을 통해 불러온다
- [x] 상품 목록과 행사 목록이 저장이 되지 않았을 경우, 예외처리한다
---
- [x] [상품명-수량], [상품명-수량] 형식으로 구매할 상품과 수량을 입력받는다
- [x] 입력 형식이 대괄호 안에 하이픈을 기준으로 상품과 수량이 나눠지지 않을 경우, "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."와 함께 예외처리한다
- [x] 입력받을 때 공백을 허용한다 단, "콜 라"와 같이 사이에 공백이 있는것은 허용하지 않는다 (커스텀)
- [x] 연속에서 상품명을 입력받을때, 중복되는 것을 허용하지 않는다 (커스텀)
---
- [x] 상품명과 수량을 분할한 후 공백 제거를 한다
- [x] 수량을 int로 변환할 수 없을 경우, 예외처리한다
- [x] 수량이 양수가 아닐 경우, 예외처리한다
- [x] 상품명이 상품목록에 있는지 조회한다
- [x] 상품명이 없을 경우, "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요." 안내 메세지를 전달하고 다시 입력받는다
- [x] 상품명이 있을 경우, 수량을 확인한다
- [x] 수량이 부족한 경우, "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요." 안내 메세지를 전달하고 다시 입력받는다
---
- [x] Y와 N외의 다른 입력값이 들어올 경우, "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요." 안내 메세지를 전달하고 예외처리한다
- [x] Y(공백)과 같이 공백을 포함한 입력을 허용하지 않는다
---
- [x] 상품명이 행사목록에 있는지 조회한다
- [x] 상품명이 행사목록에 없을 경우, "현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"와 같은 안내 메세지를 전달한다
- [x] 프로모션 할인이 기간 내 유효한지 확인한다
- [x] 프로모션 할인 기간 내 유효하지 않을 경우 안내 메세지를 전달한다
- [x] 상품명이 행사목록에 있고 기간 내 유효한데 수량을 덜 가져왔을 경우, "현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"와 같은 안내 메세지를 전달한다
  - [x] Y : 증정받을 수 있는 상품을 추가한다
  - [x] N : 증정받을 수 있는 상품을 추가하지 않는다
- [x] 프로모션 재고에 해당 상품이 남았는지 확인한다
- [x] 프로모션 재고에 해당 상품이 남아있지 않다면, "현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"와 같은 안내 메세지를 전달한다
    - [x] Y : 일부 수량에 대해 정가로 결제한다
    - [x] N : 증정받을 수 있는 상품을 추가하지 않는다
---
- [x] 멤버쉽 회원인지 조회한다
- [x] 멤버쉽 회원아 아닐 경우, 안내 메세지를 전달하고 넘어간다
- [x] 멤버쉽 회원일 경우, "멤버십 할인을 받으시겠습니까? (Y/N)"와 같은 안내 메세지를 전달한다
    - [x] Y : 멤버십 할인을 적용한다
      - [x] 멤버쉽 할인의 최대한도를 넘지 않는지 확인한다
    - [x] N : 멤버십 할인을 적용하지 않는다
---
- [x] 영수증을 출력한다
---
- [x] 영수증을 출력한 후, "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"와 같은 안내 메세지를 전달한다
  - [x] Y : 다시 입력받는다
  - [x] N : 종료한다