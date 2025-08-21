const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

import {
    useMutation,
    type UseMutationOptions,
    type UseMutationResult,
} from '@tanstack/react-query';

type Method = 'POST' | 'PUT' | 'DELETE';

const getToken = () =>
    localStorage.getItem('accessToken') ??
    sessionStorage.getItem('accessToken');

export default function useApiMutation<TRequest = any, TResponse = any>(
    url: string,
    method: Method = 'POST',
    options?: UseMutationOptions<TResponse | string, Error, TRequest>,
): UseMutationResult<TResponse | string, Error, TRequest> {
    const mutationFn = async (body: TRequest): Promise<TResponse | string> => {
        const token = getToken();
        const isFormData = body instanceof FormData;

        const headers: HeadersInit = isFormData
            ? token
                ? { Authorization: `Bearer ${token}` }
                : {}
            : {
                  'Content-Type': 'application/json',
                  ...(token ? { Authorization: `Bearer ${token}` } : {}),
              };

        const res = await fetch(`${API_BASE_URL}${url}`, {
            method,
            headers,
            body: isFormData ? body : JSON.stringify(body),
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
