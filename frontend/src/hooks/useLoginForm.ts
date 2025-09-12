// hooks/useLoginForm.ts
import { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useApiMutation from './useApiMutation';

interface UseLoginFormOptions {
    redirectTo?: string;
}

export function useLoginForm({ redirectTo = '/' }: UseLoginFormOptions = {}) {
    const [errorMessage, setErrorMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const idRef = useRef<HTMLInputElement | null>(null);
    const passwordRef = useRef<HTMLInputElement | null>(null);
    const autoLoginRef = useRef<HTMLInputElement | null>(null);

    const navigate = useNavigate();

    const mutation = useApiMutation<{ loginId: string; password: string }, any>(
        '/user/sign-in',
        'POST',
        {
            onSuccess: (data) => {
                const storage = autoLoginRef.current?.checked
                    ? localStorage
                    : sessionStorage;
                storage.setItem('accessToken', data.value);
                navigate(redirectTo);
            },
            onError: (error: Error) => setErrorMessage(error.message),
            onMutate: () => setIsLoading(true),
            onSettled: () => setIsLoading(false),
        },
    );

    const handleSubmit = () => {
        mutation.mutate({
            loginId: idRef.current?.value ?? '',
            password: passwordRef.current?.value ?? '',
        });
    };

    return {
        refs: { idRef, passwordRef, autoLoginRef },
        errorMessage,
        setErrorMessage,
        isLoading,
        handleSubmit,
    };
}
