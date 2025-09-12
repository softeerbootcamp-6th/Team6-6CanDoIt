import { useState, useRef } from 'react';
import useApiMutation from '../hooks/useApiMutation';
import { useQueryClient } from '@tanstack/react-query';
import { validateAccessToken } from '../utils/utils.ts';

interface UserData {
    nickname: string;
    loginId: string;
    imageUrl?: string;
}

export default function useNicknameEditor(onValid: () => void) {
    const [isEditingNickName, setIsEditingNickName] = useState(false);
    const inputNickNameRef = useRef<HTMLInputElement>(null);
    const queryClient = useQueryClient();
    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

    const updateNicknameMutation = useApiMutation<
        { nickname: string },
        UserData
    >('/user/nickname', 'PATCH', {
        onMutate: async (variables): Promise<{ previousData?: UserData }> => {
            const previousData = queryClient.getQueryData<UserData>([
                '/user',
                {},
            ]);
            queryClient.setQueryData<UserData>(['/user', {}], (old) =>
                old ? { ...old, nickname: variables.nickname } : old,
            );
            return { previousData };
        },
        onError: (_err, _variables, context) => {
            if ((context as any)?.previousData) {
                queryClient.setQueryData(
                    ['/user', {}],
                    (context as any).previousData,
                );
            }
            alert('잠시 후 다시 시도해주세요');
        },
        onSettled: () => {
            queryClient.invalidateQueries({ queryKey: ['/user', {}] });
            setIsEditingNickName(false);
        },
    });

    const handleSave = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!inputNickNameRef.current) return;
        if (!validateAccessToken()) {
            onValid();
            return;
        }

        const nickname = inputNickNameRef.current.value;

        try {
            const res = await fetch(
                `${API_BASE_URL}/user/nickname?nickname=${encodeURIComponent(nickname)}`,
                {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('accessToken') ?? ''}`,
                    },
                },
            );

            if (!res.ok) {
                const error = await res.json();
                if (error.errorCode === 'USR-002')
                    error.message =
                        '한글로 이루어진 단어만 가능합니다. 공백이있는지 확인해주세요';
                throw new Error(error.message || '닉네임 중복 검사 실패');
            }

            updateNicknameMutation.mutate({ nickname });
        } catch (err: any) {
            alert(err.message);
        }
    };

    return {
        isEditingNickName,
        setIsEditingNickName,
        inputNickNameRef,
        handleSave,
    };
}
