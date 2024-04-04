const BASE_URL = "https://j10e104.p.ssafy.io/api";
/**
 * 토큰을 실어서 보낸다.
 * @param {string} path   -> EX) /news/id
 * @param {string} token  -> JWT Access Token
 * @param {string} method  -> METHOD
 * @returns {Promise<any>} -> json객체 반환
 *
 */
async function privateFetch(
  path: string,
  method: string,
  body: object | null = null
): Promise<any> {
  if (body === null) {
    const res = await fetch(BASE_URL + path, {
      method,
      headers: {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("accessToken") || "",
      },
    });
    return res;
  }
  const res = await fetch(BASE_URL + path, {
    method,
    headers: {
      "Content-Type": "application/json",
      Authorization: localStorage.getItem("accessToken") || "",
    },
    body: JSON.stringify(body),
  });
  return res;
}
/**
 * 토큰이 없이 가입 되어있지 않은 사용자가 보내는 fetch함수
 * @param {string} method  -> METHOD
 * @returns  -> json 객체 반환
 */
async function publicFetch(
  path: string,
  method: string,
  body: object | null = null
): Promise<any> {
  if (body === null) {
    const res = await fetch(BASE_URL + path, {
      method,
      headers: {
        "Content-Type": "application/json",
      },
    });
    return res;
  }
  const res = await fetch(BASE_URL + path, {
    method,
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });
  return res;
}

export { privateFetch, publicFetch };
