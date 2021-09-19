let proxyObj = {};

proxyObj['/ws'] = {
    ws: true,
    target: "ws://localhost:8088"
};

proxyObj['/'] = {
    ws: false,
    target: 'http://localhost:8088',
    changeOrigin: true,
    pathRewrite: {
        '^/': ''
    }
};
module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyObj
    }
}