export default function checkHttpSImg(url: string): boolean {
  let protocol = url.split(":")[0];
  let path = url.split(":")[1];
  if (protocol === "https") {
    return true;
  } else {
    return false;
  }
}
