import {
    useQuery,
    type UseQueryOptions,
    type UseQueryResult,
} from '@tanstack/react-query';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export default function useApiQuery<TResponse = any>(
    url: string,
    params?: Record<string, any>,
    options?: Omit<
        UseQueryOptions<
            TResponse,
            Error,
            TResponse,
            [string, Record<string, any>?]
        >,
        'queryKey' | 'queryFn'
    >,
): UseQueryResult<TResponse, Error> {
    const queryFn = async ({
        queryKey,
    }: {
        queryKey: [string, Record<string, any>?];
    }) => {
        const [_url, params] = queryKey;
        const queryString =
            params && Object.keys(params).length
                ? '?' +
                  new URLSearchParams(
                      params as Record<string, string>,
                  ).toString()
                : '';

        const res = await fetch(`${API_BASE_URL}${_url}${queryString}`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
        });
        if (!res.ok) {
            const errorData = await res.json().catch(() => ({}));
            throw new Error(errorData.message || 'API 요청 실패');
        }

        return res.json();
    };

    return useQuery<
        TResponse,
        Error,
        TResponse,
        [string, Record<string, any>?]
    >({
        queryKey: [url, params ?? {}],
        queryFn,
        enabled: true,
        ...options,
    });
}
