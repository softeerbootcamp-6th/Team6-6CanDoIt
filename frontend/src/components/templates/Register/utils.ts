interface ValidationParams {
    loginId: string;
    confirmedId: string;
    nickname: string;
    confirmedNickname: string;
    password: string;
    passwordConfirm: string;
}

export function validateRegisterInput({
    loginId,
    confirmedId,
    nickname,
    confirmedNickname,
    password,
    passwordConfirm,
}: ValidationParams): string[] {
    const errors: string[] = [];

    if (loginId !== confirmedId || !confirmedId) {
        errors.push('아이디 중복 확인이 필요합니다.');
    }

    if (nickname !== confirmedNickname || !confirmedNickname) {
        errors.push('닉네임 중복 확인이 필요합니다.');
    }

    if (!password) {
        errors.push('비밀번호를 입력해주세요.');
    }

    if (password !== passwordConfirm) {
        errors.push('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
    }

    return errors;
}
