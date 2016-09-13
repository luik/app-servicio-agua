export interface ISeasonalConnectionDebt{
    id: number;
    seasonalConnectionPaymentId: number;
    seasonalConnectionPaymentDate: string;
    isPaidOut: boolean;
    connectionId: number;
    issuedDate: string;
    initialMeasurementValue: number;
    finalMeasurementValue: number;
    seasonYear: number;
    seasonMonth: number;
    priceM3: number;
    debtValue: number;
    totalDebtValue: number;
    deltaMeasurements: number;
}
