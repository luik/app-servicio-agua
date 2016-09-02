export interface ISeasonalConnectionDebt{
    id: number;
    connectionId: number;
    issuedDate: string;
    initialMeasurementValue: number;
    finalMeasurementValue: number;
    seasonYear: number;
    seasonMonth: number;
}
