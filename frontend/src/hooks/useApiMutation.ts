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

        const text = await res.text();

        if (!res.ok) {
            let errorMessage = 'API 요청 실패';
            try {
                const errorData = JSON.parse(text);
                errorMessage = errorData.message || errorMessage;
            } catch {
                errorMessage = text || errorMessage;
            }
            throw new Error(errorMessage);
        }
        try {
            return JSON.parse(text);
        } catch {
            return text;
        }
    };

    return useMutation<TResponse | string, Error, TRequest>({
        mutationFn,
        ...options,
    });
}
