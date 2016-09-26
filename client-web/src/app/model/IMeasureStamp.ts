export interface IMeasureStamp{
    id: number;
    date: String;
    value: number;
    connectionID: number;
    registerID: string;
    seasonalConnectionDebtID: number;
    seasonEntryID: number;
    customerName: string;
    zoneName: string;
    address: string;
    modifiedValue: number;
    pending: boolean;
    prevValue: number;
}
