export function getSelectedDayStartTime(dayOffset: number): string {
    const now = new Date();
    const targetDate = new Date(
        now.getFullYear(),
        now.getMonth(),
        now.getDate() + (dayOffset - 1),
    );

    const year = targetDate.getFullYear();
    const month = (targetDate.getMonth() + 1).toString().padStart(2, '0');
    const day = targetDate.getDate().toString().padStart(2, '0');

    const hour =
        dayOffset === 1 ? now.getHours().toString().padStart(2, '0') : '00';

    return `${year}-${month}-${day}T${hour}:00:00`;
}
