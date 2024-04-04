export default function changeDateFormat(initDate: string): string {
  const date = new Date(initDate);

  return date.toLocaleString("ko-KR", { timeZone: "UTC" });
}
