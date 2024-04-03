export default function checkHttpSImg(url: string): boolean {
  let protocol = url.split(":")[0];
  let path = url.split(":")[1];
  console.log("프로토콜 : ", protocol);
  console.log("경로 : ", path);
  if (protocol === "https") {
    return true;
  } else {
    return false;
  }
}
