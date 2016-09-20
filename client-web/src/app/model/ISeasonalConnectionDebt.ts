export interface ISeasonalConnectionDebt{
    id: number;
    seasonalConnectionPaymentId: number;
    seasonalConnectionPaymentDate: string;
    paidOut: boolean;
    connectionId: number;
    issuedDate: string;
    initialMeasurementValue: number;
    finalMeasurementValue: number;
    seasonYear: number;
    seasonMonth: number;
    priceM3: number;
    debtValue: number;
    totalDebtValue: number;
    totalDebtRoundedValue: number;
    deltaMeasurements: number;
}
