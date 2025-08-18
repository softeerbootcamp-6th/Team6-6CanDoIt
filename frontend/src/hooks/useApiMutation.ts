const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

import {
    useMutation,
    type UseMutationOptions,
    type UseMutationResult,
} from '@tanstack/react-query';

type Method = 'POST' | 'PUT' | 'DELETE';

export default function useApiMutation<TRequest = any, TResponse = any>(
    url: string,
    method: Method = 'POST',
    options?: UseMutationOptions<TResponse | string, Error, TRequest>,
): UseMutationResult<TResponse | string, Error, TRequest> {
    const mutationFn = async (body: TRequest): Promise<TResponse | string> => {
        const res = await fetch(`${API_BASE_URL}${url}`, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body),
        });

        if (!res.ok) {
            const errorData = await res.json().catch(() => ({}));
            throw new Error(errorData.message || 'API 요청 실패');
        }

        try {
            return await res.json();
        } catch {
            const text = await res.text();
            return text;
        }
    };

    return useMutation<TResponse | string, Error, TRequest>({
        mutationFn,
        ...options,
    });
}
