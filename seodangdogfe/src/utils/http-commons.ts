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
  token: string,
  method: string,
  body: object | null = null
): Promise<any> {
  const res = await fetch(BASE_URL + path, {
    method,
    headers: {
      "Content-Type": "application/json",
      Authorization:
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmltIiwiVVNFUiI6IlJPTEVfVVNFUiIsImV4cCI6MTcxMzU3ODcxNX0.6iCO_VO6jdC-fvfceiQtN6kyFqInb74AUBC-I4ZUYkg",
    },
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
  ibody: object | null = null
): Promise<any> {
  const res = await fetch(BASE_URL + path, {
    method,
    headers: {
      "Content-Type": "application/json",
    },
    body: ibody ? JSON.stringify(ibody) : null,
  });
  return res;
}

export { privateFetch, publicFetch };
