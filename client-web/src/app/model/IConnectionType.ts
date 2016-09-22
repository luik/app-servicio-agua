export interface IConnectionType{
    id: number;
    idx: number;
    category: string;
    consumptionRange: string;
    waterServicePrice: number;
    drainServicePrice: number;
    fixedCharge: number;
    connectionCharge: number;
    connectionChargeDuration: number;
}
