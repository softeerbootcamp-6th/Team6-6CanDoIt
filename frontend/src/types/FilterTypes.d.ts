export interface Filter {
    id: number;
    description: string;
}
export type Keyword = 'weatherKeywords' | 'rainKeywords' | 'etceteraKeywords';
export type FilterColumn = Record<Keyword, Filter[]>;
export type SelectedColumn = Record<Keyword, number[]>;
