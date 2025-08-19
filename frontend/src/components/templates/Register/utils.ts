interface ValidationParams {
    loginId: string;
    confirmedId: string;
    nickname: string;
    confirmedNickname: string;
    password: string;
    passwordConfirm: string;
    checkBox: boolean;
}

export function validateRegisterInput({
    loginId,
    confirmedId,
    nickname,
    confirmedNickname,
    password,
    passwordConfirm,
    checkBox,
}: ValidationParams): string {
    let errors: string;

    if (loginId !== confirmedId || !confirmedId) {
        errors = '아이디 중복 확인이 필요합니다.';
        return errors;
    }

    if (nickname !== confirmedNickname || !confirmedNickname) {
        errors = '닉네임 중복 확인이 필요합니다.';
        return errors;
    }

    if (!password) {
        errors = '비밀번호를 입력해주세요.';
        return errors;
    }

    if (password !== passwordConfirm) {
        errors = '비밀번호와 비밀번호 확인이 일치하지 않습니다.';
        return errors;
    }

    if (!checkBox) {
        errors = '필수 체크 항목을 확인해주세요.';
        return errors;
    }

    return '';
}
