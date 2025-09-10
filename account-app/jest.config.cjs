// jest.config.cjs
const { createCjsPreset } = require('jest-preset-angular/presets');

module.exports = {
    ...createCjsPreset(),
    setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
    testMatch: ['**/?(*.)+(spec).ts'],
    testPathIgnorePatterns: ['<rootDir>/src/test.ts'], // ignora el archivo de Karma si existe
    moduleNameMapper: {
        '^@app/(.*)$': '<rootDir>/src/app/$1',
        '\\.(css|scss|sass|less)$': '<rootDir>/src/test/style-mock.js',
        '\\.(jpg|jpeg|png|gif|webp|svg)$': '<rootDir>/src/test/file-mock.js'
    },
    testEnvironmentOptions: { url: 'http://localhost/' }
};
