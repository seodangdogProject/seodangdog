/** @type {import('next').NextConfig} */
const nextConfig = {
    reactStrictMode: false,
    webpack: (config) => {
        config.module.rules.push({
            test: /.svg$/,
            issuer: { and: [/.(js|ts|md)x?$/] },
            use: ["@svgr/webpack"],
        });
        return config;
    },
};
export default nextConfig;
