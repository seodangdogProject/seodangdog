/** @type {import('next').NextConfig} */
const nextConfig = {
  webpack: (config) => {
    config.module.rules.push({
      test: /\.svg$/,
      use: ["@svgr/webpack"],
    });
    return config;
  },
};

// module.exports = {
//     typescript : {
//         ignoreBuildErrors : true,  // typeScript 타입 에러시 build 안되는거 무시하기
//     }
// }
export default nextConfig;
