const validHandler = {
    // 기존 회원 중복 체크를 진행 => 이미 등록된 아이디입니다.
    // 양식에 맞지 않는 아이디 작성시  => 양식에 맞지 않는 아이디입니다. 다시 한번 확인해주세요.
    // 비밀번호는 영문,숫자 포함 8자 이상=> 양식에 맞지 않는 비밀번호입니다. 다시 한번 확인해주세요.
    // 닉네임은 숫자 영문 특수문자가 있을 경우 => 한글로 된 닉네임만 가능합니다. 다시 한번 확인해주세요 안내
    // 닉네임 중복 체크 => 이미 등록된 닉네임입니다. 안내
};

const iconButtonHandler = {
    clearTextByRef(ref: React.RefObject<HTMLInputElement>) {
        if (ref.current) {
            ref.current.value = '';
        }
    },
};

export { validHandler, iconButtonHandler };
