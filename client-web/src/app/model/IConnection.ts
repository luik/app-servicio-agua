export interface IConnection{
    id: number;
    customerName: string;
    zoneName: string;
    registerName: string;
    address: string;
    registerID: number;
    customerID: number;
    zoneID: number;
    comment: string;
    active: boolean;
    startDate: string;
    endDate: string;
    connectionCategoryID: number;
    connectionCategoryName: string;
}
