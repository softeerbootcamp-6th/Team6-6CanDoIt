import useApiMutation from './useApiMutation.ts';

export default function useReportMutation({
    onSuccess,
    onError,
}: {
    onSuccess: () => void | Promise<void>;
    onError: (error: any) => void;
}) {
    return useApiMutation<FormData, any>(`/card/interaction/report`, 'POST', {
        onSuccess,
        onError,
    });
}
