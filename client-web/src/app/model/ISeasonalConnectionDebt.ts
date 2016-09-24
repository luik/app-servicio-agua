export interface ISeasonalConnectionDebt{
    id: number;
    seasonalConnectionPaymentId: number;
    seasonalConnectionPaymentDate: string;
    paidOut: boolean;
    connectionId: number;
    issuedDate: string;
    dueDate: string;
    initialMeasurementDate: string;
    initialMeasurementValue: number;
    finalMeasurementDate: string;
    finalMeasurementValue: number;
    seasonYear: number;
    seasonMonth: number;
    priceM3: number;
    debtValue: number;
    totalDebtValue: number;
    totalDebtRoundedValue: number;
    deltaMeasurements: number;
}
