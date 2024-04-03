const createQueryString = (name: string, value: boolean) => {
  const params = new URLSearchParams();
  params.set(name, value.toString());

  return params.toString();
};
export default createQueryString;
