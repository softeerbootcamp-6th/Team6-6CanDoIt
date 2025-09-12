import { useRef } from 'react';
import useApiMutation from '../hooks/useApiMutation';
import { useQueryClient } from '@tanstack/react-query';
import { convertToWebp, validateAccessToken } from '../utils/utils.ts';

interface UserData {
    nickname: string;
    loginId: string;
    imageUrl?: string;
}

export default function useProfileImageUploader(onValid: () => void) {
    const fileInputRef = useRef<HTMLInputElement>(null);
    const queryClient = useQueryClient();

    const updateProfileImageMutation = useApiMutation<FormData, any>(
        '/user/image',
        'PATCH',
        {
            onMutate: async (formData) => {
                const previousData = queryClient.getQueryData<UserData>([
                    '/user',
                    {},
                ]);
                const file = formData.get('imageFile') as File;
                if (!file) return { previousData } as any;

                const imageUrl = URL.createObjectURL(file);
                queryClient.setQueryData<UserData>(['/user', {}], (old) =>
                    old ? { ...old, imageUrl } : old,
                );

                return { previousData } as any;
            },
            onError: (_err, _variables, context) => {
                if ((context as any)?.previousData) {
                    queryClient.setQueryData(
                        ['/user', {}],
                        (context as any).previousData,
                    );
                }
                alert('업로드 실패. 잠시 후 다시 시도해주세요');
            },
            onSettled: () => {
                queryClient.invalidateQueries({ queryKey: ['/user', {}] });
            },
        },
    );

    const handleFileClick = () => {
        fileInputRef.current?.click();
    };

    const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;
        if (!validateAccessToken()) {
            onValid();
            return;
        }

        const { imageFile } = await convertToWebp(file);
        const formData = new FormData();
        formData.append('imageFile', imageFile);

        updateProfileImageMutation.mutate(formData);
    };

    return { fileInputRef, handleFileClick, handleFileChange };
}
