export interface ISeasonalConnectionPayment{
    id: number;
    seasonalConnectionDebtId: number;
    paymentDate: number;
    connectionId: number;
    issuedDate: string;
    initialMeasurementValue: number;
    finalMeasurementValue: number;
    seasonYear: number;
    seasonMonth: number;
    seasonMonthName: string;
    priceM3: number;
    debtValue: number;
    totalDebtValue: number;
    deltaMeasurements: number;
}
