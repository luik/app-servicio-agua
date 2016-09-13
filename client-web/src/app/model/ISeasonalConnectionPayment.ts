export interface ISeasonalConnectionPayment{
    id: number;
    seasonalConnectionDebtId: number;
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
