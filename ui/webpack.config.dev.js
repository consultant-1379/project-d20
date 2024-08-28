const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');
const majorVersion = require('./package.json').version.match(/^\d+/)[0];

const clientRoot = 'client';
const timestamp = (new Date()).getTime();

const getExternalsEntries = () => {
  const obj = {};
  const externals = require('./externals.config.dev');
  Object.keys(externals).forEach((category) => {
    const items = externals[category];
    if (category === 'components') {
      Object.keys(items).forEach((categoryItem) => {
        if (categoryItem === 'default') {
          items[categoryItem].forEach((item) => {
            const { path: itemPath, entry } = item;
            const ip = path.join(category, itemPath, 'Main');
            const op = path.resolve(__dirname, clientRoot, category, itemPath, 'src', `${entry}.js`);
            console.log(`[${ip}]: ${op}`);
            obj[ip] = op;
          });
          return;
        }

        if (categoryItem === 'shareable') {
          items[categoryItem].forEach((item) => {
            const { path: itemPath, entry } = item;
            const ip = path.join(category, itemPath, majorVersion, 'Main');
            const op = path.resolve(__dirname, clientRoot, category, itemPath, 'src', `${entry}.js`);
            console.log(`[${ip}]: ${op}`);
            obj[ip] = op;
          });
        }
      });
    } else {
      items.forEach((item) => {
        const { path: itemPath, entry } = item;
        const ip = path.join(category, itemPath, entry);
        const op = path.resolve(__dirname, clientRoot, category, itemPath, 'src', `${entry}.js`);
        console.log(`[${ip}]: ${op}`);
        obj[ip] = op;
      });
    }
  });

  return obj;
};

const externalsConfig = {
  entry: getExternalsEntries(),
  output: {
    path: path.resolve(__dirname, clientRoot),
    filename: '[name].js',
    libraryTarget: 'amd',
  },
  externals: {
    '@eui/component': 'amd @eui/component',
    '@eui/lit-component': 'amd @eui/lit-component',
    '@eui/panel': 'amd @eui/panel',
    '@eui/app': 'amd @eui/app',
    '@eui/base': 'amd @eui/base',
    'panel-component': 'amd panel-component',
    'displayarea-component': 'amd displayarea-component',
    'job1component': 'amd job1component',
    'main-component': 'amd main-component',
    'job2-main-component': 'amd job2-main-component',
    'job3-main-component': 'amd job3-main-component',
    'job4-main-component': 'amd job4-main-component',
    'job5-main-component': 'amd job5-main-component',
    'job6-main-component': 'amd job6-main-component',
    'job7-main-component': 'amd job7-main-component',
    'job8-main-component': 'amd job8-main-component',
    'job9-main-component': 'amd job9-main-component',
    'job10-main-component': 'amd job10-main-component',
    'job2-displayarea-component': 'amd job2-displayarea-component',
    'job3-displayarea-component': 'amd job3-displayarea-component',
    'job4-displayarea-component': 'amd job4-displayarea-component',
    'job5-displayarea-component': 'amd job5-displayarea-component',
    'job6-displayarea-component': 'amd job6-displayarea-component',
    'job7-displayarea-component': 'amd job7-displayarea-component',
    'job8-displayarea-component': 'amd job8-displayarea-component',
    'job9-displayarea-component': 'amd job9-displayarea-component',
    'job10-displayarea-component': 'amd job10-displayarea-component',
    'calender-component': 'amd calender-component'
  },
};

const config = {
  mode: 'development',
  devtool: 'inline-source-map',
  devServer: {
    contentBase: [
      path.resolve(__dirname, 'client'),
    ],
    before: require('./dev/mock-server-dev'),
  },
  plugins: [
    new HtmlWebpackPlugin({
      hash: true,
      filename: path.resolve(__dirname, 'client/index.html'),
      template: path.resolve(__dirname, 'client/index.html'),
      ts: timestamp,
      chunks: [],
    }),
    new HtmlWebpackPlugin({
      hash: true,
      filename: path.resolve(__dirname, 'client/login.html'),
      template: path.resolve(__dirname, 'client/login.html'),
      ts: timestamp,
      chunks: [],
    }),
  ],
  module: {
    rules: [
      {
        test: /\.js$/,
        include: [
          path.resolve(__dirname, 'client/components'),
          path.resolve(__dirname, 'client/panels'),
          path.resolve(__dirname, 'client/plugins'),
          path.resolve(__dirname, 'client/apps'),
        ],
        loader: 'babel-loader',
        options: {
          plugins: [
            ['@babel/plugin-proposal-decorators', { legacy: true }],
            ['@babel/plugin-proposal-class-properties', { loose: true }],
          ],
        },
      },
      {
        test: /\.(html)/,
        use: {
          loader: 'raw-loader',
          options: {
            esModule: true,
          },
        },
        exclude: [
          path.resolve(__dirname, 'client/index.html'),
          path.resolve(__dirname, 'client/login.html'),
        ],
      },
      {
        test: /\.css$/,
        use: ['css-loader'],
      },
    ],
  },
};

module.exports = () => new Promise((resolve) => {
  const _externalsConfig = Object.assign({}, config, externalsConfig);
  resolve(_externalsConfig);
});
