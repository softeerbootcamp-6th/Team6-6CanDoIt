const validHandler = {
    isValidIdLength(value: string): boolean {
        return value.length >= 6 && value.length <= 20;
    },

    isAlphaNumeric(value: string): boolean {
        const regex = /^[A-Za-z0-9]+$/;
        return regex.test(value);
    },

    isPasswordMinLength(value: string): boolean {
        return value.length >= 8;
    },

    hasNumberAndLetter(value: string): boolean {
        const hasNumber = /\d/.test(value);
        const hasLetter = /[a-zA-Z]/.test(value);
        return hasNumber && hasLetter;
    },

    isValidNicknameLength(value: string): boolean {
        return value.length >= 2 && value.length <= 20;
    },

    isKoreanOnly(value: string): boolean {
        return !/[0-9a-zA-Z!@#$%^&*(),.?":{}|<>]/.test(value);
    },
};

const iconButtonHandler = {
    clearTextByRef(ref: React.RefObject<HTMLInputElement>) {
        if (ref.current) {
            ref.current.value = '';
        }
    },

    togglePasswordVisibility(ref: React.RefObject<HTMLInputElement>) {
        if (ref.current) {
            ref.current.type =
                ref.current.type === 'password' ? 'text' : 'password';
        }
    },
};

export { validHandler, iconButtonHandler };
